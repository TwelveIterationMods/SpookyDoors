package net.blay09.mods.spookydoors.fabric.datagen;

import net.blay09.mods.spookydoors.block.ModBlockTags;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.BlockItemId;
import net.minecraft.references.BlockItemIds;

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
            builder(ModBlockTags.DOORS)
                    .add(BlockItemIds.OAK_DOOR)
                    .add(BlockItemIds.SPRUCE_DOOR)
                    .add(BlockItemIds.BIRCH_DOOR)
                    .add(BlockItemIds.JUNGLE_DOOR)
                    .add(BlockItemIds.ACACIA_DOOR)
                    .add(BlockItemIds.DARK_OAK_DOOR)
                    .add(BlockItemIds.MANGROVE_DOOR)
                    .add(BlockItemIds.CHERRY_DOOR)
                    .add(BlockItemIds.BAMBOO_DOOR)
                    .add(BlockItemIds.CRIMSON_DOOR)
                    .add(BlockItemIds.WARPED_DOOR)
                    .addAll(BlockItemIds.COPPER_DOOR.map(BlockItemId::block));
            builder(ModBlockTags.EXCLUDED_DOORS);
        }
    }
}
