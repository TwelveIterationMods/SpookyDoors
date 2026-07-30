package net.blay09.mods.spookydoors.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static net.blay09.mods.spookydoors.SpookyDoors.id;

public class ModItemTags {

    public static final TagKey<Item> HAUNTS_DOORS = TagKey.create(Registries.ITEM, id("haunts_doors"));
    public static final TagKey<Item> EXORCISES_DOORS = TagKey.create(Registries.ITEM, id("exorcises_doors"));
}
