package net.blay09.mods.spookydoors.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.blay09.mods.spookydoors.block.SpookyDoorShapes;
import net.blay09.mods.spookydoors.client.SpookyDoorClientTracking;
import net.blay09.mods.spookydoors.core.SpookyDoorProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.AxisAngle4d;
import org.joml.Quaternionf;

public class SpookyDoorRenderer {

    public static void renderDoors(PoseStack poseStack, MultiBufferSource bufferSource, Vec3 cameraPosition) {
        final var level = Minecraft.getInstance().level;
        if (level == null) {
            return;
        }

        final var blockRenderDispatcher = Minecraft.getInstance().getBlockRenderer();
        for (final var basePos : SpookyDoorClientTracking.get(level).doorPositions()) {
            final var baseState = level.getBlockState(basePos);
            if (!(baseState.getBlock() instanceof DoorBlock)) {
                continue;
            }

            renderDoorHalf(level, blockRenderDispatcher, poseStack, bufferSource, cameraPosition, basePos, baseState, basePos);

            final var upperPos = basePos.above();
            final var upperState = level.getBlockState(upperPos);
            if (upperState.getBlock() instanceof DoorBlock && upperState.getValue(DoorBlock.HALF) == DoubleBlockHalf.UPPER) {
                renderDoorHalf(level, blockRenderDispatcher, poseStack, bufferSource, cameraPosition, upperPos, upperState, basePos);
            }
        }
    }

    private static void renderDoorHalf(Level level, BlockRenderDispatcher blockRenderDispatcher, PoseStack poseStack, MultiBufferSource bufferSource, Vec3 cameraPosition, BlockPos pos, BlockState state, BlockPos basePos) {
        final var openness = SpookyDoorProvider.get(level).at(basePos).percentOpen();
        final var stateForRender = state.setValue(DoorBlock.OPEN, false);
        final var vertexConsumer = bufferSource.getBuffer(RenderType.cutout());

        poseStack.pushPose();
        poseStack.translate(pos.getX() - cameraPosition.x(), pos.getY() - cameraPosition.y(), pos.getZ() - cameraPosition.z());
        applyDoorPose(poseStack, openness, state.getValue(DoorBlock.FACING), state.getValue(DoorBlock.HINGE));
        blockRenderDispatcher.getModelRenderer().renderModel(poseStack.last(),
                vertexConsumer,
                stateForRender,
                blockRenderDispatcher.getBlockModel(stateForRender),
                1f,
                1f,
                1f,
                getDoorLight(level, pos, stateForRender, openness),
                OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    private static int getDoorLight(Level level, BlockPos pos, BlockState state, float openness) {
        final var shape = SpookyDoorShapes.getInteractionShape(state, openness);
        final var skyLight = new int[]{LightTexture.sky(LevelRenderer.getLightColor(level, state, pos))};
        final var blockLight = new int[]{LightTexture.block(LevelRenderer.getLightColor(level, state, pos))};

        shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
            sampleLight(level, state, pos, minX, minY, minZ, skyLight, blockLight);
            sampleLight(level, state, pos, maxX, maxY, maxZ, skyLight, blockLight);
            sampleLight(level, state, pos, (minX + maxX) / 2, (minY + maxY) / 2, (minZ + maxZ) / 2, skyLight, blockLight);
        });

        return LightTexture.pack(blockLight[0], skyLight[0]);
    }

    private static void sampleLight(Level level, BlockState state, BlockPos pos, double x, double y, double z, int[] skyLight, int[] blockLight) {
        final var samplePos = BlockPos.containing(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
        final var light = LevelRenderer.getLightColor(level, state, samplePos);
        skyLight[0] = Math.max(skyLight[0], LightTexture.sky(light));
        blockLight[0] = Math.max(blockLight[0], LightTexture.block(light));
    }

    public static VoxelShape getOutlineShape(BlockState state) {
        return SpookyDoorShapes.getDoorGeometry(state, false).shape();
    }

    public static void applyDoorPose(PoseStack poseStack, float openness, Direction facing, DoorHingeSide hinge) {
        final var pivot = SpookyDoorShapes.getPivot(facing, hinge);
        final var offX = pivot.x() / 16.0D;
        final var offZ = pivot.z() / 16.0D;
        poseStack.translate(offX, 0, offZ);
        poseStack.mulPose(new Quaternionf(new AxisAngle4d(openness * Math.PI / 2, 0, hinge == DoorHingeSide.LEFT ? 1 : -1, 0)));
        poseStack.translate(-offX, 0, -offZ);
    }
}
