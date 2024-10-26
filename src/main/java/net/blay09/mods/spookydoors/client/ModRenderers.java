package net.blay09.mods.spookydoors.client;

import net.blay09.mods.spookydoors.ModBlockEntities;
import net.blay09.mods.spookydoors.SpookyDoors;
import net.blay09.mods.spookydoors.client.render.SpookyDoorBlockEntityRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = SpookyDoors.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ModRenderers {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.SPOOKY_DOOR.get(), SpookyDoorBlockEntityRenderer::new);
    }
}
