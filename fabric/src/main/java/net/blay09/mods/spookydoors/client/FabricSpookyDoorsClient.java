package net.blay09.mods.spookydoors.client;

import net.blay09.mods.balm.client.BalmClient;
import net.blay09.mods.balm.fabric.platform.runtime.FabricLoadContext;
import net.blay09.mods.spookydoors.SpookyDoors;
import net.fabricmc.api.ClientModInitializer;

public class FabricSpookyDoorsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BalmClient.initializeMod(SpookyDoors.MOD_ID, FabricLoadContext.INSTANCE, SpookyDoorsClient::initialize);
    }
}

