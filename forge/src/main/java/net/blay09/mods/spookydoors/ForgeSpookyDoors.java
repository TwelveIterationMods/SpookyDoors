package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.BalmClient;
import net.blay09.mods.balm.forge.platform.runtime.ForgeLoadContext;
import net.blay09.mods.spookydoors.client.SpookyDoorsClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SpookyDoors.MOD_ID)
public class ForgeSpookyDoors {

    public ForgeSpookyDoors(FMLJavaModLoadingContext context) {
        final var loadContext = new ForgeLoadContext(context.getModBusGroup());
        Balm.initializeMod(SpookyDoors.MOD_ID, loadContext, SpookyDoors::initialize);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> BalmClient.initializeMod(SpookyDoors.MOD_ID, loadContext, SpookyDoorsClient::initialize));
    }

}
