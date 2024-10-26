package net.blay09.mods.spookydoors.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.blay09.mods.spookydoors.SpookyDoors;
import net.blay09.mods.spookydoors.block.SpookyDoorBlock;
import net.blay09.mods.spookydoors.block.entity.SpookyDoorBlockEntity;
import net.blay09.mods.spookydoors.client.render.SpookyDoorBlockEntityRenderer;
import net.blay09.mods.spookydoors.network.ServerboundOpenCloseDoorPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RenderHighlightEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;

@Mod(value = SpookyDoors.MOD_ID, dist = Dist.CLIENT)
public class SpookyDoorsClient {

    private static final int SYNC_INTERVAL = 20;
    private static SpookyDoorsClient INSTANCE;

    public static SpookyDoorsClient getInstance() {
        return INSTANCE;
    }

    private double lastMouseX;
    private boolean isDragging;
    private SpookyDoorBlockEntity activeDoor;
    private int ticksSinceLastSync;
    private boolean isDirty;

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

            // Calculate the horizontal mouse movement (delta x).
            double deltaX = x - lastMouseX;

            // Get the player's position and the door's position.
            final var player = Minecraft.getInstance().player;
            final var doorPos = activeDoor.getBlockPos();

            // Calculate the relative position of the player to the door.
            final double relativeX = player.getX() - doorPos.getX();
            final double relativeZ = player.getZ() - doorPos.getZ();

            // Determine if the player is on the "front" or "back" side of the door.
            boolean isPlayerBehind = switch (facing) {
                case NORTH -> relativeZ < 0;
                case SOUTH -> relativeZ > 0;
                case WEST -> relativeX < 0;
                case EAST -> relativeX > 0;
                default -> false;
            };

            // Adjust deltaX based on the door's facing, hinge, and player position.
            // If the player is behind the door, invert the direction of deltaX.
            if (isPlayerBehind) {
                deltaX = -deltaX;
            }

            // Further adjust deltaX based on hinge.
            deltaX = hinge == DoorHingeSide.LEFT ? -deltaX : deltaX;

            // Scale the deltaX for smoother adjustment and clamp the openness.
            final double sensitivity = 0.005; // Adjust sensitivity for a smoother or faster drag response
            openness += (float) (deltaX * sensitivity);

            // Update the door's openness and store the current mouse position for the next move.
            activeDoor.setOpennessBy(openness, player);
            isDirty = true;
            lastMouseX = x;

            return true; // Indicate that the mouse movement was handled.
        }
        return false;
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
                }
                isDragging = false;
                activeDoor = null;
            }
        }
    }

}
