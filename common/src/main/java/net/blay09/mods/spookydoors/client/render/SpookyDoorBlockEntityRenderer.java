package net.blay09.mods.spookydoors.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.blay09.mods.spookydoors.block.SpookyDoorBlock;
import net.blay09.mods.spookydoors.block.entity.SpookyDoorBlockEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.AxisAngle4d;
import org.joml.Quaternionf;

public class SpookyDoorBlockEntityRenderer implements BlockEntityRenderer<SpookyDoorBlockEntity, SpookyDoorBlockEntityRenderer.SpookyDoorRenderState> {

    private final BlockRenderDispatcher blockRenderDispatcher;

    public static class SpookyDoorRenderState extends BlockEntityRenderState {
        public float openness;
    }

    public SpookyDoorBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderDispatcher = context.blockRenderDispatcher();
    }

    @Override
    public SpookyDoorRenderState createRenderState() {
        return new SpookyDoorRenderState();
    }

    @Override
    public void extractRenderState(SpookyDoorBlockEntity blockEntity, SpookyDoorRenderState renderState, float delta, Vec3 vec, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, delta, vec, crumblingOverlay);

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

        renderState.openness = baseDoor.getOpenness();
        renderState.blockState = state.setValue(SpookyDoorBlock.OPEN, false);
    }

    @Override
    public void submit(SpookyDoorRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        final var state = renderState.blockState;
        applyDoorPose(poseStack, renderState.openness, state.getValue(SpookyDoorBlock.FACING), state.getValue(SpookyDoorBlock.HINGE));
        final var model = blockRenderDispatcher.getBlockModel(renderState.blockState);
        submitNodeCollector.submitBlockModel(poseStack, RenderType.entitySolid(TextureAtlas.LOCATION_BLOCKS), model, 1f, 1f, 1f, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
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
