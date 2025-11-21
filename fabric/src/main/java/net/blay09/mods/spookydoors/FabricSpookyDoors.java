package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.fabric.platform.runtime.FabricLoadContext;
import net.fabricmc.api.ModInitializer;

public class FabricSpookyDoors implements ModInitializer {
    @Override
    public void onInitialize() {
        Balm.initializeMod(SpookyDoors.MOD_ID, FabricLoadContext.INSTANCE, SpookyDoors::initialize);
    }
}
