package net.blay09.mods.spookydoors.client;

import net.blay09.mods.balm.api.client.rendering.BalmRenderers;
import net.blay09.mods.spookydoors.ModBlockEntities;
import net.blay09.mods.spookydoors.client.render.SpookyDoorBlockEntityRenderer;

import static net.blay09.mods.spookydoors.SpookyDoors.id;

public class ModRenderers {
    public static void initialize(BalmRenderers renderers) {
        renderers.registerBlockEntityRenderer(id("spooky_door"), ModBlockEntities.spookyDoor::get, SpookyDoorBlockEntityRenderer::new);
    }
}
