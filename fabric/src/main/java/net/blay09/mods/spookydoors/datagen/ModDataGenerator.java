package net.blay09.mods.spookydoors.datagen;

import net.blay09.mods.spookydoors.block.ModBlockTags;
import net.blay09.mods.spookydoors.item.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        final var pack = fabricDataGenerator.createPack();
        pack.addProvider(ModBlockTagProvider::new);
        pack.addProvider(ModItemTagProvider::new);
    }

    private static class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {

        public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
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
                    .add(Blocks.CRIMSON_DOOR)
                    .add(Blocks.WARPED_DOOR);
            getOrCreateTagBuilder(ModBlockTags.EXCLUDED_DOORS);
        }
    }

    private static class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

        public ModItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            getOrCreateTagBuilder(ModItemTags.HAUNTS_DOORS)
                    .add(Items.GHAST_TEAR);
            getOrCreateTagBuilder(ModItemTags.EXORCISES_DOORS)
                    .add(Items.HONEYCOMB);
        }
    }
}
