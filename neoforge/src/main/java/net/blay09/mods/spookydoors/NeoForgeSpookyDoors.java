package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.neoforge.NeoForgeLoadContext;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(SpookyDoors.MOD_ID)
public class NeoForgeSpookyDoors {

    public NeoForgeSpookyDoors(IEventBus modEventBus) {
        final var context = new NeoForgeLoadContext(modEventBus);
        Balm.initialize(SpookyDoors.MOD_ID, context, SpookyDoors::initialize);
    }
}
