package net.blay09.mods.spookydoors.datagen;

import net.blay09.mods.spookydoors.item.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class SpookyDoorsItemTagsProvider extends FabricTagProvider<Item> {
    public SpookyDoorsItemTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.ITEM, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(ModItemTags.HAUNTS_DOORS).add(Items.GHAST_TEAR);
        getOrCreateTagBuilder(ModItemTags.EXORCISES_DOORS).add(Items.HONEYCOMB);
    }
}
