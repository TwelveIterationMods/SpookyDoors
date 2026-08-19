package net.blay09.mods.spookydoors.mixin.client;

import net.blay09.mods.spookydoors.client.SpookyDoorsClient;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow
    private int rightClickDelay;

    @Inject(method = "handleKeybinds", at = @At("HEAD"))
    private void handleKeybinds(CallbackInfo ci) {
        if (SpookyDoorsClient.captureUseKey()) {
            rightClickDelay = 4;
        }
    }
}
