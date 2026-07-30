package net.blay09.mods.spookydoors.datagen;

import net.blay09.mods.spookydoors.item.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        final var pack = fabricDataGenerator.createPack();
        pack.addProvider(ModItemTagProvider::new);
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
