package net.blay09.mods.spookydoors.datagen;

import net.blay09.mods.spookydoors.block.ModBlockTags;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ModDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        final var pack = fabricDataGenerator.createPack();

        pack.addProvider(SpookyDoorsBlockTagsProvider::new);
        pack.addProvider(SpookyDoorsItemTagsProvider::new);
    }

    private static class SpookyDoorsBlockTagsProvider extends FabricTagProvider<Block> {

        public SpookyDoorsBlockTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, Registries.BLOCK, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            getOrCreateTagBuilder(ModBlockTags.DOORS)
                    .add(Blocks.OAK_DOOR)
                    .add(Blocks.SPRUCE_DOOR)
                    .add(Blocks.BIRCH_DOOR)
                    .add(Blocks.JUNGLE_DOOR)
                    .add(Blocks.ACACIA_DOOR)
                    .add(Blocks.DARK_OAK_DOOR)
                    .add(Blocks.MANGROVE_DOOR)
                    .add(Blocks.CHERRY_DOOR)
                    .add(Blocks.BAMBOO_DOOR)
                    .add(Blocks.COPPER_DOOR)
                    .add(Blocks.EXPOSED_COPPER_DOOR)
                    .add(Blocks.WEATHERED_COPPER_DOOR)
                    .add(Blocks.OXIDIZED_COPPER_DOOR)
                    .add(Blocks.WAXED_COPPER_DOOR)
                    .add(Blocks.WAXED_EXPOSED_COPPER_DOOR)
                    .add(Blocks.WAXED_WEATHERED_COPPER_DOOR)
                    .add(Blocks.WAXED_OXIDIZED_COPPER_DOOR)
                    .add(Blocks.CRIMSON_DOOR)
                    .add(Blocks.WARPED_DOOR);
            getOrCreateTagBuilder(ModBlockTags.EXCLUDED_DOORS);
        }
    }
}
