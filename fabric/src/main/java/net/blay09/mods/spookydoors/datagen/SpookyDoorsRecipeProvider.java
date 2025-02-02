package net.blay09.mods.spookydoors.datagen;

import net.blay09.mods.spookydoors.ModBlocks;
import net.blay09.mods.spookydoors.block.SpookyDoorBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

public class SpookyDoorsRecipeProvider extends FabricRecipeProvider {
    public SpookyDoorsRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> output) {
        shapelessDoor(output, Blocks.OAK_DOOR, ModBlocks.spookyOakDoor);
        shapelessDoor(output, Blocks.SPRUCE_DOOR, ModBlocks.spookySpruceDoor);
        shapelessDoor(output, Blocks.BIRCH_DOOR, ModBlocks.spookyBirchDoor);
        shapelessDoor(output, Blocks.JUNGLE_DOOR, ModBlocks.spookyJungleDoor);
        shapelessDoor(output, Blocks.ACACIA_DOOR, ModBlocks.spookyAcaciaDoor);
        shapelessDoor(output, Blocks.CHERRY_DOOR, ModBlocks.spookyCherryDoor);
        shapelessDoor(output, Blocks.DARK_OAK_DOOR, ModBlocks.spookyDarkOakDoor);
        shapelessDoor(output, Blocks.MANGROVE_DOOR, ModBlocks.spookyMangroveDoor);
        shapelessDoor(output, Blocks.BAMBOO_DOOR, ModBlocks.spookyBambooDoor);
    }

    private static void shapelessDoor(Consumer<FinishedRecipe> output, Block block, SpookyDoorBlock spookyBlock) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, spookyBlock)
                .requires(block)
                .unlockedBy(BuiltInRegistries.BLOCK.getKey(spookyBlock).getPath(), has(block))
                .save(output);
    }
}
