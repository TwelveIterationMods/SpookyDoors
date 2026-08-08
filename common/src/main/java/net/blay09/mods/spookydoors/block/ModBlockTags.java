package net.blay09.mods.spookydoors.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static net.blay09.mods.spookydoors.SpookyDoors.id;

public class ModBlockTags {

    public static final TagKey<Block> DOORS = TagKey.create(Registries.BLOCK, id("doors"));
    public static final TagKey<Block> EXCLUDED_DOORS = TagKey.create(Registries.BLOCK, id("excluded_doors"));
}
