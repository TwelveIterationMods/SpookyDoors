package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.EmptyLoadContext;
import net.fabricmc.api.ModInitializer;

public class FabricSpookyDoors implements ModInitializer {
    @Override
    public void onInitialize() {
        Balm.initialize(SpookyDoors.MOD_ID, EmptyLoadContext.INSTANCE, SpookyDoors::initialize);
    }
}
