package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.spookydoors.legacy.ModMigrations;
import net.blay09.mods.spookydoors.network.ModNetworking;
import net.blay09.mods.spookydoors.sounds.ModSounds;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpookyDoors {

    public static final Logger logger = LoggerFactory.getLogger(SpookyDoors.class);

    public static final String MOD_ID = "spookydoors";

    public static void initialize() {
        ModMigrations.initialize();
        ModNetworking.initialize(Balm.getNetworking());
        ModSounds.initialize(Balm.getSounds());
    }

    public static ResourceLocation id(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}
