package net.blay09.mods.spookydoors.datagen;

import net.blay09.mods.spookydoors.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class SpookyDoorsBlockTagsProvider extends FabricTagProvider<Block> {
    public SpookyDoorsBlockTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.BLOCK, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(
                ModBlocks.spookyOakDoor,
                ModBlocks.spookySpruceDoor,
                ModBlocks.spookyBirchDoor,
                ModBlocks.spookyJungleDoor,
                ModBlocks.spookyAcaciaDoor,
                ModBlocks.spookyCherryDoor,
                ModBlocks.spookyDarkOakDoor,
                ModBlocks.spookyMangroveDoor,
                ModBlocks.spookyBambooDoor);
    }
}
