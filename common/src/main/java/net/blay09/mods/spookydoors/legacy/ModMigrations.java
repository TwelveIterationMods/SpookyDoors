package net.blay09.mods.spookydoors.legacy;

import net.blay09.mods.balm.core.BalmRegistrar;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;

import java.util.List;

import static net.blay09.mods.spookydoors.SpookyDoors.id;

public class ModMigrations {
    private static final List<String> LEGACY_DOOR_TYPES = List.of(
            "oak",
            "spruce",
            "birch",
            "acacia",
            "cherry",
            "jungle",
            "dark_oak",
            "mangrove",
            "bamboo",
            "crimson",
            "warped"
    );

    public static void initialize(BalmRegistrar registrar) {
        LEGACY_DOOR_TYPES.forEach(doorType -> {
            String doorName = doorType + "_door";
            registrar.addAlias(Registries.BLOCK, id("spooky_" + doorName), Identifier.withDefaultNamespace(doorName));
            registrar.addAlias(Registries.ITEM, id("spooky_" + doorName), Identifier.withDefaultNamespace(doorName));
        });
    }
}
