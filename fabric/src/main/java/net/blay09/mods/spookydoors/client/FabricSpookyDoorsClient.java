package net.blay09.mods.spookydoors.client;

import net.blay09.mods.balm.api.EmptyLoadContext;
import net.blay09.mods.balm.api.client.BalmClient;
import net.blay09.mods.spookydoors.SpookyDoors;
import net.fabricmc.api.ClientModInitializer;

public class FabricSpookyDoorsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BalmClient.initialize(SpookyDoors.MOD_ID, EmptyLoadContext.INSTANCE, SpookyDoorsClient::initialize);
    }
}

