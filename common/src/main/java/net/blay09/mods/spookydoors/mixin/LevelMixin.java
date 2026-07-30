package net.blay09.mods.spookydoors.mixin;

import net.blay09.mods.spookydoors.block.SpookyDoorBlockHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Level.class)
public class LevelMixin {

    @Inject(method = "updatePOIOnBlockStateChange", at = @At("HEAD"))
    private void updatePOIOnBlockStateChange(BlockPos pos, BlockState oldState, BlockState newState, CallbackInfo ci) {
        SpookyDoorBlockHooks.onRemove(oldState, (Level) (Object) this, pos, newState);
    }
}
