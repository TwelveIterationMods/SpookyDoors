package net.blay09.mods.spookydoors.mixin;

import net.blay09.mods.spookydoors.client.SpookyDoorsClient;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.input.MouseButtonInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    @Inject(method = "onMove", at = @At("HEAD"), cancellable = true)
    private void onMove(long windowPointer, double x, double y, CallbackInfo ci) {
        if (SpookyDoorsClient.onMoveMouse(windowPointer, x, y)) {
            //noinspection DataFlowIssue
            ((MouseHandler) (Object) this).setIgnoreFirstMove();
            ci.cancel();
        }
    }

    @Inject(method = "onButton", at = @At("HEAD"), cancellable = true)
    private void onButton(long windowPointer, MouseButtonInfo mouseButtonInfo, int action, CallbackInfo ci) {
        if (SpookyDoorsClient.onMouseInput(mouseButtonInfo, action)) {
            ci.cancel();
        }
    }
}
