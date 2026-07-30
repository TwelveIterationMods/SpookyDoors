package net.blay09.mods.spookydoors.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.blay09.mods.spookydoors.client.SpookyDoorsClient;
import net.blay09.mods.spookydoors.client.render.SpookyDoorRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Inject(method = "submitBlockEntities", at = @At("TAIL"))
    private void submitBlockEntities(PoseStack poseStack, LevelRenderState levelRenderState, SubmitNodeCollector submitNodeCollector, CallbackInfo ci) {
        SpookyDoorRenderer.submitDoors(poseStack, submitNodeCollector, levelRenderState.cameraRenderState.pos);
    }

    @Inject(method = "submitBlockOutline", at = @At("HEAD"), cancellable = true)
    private void submitBlockOutline(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, LevelRenderState levelRenderState, CallbackInfo ci) {
        if (SpookyDoorsClient.submitBlockOutline(poseStack, submitNodeCollector, levelRenderState)) {
            ci.cancel();
        }
    }
}
