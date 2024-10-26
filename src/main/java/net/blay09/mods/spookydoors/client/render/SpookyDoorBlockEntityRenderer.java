package net.blay09.mods.spookydoors.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.blay09.mods.spookydoors.block.SpookyDoorBlock;
import net.blay09.mods.spookydoors.block.entity.SpookyDoorBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.AABB;
import org.joml.AxisAngle4d;
import org.joml.Quaternionf;

public class SpookyDoorBlockEntityRenderer implements BlockEntityRenderer<SpookyDoorBlockEntity> {

    private final BlockRenderDispatcher blockRenderDispatcher;
    private final RandomSource randomSource;

    public SpookyDoorBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        blockRenderDispatcher = context.getBlockRenderDispatcher();
        randomSource = RandomSource.create();
    }

    @Override
    public void render(SpookyDoorBlockEntity blockEntity, float delta, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        final var level = blockEntity.getLevel();
        if (level == null) {
            return;
        }

        final var pos = blockEntity.getBlockPos();
        final var state = blockEntity.getBlockState();
        var baseDoor = blockEntity;
        if (state.getValue(SpookyDoorBlock.HALF) == DoubleBlockHalf.UPPER) {
            final var lowerBlockEntity = level.getBlockEntity(pos.below());
            if (lowerBlockEntity instanceof SpookyDoorBlockEntity lowerSpookyDoor) {
                baseDoor = lowerSpookyDoor;
            }
        }

        final var vertexConsumer = multiBufferSource.getBuffer(RenderType.CUTOUT);
        poseStack.pushPose();
        applyDoorPose(poseStack, baseDoor.getOpenness(), state.getValue(SpookyDoorBlock.FACING), state.getValue(SpookyDoorBlock.HINGE));
        final var stateForRender = state.setValue(SpookyDoorBlock.OPEN, false);
        blockRenderDispatcher.renderBatched(stateForRender, pos, level, poseStack, vertexConsumer, false, randomSource);
        poseStack.popPose();
    }

    @Override
    public AABB getRenderBoundingBox(SpookyDoorBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(1);
    }

    public static void applyDoorPose(PoseStack poseStack, float openness, Direction facing, DoorHingeSide hinge) {
        var offX = 0f;
        var offZ = 0f;
        if (hinge == DoorHingeSide.LEFT) {
            if (facing == Direction.WEST) {
                offX = 1f - 1 / 16f - 1 / 32f;
                offZ = 1f - 1 / 16f - 1 / 32f;
            } else if (facing == Direction.SOUTH) {
                offX = 1f - 1 / 16f - 1 / 32f;
                offZ = 1 / 16f + 1 / 32f;
            } else if (facing == Direction.NORTH) {
                offX = 1 / 16f + 1 / 32f;
                offZ = 1 - 1 / 16f - 1 / 32f;
            } else if (facing == Direction.EAST) {
                offX = 1 / 16f + 1 / 32f;
                offZ = 1 / 16f + 1 / 32f;
            }
        } else {
            if (facing == Direction.WEST) {
                offX = 1f - 1 / 16f - 1 / 32f;
                offZ = 1 / 16f + 1 / 32f;
            } else if (facing == Direction.SOUTH) {
                offX = 1 / 16f + 1 / 32f;
                offZ = 1 / 16f + 1 / 32f;
            } else if (facing == Direction.NORTH) {
                offX = 1f - 1 / 16f - 1 / 32f;
                offZ = 1f - 1 / 16f - 1 / 32f;
            } else if (facing == Direction.EAST) {
                offX = 1 / 16f + 1 / 32f;
                offZ = 1f - 1 / 16f - 1 / 32f;
            }
        }
        poseStack.translate(offX, 0, offZ);
        poseStack.pushPose();
        poseStack.scale(0.25f, 0.25f, 0.25f);
        poseStack.popPose();
        poseStack.mulPose(new Quaternionf(new AxisAngle4d(openness * Math.PI / 2, 0, hinge == DoorHingeSide.LEFT ? 1 : -1, 0)));
        poseStack.translate(-offX, 0, -offZ);
    }
}
