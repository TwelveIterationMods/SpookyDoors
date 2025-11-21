package net.blay09.mods.spookydoors.datagen;

import net.blay09.mods.balm.world.level.block.DeferredBlock;
import net.blay09.mods.spookydoors.ModBlocks;
import net.blay09.mods.spookydoors.block.SpookyDoorBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class SpookyDoorsRecipeProvider extends FabricRecipeProvider {
    public SpookyDoorsRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
        return new RecipeProvider(provider, recipeOutput) {
            @Override
            public void buildRecipes() {
                ModBlocks.spookyDoors.forEach((type, block) -> shapelessDoor(output, Blocks.OAK_DOOR, block));
            }

            private void shapelessDoor(RecipeOutput output, Block block, DeferredBlock spookyBlock) {
                shapeless(RecipeCategory.MISC, spookyBlock)
                        .requires(block)
                        .unlockedBy(BuiltInRegistries.BLOCK.getKey(spookyBlock.asBlock()).getPath(), has(block))
                        .save(output);
            }
        };
    }

    @Override
    public String getName() {
        return "spookydoors";
    }
}