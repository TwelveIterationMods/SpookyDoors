package net.blay09.mods.spookydoors.client;

import net.blay09.mods.balm.api.client.rendering.BalmRenderers;
import net.blay09.mods.spookydoors.ModBlockEntities;
import net.blay09.mods.spookydoors.client.render.SpookyDoorBlockEntityRenderer;

public class ModRenderers {
    public static void initialize(BalmRenderers renderers) {
        renderers.registerBlockEntityRenderer(ModBlockEntities.spookyDoor::get, SpookyDoorBlockEntityRenderer::new);
    }
}
