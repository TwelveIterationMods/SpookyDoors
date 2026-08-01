package net.blay09.mods.spookydoors.legacy;

import net.blay09.mods.balm.api.Balm;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

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

    public static void initialize() {
        LEGACY_DOOR_TYPES.forEach(doorType -> {
            addDoorAlias(BuiltInRegistries.BLOCK, doorType);
            addDoorAlias(BuiltInRegistries.ITEM, doorType);
        });
    }

    private static <T> void addDoorAlias(Registry<T> registry, String doorType) {
        final var doorName = doorType + "_door";
        Balm.getRegistries().addAlias(registry, id("spooky_" + doorName), new ResourceLocation(doorName));
    }
}
