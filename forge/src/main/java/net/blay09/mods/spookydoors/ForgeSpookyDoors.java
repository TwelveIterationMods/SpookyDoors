package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.EmptyLoadContext;
import net.blay09.mods.balm.api.client.BalmClient;
import net.blay09.mods.spookydoors.client.SpookyDoorsClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(SpookyDoors.MOD_ID)
public class ForgeSpookyDoors {

    public ForgeSpookyDoors() {
        Balm.initialize(SpookyDoors.MOD_ID, EmptyLoadContext.INSTANCE, SpookyDoors::initialize);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> BalmClient.initialize(SpookyDoors.MOD_ID, EmptyLoadContext.INSTANCE, SpookyDoorsClient::initialize));
    }

}
