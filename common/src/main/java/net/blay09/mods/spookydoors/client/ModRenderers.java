package net.blay09.mods.spookydoors.client;

import net.blay09.mods.balm.client.renderer.blockentity.BalmBlockEntityRendererRegistrar;
import net.blay09.mods.spookydoors.ModBlockEntities;
import net.blay09.mods.spookydoors.client.render.SpookyDoorBlockEntityRenderer;

public class ModRenderers {
    public static void initialize(BalmBlockEntityRendererRegistrar renderers) {
        renderers.register(ModBlockEntities.spookyDoor, SpookyDoorBlockEntityRenderer::new);
    }
}
