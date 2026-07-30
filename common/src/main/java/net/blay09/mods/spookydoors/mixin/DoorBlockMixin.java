package net.blay09.mods.spookydoors.mixin;

import net.blay09.mods.spookydoors.block.SpookyDoorBlockHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import org.jetbrains.annotations.Nullable;

@Mixin(DoorBlock.class)
public class DoorBlockMixin {

    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true)
    private void getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        final var interactionShape = SpookyDoorBlockHooks.getInteractionShape(state, level, pos);
        if (interactionShape != null) {
            cir.setReturnValue(interactionShape);
        }
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
        final var result = SpookyDoorBlockHooks.use((DoorBlock) (Object) this, state, level, pos, player, hand);
        if (result != null) {
            cir.setReturnValue(result);
        }
    }

    @Inject(method = "setOpen", at = @At("RETURN"))
    private void setOpen(@Nullable Entity entity, Level level, BlockState state, BlockPos pos, boolean open, CallbackInfo ci) {
        SpookyDoorBlockHooks.setOpen((DoorBlock) (Object) this, level, state, pos, open);
    }

}
