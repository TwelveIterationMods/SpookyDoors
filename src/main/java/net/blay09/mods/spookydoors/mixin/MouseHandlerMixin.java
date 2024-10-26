package net.blay09.mods.spookydoors.mixin;

import net.blay09.mods.spookydoors.client.SpookyDoorsClient;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    @Inject(method = "onMove", at = @At("HEAD"), cancellable = true)
    private void onMove(long windowPointer, double x, double y, CallbackInfo ci) {
        if (SpookyDoorsClient.getInstance().onMoveMouse(windowPointer, x, y)) {
            //noinspection DataFlowIssue
            ((MouseHandler) (Object) this).setIgnoreFirstMove();
            ci.cancel();
        }
    }
}
