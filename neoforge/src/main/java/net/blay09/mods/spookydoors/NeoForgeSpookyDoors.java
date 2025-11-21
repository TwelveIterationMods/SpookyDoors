package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.neoforge.platform.runtime.NeoForgeLoadContext;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(SpookyDoors.MOD_ID)
public class NeoForgeSpookyDoors {

    public NeoForgeSpookyDoors(IEventBus modEventBus) {
        final var context = new NeoForgeLoadContext(modEventBus);
        Balm.initializeMod(SpookyDoors.MOD_ID, context, SpookyDoors::initialize);
    }
}
