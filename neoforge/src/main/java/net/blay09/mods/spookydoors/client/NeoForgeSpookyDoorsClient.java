package net.blay09.mods.spookydoors.client;

import net.blay09.mods.balm.api.client.BalmClient;
import net.blay09.mods.balm.neoforge.NeoForgeLoadContext;
import net.blay09.mods.spookydoors.SpookyDoors;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(value = SpookyDoors.MOD_ID, dist = Dist.CLIENT)
public class NeoForgeSpookyDoorsClient {

    public NeoForgeSpookyDoorsClient(IEventBus modEventBus) {
        final var context = new NeoForgeLoadContext(modEventBus);
        BalmClient.initializeMod(SpookyDoors.MOD_ID, context, SpookyDoorsClient::initialize);
    }
}
