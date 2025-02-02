package net.blay09.mods.spookydoors.datagen;

import net.blay09.mods.spookydoors.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

public class SpookyDoorsItemTagsProvider extends FabricTagProvider<Item> {
    public SpookyDoorsItemTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.ITEM, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(ItemTags.WOODEN_DOORS)
                .add(ModBlocks.spookyOakDoor.asItem(),
                        ModBlocks.spookySpruceDoor.asItem(),
                        ModBlocks.spookyBirchDoor.asItem(),
                        ModBlocks.spookyJungleDoor.asItem(),
                        ModBlocks.spookyAcaciaDoor.asItem(),
                        ModBlocks.spookyCherryDoor.asItem(),
                        ModBlocks.spookyDarkOakDoor.asItem(),
                        ModBlocks.spookyMangroveDoor.asItem(),
                        ModBlocks.spookyBambooDoor.asItem());
    }
}
