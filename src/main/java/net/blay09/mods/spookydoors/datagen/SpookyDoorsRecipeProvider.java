package net.blay09.mods.spookydoors.datagen;

import net.blay09.mods.spookydoors.ModBlocks;
import net.blay09.mods.spookydoors.block.SpookyDoorBlock;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.concurrent.CompletableFuture;

public class SpookyDoorsRecipeProvider extends RecipeProvider {
    public SpookyDoorsRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        shapelessDoor(output, Blocks.OAK_DOOR, ModBlocks.SPOOKY_OAK_DOOR);
        shapelessDoor(output, Blocks.SPRUCE_DOOR, ModBlocks.SPOOKY_SPRUCE_DOOR);
        shapelessDoor(output, Blocks.BIRCH_DOOR, ModBlocks.SPOOKY_BIRCH_DOOR);
        shapelessDoor(output, Blocks.JUNGLE_DOOR, ModBlocks.SPOOKY_JUNGLE_DOOR);
        shapelessDoor(output, Blocks.ACACIA_DOOR, ModBlocks.SPOOKY_ACACIA_DOOR);
        shapelessDoor(output, Blocks.CHERRY_DOOR, ModBlocks.SPOOKY_CHERRY_DOOR);
        shapelessDoor(output, Blocks.DARK_OAK_DOOR, ModBlocks.SPOOKY_DARK_OAK_DOOR);
        shapelessDoor(output, Blocks.MANGROVE_DOOR, ModBlocks.SPOOKY_MANGROVE_DOOR);
        shapelessDoor(output, Blocks.BAMBOO_DOOR, ModBlocks.SPOOKY_BAMBOO_DOOR);
    }

    private static void shapelessDoor(RecipeOutput output, Block block, DeferredBlock<SpookyDoorBlock> spookyBlock) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, spookyBlock)
                .requires(block)
                .unlockedBy(spookyBlock.getKey().location().getPath(), has(block))
                .save(output);
    }
}
