package net.blay09.mods.spookydoors.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.BalmClientRegistrars;
import net.blay09.mods.balm.client.platform.event.callback.ClientTickCallback;
import net.blay09.mods.balm.client.platform.event.callback.RenderCallback;
import net.blay09.mods.balm.platform.event.EventHandling;
import net.blay09.mods.spookydoors.SpookyDoors;
import net.blay09.mods.spookydoors.block.SpookyDoorBlock;
import net.blay09.mods.spookydoors.block.entity.SpookyDoorBlockEntity;
import net.blay09.mods.spookydoors.client.render.SpookyDoorBlockEntityRenderer;
import net.blay09.mods.spookydoors.network.ServerboundOpenCloseDoorPacket;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.ShapeRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class SpookyDoorsClient {

    private static final Identifier UI_HINT_TEXTURE = Identifier.fromNamespaceAndPath(SpookyDoors.MOD_ID, "textures/gui/door_ui_hint.png");
    private static final int UI_HINT_TICKS = 20;

    private static final int SYNC_INTERVAL = 1;

    private static double lastMouseX;
    private static boolean isDragging;
    private static float accumulatedOpennessChange;
    private static SpookyDoorBlockEntity activeDoor;
    private static int ticksSinceLastSync;
    private static boolean isDirty;

    private static int uiHintTicksLeft = 0;

    public static void initialize(BalmClientRegistrars registrars) {
        registrars.blockEntityRenderers(ModRenderers::initialize);

        RenderCallback.Gui.AFTER.register(SpookyDoorsClient::onDrawGui);
        RenderCallback.BlockHighlight.EVENT.register(SpookyDoorsClient::onDrawHighlight);
        ClientTickCallback.AFTER.register(SpookyDoorsClient::onClientTick);
    }

    public static void setActiveDoor(SpookyDoorBlockEntity activeDoor) {
        SpookyDoorsClient.activeDoor = activeDoor;
    }

    public static boolean onMoveMouse(long windowHandle, double x, double y) {
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

    private static void onDrawGui(GuiGraphics guiGraphics, Window window) {
        if (uiHintTicksLeft > 0) {
            final var poseStack = guiGraphics.pose();
            poseStack.pushMatrix();
            final var screenCenterX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2;
            final var screenCenterY = Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2;
            poseStack.translate(screenCenterX, screenCenterY);
            poseStack.scale(0.4f, 0.4f);
            final var alpha = uiHintTicksLeft / (float) UI_HINT_TICKS;
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, UI_HINT_TEXTURE, -23, -16 - 38, 0, 0, 46, 32, 46, 32, 0xFFFFFF | (int) (alpha * 255) << 24);
            poseStack.popMatrix();
        }
    }

    private static EventHandling onDrawHighlight(BlockHitResult hitResult, PoseStack poseStack, MultiBufferSource multiBufferSource, Camera camera) {
        final var level = Minecraft.getInstance().level;
        if (level == null) {
            return EventHandling.RESUME;
        }

        final var pos = hitResult.getBlockPos();
        final var state = level.getBlockState(pos);
        final var blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof SpookyDoorBlockEntity spookyDoor) {
            final var baseDoor = spookyDoor.getBaseDoor();
            if (level.getWorldBorder().isWithinBounds(pos)) {
                final var cameraVec = camera.position();
                final var cameraX = cameraVec.x();
                final var cameraY = cameraVec.y();
                final var cameraZ = cameraVec.z();
                final var vertexConsumer = multiBufferSource.getBuffer(RenderTypes.lines());
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
                ShapeRenderer.renderShape(poseStack, vertexConsumer, shape, 0, 0, 0, 0x66000000, 7f);
                poseStack.popPose();
            }
            return EventHandling.CANCEL;
        }
        return EventHandling.RESUME;
    }

    private static void onClientTick(Minecraft client) {
        if (!isDragging && activeDoor != null) {
            final var player = Minecraft.getInstance().player;
            if (player == null || !player.blockPosition().equals(activeDoor.getBlockPos())) {
                activeDoor = null;
            }
        }
        ticksSinceLastSync++;
        if (ticksSinceLastSync >= SYNC_INTERVAL) {
            if (activeDoor != null && isDirty) {
                Balm.networking().sendToServer(new ServerboundOpenCloseDoorPacket(activeDoor.getBlockPos(), activeDoor.getOpenness()));
                isDirty = false;
            }
            ticksSinceLastSync = 0;
        }
        if (uiHintTicksLeft > 0) {
            uiHintTicksLeft--;
        }
    }

    public static boolean onMouseInput(MouseButtonInfo mouseButtonInfo, int action) {
        final var minecraft = Minecraft.getInstance();
        if (minecraft.options.keyUse.matchesMouse(new MouseButtonEvent(0, 0, mouseButtonInfo))) {
            if (action == InputConstants.PRESS) {
                final var hitResult = minecraft.hitResult;
                if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
                    final var blockHitResult = ((BlockHitResult) hitResult);
                    final var pos = blockHitResult.getBlockPos();
                    final var level = minecraft.level;
                    if (level != null) {
                        final var blockEntity = level.getBlockEntity(pos);
                        if (blockEntity instanceof SpookyDoorBlockEntity spookyDoor) {
                            final var entity = minecraft.getCameraEntity();
                            if (entity != null) {
                                lastMouseX = minecraft.mouseHandler.xpos();
                                activeDoor = spookyDoor.getBaseDoor();
                                activeDoor.setClientControl(true);
                                isDragging = true;
                                return true;
                            }
                        }
                    }
                }
            } else if (action == InputConstants.RELEASE) {
                if (activeDoor != null) {
                    if (isDirty) {
                        Balm.networking().sendToServer(new ServerboundOpenCloseDoorPacket(activeDoor.getBlockPos(), activeDoor.getOpenness()));
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
        return false;
    }

}
