package net.blay09.mods.spookydoors.fabric.datagen;

import net.blay09.mods.spookydoors.block.ModBlockTags;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ModDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        final var pack = fabricDataGenerator.createPack();
        pack.addProvider(SpookyDoorsBlockTagsProvider::new);
        pack.addProvider(SpookyDoorsItemTagsProvider::new);
    }

    private static class SpookyDoorsBlockTagsProvider extends FabricTagsProvider.BlockTagsProvider {

        public SpookyDoorsBlockTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
            super(output, registryLookupFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider registries) {
            valueLookupBuilder(ModBlockTags.DOORS)
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
            valueLookupBuilder(ModBlockTags.EXCLUDED_DOORS);
        }
    }
}
