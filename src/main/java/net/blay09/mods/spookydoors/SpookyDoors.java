package net.blay09.mods.spookydoors;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(SpookyDoors.MOD_ID)
public class SpookyDoors {
    public static final String MOD_ID = "spookydoors";

    // TODO test multiplayer
    // TODO quick open

    public SpookyDoors(IEventBus modEventBus, ModContainer modContainer) {
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModSounds.register(modEventBus);
    }
}
