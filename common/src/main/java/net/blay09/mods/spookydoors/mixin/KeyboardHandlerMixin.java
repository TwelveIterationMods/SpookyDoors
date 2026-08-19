package net.blay09.mods.spookydoors.mixin;

import net.blay09.mods.spookydoors.client.SpookyDoorsClient;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {
    @Final
    @Shadow
    private Minecraft minecraft;

    @Inject(method = "keyPress", at = @At("HEAD"), cancellable = true)
    public void keyPress(long handle, @KeyEvent.Action int action, KeyEvent event, CallbackInfo ci) {
        if (handle == this.minecraft.getWindow().handle()
                && SpookyDoorsClient.onKeyPress(action, event)) {
            ci.cancel();
        }
    }
}
