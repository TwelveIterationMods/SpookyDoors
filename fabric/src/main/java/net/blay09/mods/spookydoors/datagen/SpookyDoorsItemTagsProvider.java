package net.blay09.mods.spookydoors.datagen;

import net.blay09.mods.spookydoors.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

public class SpookyDoorsItemTagsProvider extends IntrinsicHolderTagsProvider<Item> {
    public SpookyDoorsItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.ITEM, registriesFuture, (item) -> item.builtInRegistryHolder().key());
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        final var woodenDoors = tag(ItemTags.WOODEN_DOORS);
        ModBlocks.spookyDoors.forEach((type, block) -> woodenDoors.add(block.asItem()));
    }
}
