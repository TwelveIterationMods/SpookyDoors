package net.blay09.mods.spookydoors.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import net.blay09.mods.spookydoors.SpookyDoors;
import net.blay09.mods.spookydoors.block.SpookyDoorBlock;
import net.blay09.mods.spookydoors.block.entity.SpookyDoorBlockEntity;
import net.blay09.mods.spookydoors.client.render.SpookyDoorBlockEntityRenderer;
import net.blay09.mods.spookydoors.network.ServerboundOpenCloseDoorPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;

@Mod(value = SpookyDoors.MOD_ID, dist = Dist.CLIENT)
public class SpookyDoorsClient {

    private static final ResourceLocation UI_HINT_TEXTURE = ResourceLocation.fromNamespaceAndPath(SpookyDoors.MOD_ID, "textures/gui/door_ui_hint.png");
    private static final int UI_HINT_TICKS = 20;

    private static final int SYNC_INTERVAL = 1;
    private static SpookyDoorsClient INSTANCE;

    public static SpookyDoorsClient getInstance() {
        return INSTANCE;
    }

    private double lastMouseX;
    private boolean isDragging;
    private float accumulatedOpennessChange;
    private SpookyDoorBlockEntity activeDoor;
    private int ticksSinceLastSync;
    private boolean isDirty;

    private int uiHintTicksLeft = 0;

    public SpookyDoorsClient(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        INSTANCE = this;
    }

    public static void setActiveDoor(SpookyDoorBlockEntity activeDoor) {
        getInstance().activeDoor = activeDoor;
    }

    public boolean onMoveMouse(long windowPointer, double x, double y) {
        if (activeDoor != null && isDragging) {
            final var state = activeDoor.getBlockState();
            final var facing = state.getValue(SpookyDoorBlock.FACING);
            final var hinge = state.getValue(SpookyDoorBlock.HINGE);
            var openness = activeDoor.getOpenness();

            double deltaX = x - lastMouseX;

            final var player = Minecraft.getInstance().player;
            final var doorPos = activeDoor.getBlockPos();

            final double relativeX = player.getX() - doorPos.getX();
            final double relativeZ = player.getZ() - doorPos.getZ();

            boolean isPlayerBehind = switch (facing) {
                case NORTH -> relativeZ < 0;
                case SOUTH -> relativeZ > 0;
                case WEST -> relativeX < 0;
                case EAST -> relativeX > 0;
                default -> false;
            };

            if (isPlayerBehind) {
                deltaX = -deltaX;
            }

            deltaX = hinge == DoorHingeSide.LEFT ? -deltaX : deltaX;

            final double sensitivity = 0.005;
            openness += (float) (deltaX * sensitivity);

            final var currentOpenness = activeDoor.getOpenness();
            accumulatedOpennessChange += Math.abs(openness - currentOpenness);
            activeDoor.setOpennessBy(openness, player);
            isDirty = true;
            lastMouseX = x;

            return true;
        }
        return false;
    }

    @SubscribeEvent
    void onDrawGui(RenderGuiEvent.Post event) {
        if (uiHintTicksLeft > 0) {
            final var guiGraphics = event.getGuiGraphics();
            final var poseStack = guiGraphics.pose();
            RenderSystem.enableBlend();
            poseStack.pushPose();
            final var screenCenterX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2;
            final var screenCenterY = Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2;
            poseStack.translate(screenCenterX, screenCenterY, 0);
            poseStack.scale(0.4f, 0.4f, 0.4f);
            final var alpha = uiHintTicksLeft / (float) UI_HINT_TICKS;
            guiGraphics.setColor(1f, 1f, 1f, alpha);
            guiGraphics.blit(UI_HINT_TEXTURE, -23, -16 - 38, 0, 0, 46, 32, 46, 32);
            poseStack.popPose();
        }
    }

    @SubscribeEvent
    void onDrawHighlight(RenderHighlightEvent.Block event) {
        final var level = Minecraft.getInstance().level;
        if (level == null) {
            return;
        }

        final var pos = event.getTarget().getBlockPos();
        final var state = level.getBlockState(pos);
        final var blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof SpookyDoorBlockEntity spookyDoor) {
            final var baseDoor = spookyDoor.getBaseDoor();
            if (level.getWorldBorder().isWithinBounds(pos)) {
                final var cameraVec = event.getCamera().getPosition();
                final var cameraX = cameraVec.x();
                final var cameraY = cameraVec.y();
                final var cameraZ = cameraVec.z();
                final var vertexConsumer = event.getMultiBufferSource().getBuffer(RenderType.lines());
                final var poseStack = event.getPoseStack();
                poseStack.pushPose();
                poseStack.translate(pos.getX() - cameraX,
                        pos.getY() - cameraY,
                        pos.getZ() - cameraZ);
                SpookyDoorBlockEntityRenderer.applyDoorPose(
                        poseStack,
                        baseDoor.getOpenness(),
                        state.getValue(SpookyDoorBlock.FACING),
                        state.getValue(SpookyDoorBlock.HINGE));
                final var shape = SpookyDoorBlock.getOutlineShape(state);
                LevelRenderer.renderVoxelShape(poseStack, vertexConsumer, shape, 0, 0, 0, 0f, 0f, 0f, 0.4f, false);
                poseStack.popPose();
            }
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    void onClientTick(ClientTickEvent.Post event) {
        if (!isDragging && activeDoor != null) {
            if (!Minecraft.getInstance().player.blockPosition().equals(activeDoor.getBlockPos())) {
                activeDoor = null;
            }
        }
        ticksSinceLastSync++;
        if (ticksSinceLastSync >= SYNC_INTERVAL) {
            if (activeDoor != null && isDirty) {
                PacketDistributor.sendToServer(new ServerboundOpenCloseDoorPacket(activeDoor.getBlockPos(), activeDoor.getOpenness()));
                isDirty = false;
            }
            ticksSinceLastSync = 0;
        }
        if (uiHintTicksLeft > 0) {
            uiHintTicksLeft--;
        }
    }

    @SubscribeEvent
    void onMouseInput(InputEvent.MouseButton.Pre event) {
        if (event.getButton() == InputConstants.MOUSE_BUTTON_RIGHT) {
            if (event.getAction() == InputConstants.PRESS) {
                final var hitResult = Minecraft.getInstance().hitResult;
                if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
                    final var blockHitResult = ((BlockHitResult) hitResult);
                    final var level = Minecraft.getInstance().level;
                    final var pos = blockHitResult.getBlockPos();
                    final var blockEntity = level.getBlockEntity(pos);
                    if (blockEntity instanceof SpookyDoorBlockEntity spookyDoor) {
                        final var entity = Minecraft.getInstance().getCameraEntity();
                        if (entity != null) {
                            lastMouseX = Minecraft.getInstance().mouseHandler.xpos();
                            activeDoor = spookyDoor.getBaseDoor();
                            activeDoor.setClientControl(true);
                            isDragging = true;
                            event.setCanceled(true);
                        }
                    }
                }
            } else if (event.getAction() == InputConstants.RELEASE) {
                if (activeDoor != null) {
                    if (isDirty) {
                        PacketDistributor.sendToServer(new ServerboundOpenCloseDoorPacket(activeDoor.getBlockPos(), activeDoor.getOpenness()));
                        isDirty = false;
                    }
                    activeDoor.setClientControl(false);
                    if (accumulatedOpennessChange < 0.1) {
                        uiHintTicksLeft = UI_HINT_TICKS;
                    }
                    accumulatedOpennessChange = 0f;
                }
                isDragging = false;
                activeDoor = null;
            }
        }
    }

}
