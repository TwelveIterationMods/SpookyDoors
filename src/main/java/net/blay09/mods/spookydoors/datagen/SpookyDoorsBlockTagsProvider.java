package net.blay09.mods.spookydoors.datagen;

import net.blay09.mods.spookydoors.ModBlocks;
import net.blay09.mods.spookydoors.SpookyDoors;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class SpookyDoorsBlockTagsProvider extends TagsProvider<Block> {
    protected SpookyDoorsBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.BLOCK, lookupProvider, SpookyDoors.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.WOODEN_DOORS).add(
                ModBlocks.SPOOKY_OAK_DOOR.getKey(),
                ModBlocks.SPOOKY_SPRUCE_DOOR.getKey(),
                ModBlocks.SPOOKY_BIRCH_DOOR.getKey(),
                ModBlocks.SPOOKY_JUNGLE_DOOR.getKey(),
                ModBlocks.SPOOKY_ACACIA_DOOR.getKey(),
                ModBlocks.SPOOKY_CHERRY_DOOR.getKey(),
                ModBlocks.SPOOKY_DARK_OAK_DOOR.getKey(),
                ModBlocks.SPOOKY_MANGROVE_DOOR.getKey(),
                ModBlocks.SPOOKY_BAMBOO_DOOR.getKey());
    }
}
