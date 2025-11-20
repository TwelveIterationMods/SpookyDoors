package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.core.BalmRegistrars;
import net.blay09.mods.spookydoors.network.ModNetworking;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;

public class SpookyDoors {

    public static final String MOD_ID = "spookydoors";

    public static void initialize(BalmRegistrars registrars) {
        registrars.blocks(ModBlocks::initialize);
        registrars.blockEntityTypes(ModBlockEntities::initialize);
        registrars.creativeModeTabs(ModItems::initialize);
        registrars.registrar(Registries.SOUND_EVENT, ModSounds::initialize);
        ModNetworking.initialize(Balm.networking());
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
