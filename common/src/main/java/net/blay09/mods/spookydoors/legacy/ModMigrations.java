package net.blay09.mods.spookydoors.legacy;

import net.blay09.mods.balm.api.Balm;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;

import static net.blay09.mods.spookydoors.SpookyDoors.id;

public class ModMigrations {
    public static void initialize() {
        WoodType.values().forEach(woodType -> {
            addDoorAlias(BuiltInRegistries.BLOCK, woodType);
            addDoorAlias(BuiltInRegistries.ITEM, woodType);
        });
    }

    private static <T> void addDoorAlias(Registry<T> registry, WoodType woodType) {
        String doorName = woodType.name() + "_door";
        Balm.getRegistries().addAlias(registry, id("spooky_" + doorName), new ResourceLocation(doorName));
    }
}
