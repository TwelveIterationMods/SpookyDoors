package net.blay09.mods.spookydoors.legacy;

import net.blay09.mods.balm.core.BalmRegistrar;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.properties.WoodType;

import static net.blay09.mods.spookydoors.SpookyDoors.id;

public class ModMigrations {
    public static void initialize(BalmRegistrar registrar) {
        WoodType.values().forEach(woodType -> {
            String doorName = woodType.name() + "_door";
            registrar.addAlias(Registries.BLOCK, id("spooky_" + doorName), Identifier.withDefaultNamespace(doorName));
            registrar.addAlias(Registries.ITEM, id("spooky_" + doorName), Identifier.withDefaultNamespace(doorName));
        });
    }
}
