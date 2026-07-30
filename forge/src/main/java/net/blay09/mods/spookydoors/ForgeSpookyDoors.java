package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.EmptyLoadContext;
import net.blay09.mods.balm.api.client.BalmClient;
import net.blay09.mods.balm.forge.ForgeLoadContext;
import net.blay09.mods.spookydoors.client.SpookyDoorsClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(SpookyDoors.MOD_ID)
public class ForgeSpookyDoors {

    public ForgeSpookyDoors(IEventBus modEventBus) {
        final var context = new ForgeLoadContext(modEventBus);
        Balm.initializeMod(SpookyDoors.MOD_ID, context, SpookyDoors::initialize);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> BalmClient.initializeMod(SpookyDoors.MOD_ID, context, SpookyDoorsClient::initialize));
    }

}
