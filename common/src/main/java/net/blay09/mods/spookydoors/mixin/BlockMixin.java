package net.blay09.mods.spookydoors.mixin;

import net.blay09.mods.spookydoors.block.SpookyDoorBlockHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class BlockMixin {

    @Inject(method = "getRenderShape(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/RenderShape;", at = @At("HEAD"), cancellable = true)
    private void getRenderShape(BlockState state, CallbackInfoReturnable<RenderShape> cir) {
        final var renderShape = SpookyDoorBlockHooks.getRenderShape(state);
        if (renderShape != null) {
            cir.setReturnValue(renderShape);
        }
    }

    @Inject(method = "getCollisionShape(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At("HEAD"), cancellable = true)
    private void getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        final var collisionShape = SpookyDoorBlockHooks.getCollisionShape(state, level, pos);
        if (collisionShape != null) {
            cir.setReturnValue(collisionShape);
        }
    }

    @Inject(method = "entityInside(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"))
    private void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo ci) {
        SpookyDoorBlockHooks.entityInside(state, level, pos, entity);
    }

    @Inject(method = "onPlace(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)V", at = @At("HEAD"))
    private void onPlace(BlockState state, Level level, BlockPos pos, BlockState previousState, boolean isMoving, CallbackInfo ci) {
        SpookyDoorBlockHooks.onPlace(state, level, pos, previousState);
    }

    @Inject(method = "onRemove(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)V", at = @At("HEAD"))
    private void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving, CallbackInfo ci) {
        SpookyDoorBlockHooks.onRemove(state, level, pos, newState);
    }
}
