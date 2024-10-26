package net.blay09.mods.spookydoors.datagen;

import net.blay09.mods.spookydoors.ModItems;
import net.blay09.mods.spookydoors.SpookyDoors;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class SpookyDoorsItemTagsProvider extends TagsProvider<Item> {
    protected SpookyDoorsItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.ITEM, lookupProvider, SpookyDoors.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.WOODEN_DOORS).add(
                ModItems.SPOOKY_OAK_DOOR_ITEM.getKey(),
                ModItems.SPOOKY_SPRUCE_DOOR_ITEM.getKey(),
                ModItems.SPOOKY_BIRCH_DOOR_ITEM.getKey(),
                ModItems.SPOOKY_JUNGLE_DOOR_ITEM.getKey(),
                ModItems.SPOOKY_ACACIA_DOOR_ITEM.getKey(),
                ModItems.SPOOKY_CHERRY_DOOR_ITEM.getKey(),
                ModItems.SPOOKY_DARK_OAK_DOOR_ITEM.getKey(),
                ModItems.SPOOKY_MANGROVE_DOOR_ITEM.getKey(),
                ModItems.SPOOKY_BAMBOO_DOOR_ITEM.getKey());
    }
}
