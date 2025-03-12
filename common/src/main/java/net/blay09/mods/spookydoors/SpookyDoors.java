package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.spookydoors.network.ModNetworking;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpookyDoors {

    public static final Logger logger = LoggerFactory.getLogger(SpookyDoors.class);

    public static final String MOD_ID = "spookydoors";

    public static void initialize() {
        ModBlocks.initialize(Balm.getBlocks());
        ModBlockEntities.initialize(Balm.getBlockEntities());
        ModItems.initialize(Balm.getItems());
        ModNetworking.initialize(Balm.getNetworking());
        ModSounds.initialize(Balm.getSounds());
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
