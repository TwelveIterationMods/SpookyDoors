package net.blay09.mods.spookydoors.datagen;

import net.blay09.mods.spookydoors.item.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class SpookyDoorsItemTagsProvider extends FabricTagsProvider.ItemTagsProvider {
    public SpookyDoorsItemTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        valueLookupBuilder(ModItemTags.HAUNTS_DOORS).add(Items.GHAST_TEAR);
        valueLookupBuilder(ModItemTags.EXORCISES_DOORS).add(Items.HONEYCOMB);
    }
}
