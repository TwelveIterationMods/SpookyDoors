package net.blay09.mods.spookydoors.datagen;

import net.blay09.mods.spookydoors.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class SpookyDoorsBlockTagsProvider extends IntrinsicHolderTagsProvider<Block> {
    public SpookyDoorsBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.BLOCK, registriesFuture, (block) -> block.builtInRegistryHolder().key());
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_AXE).add(
                ModBlocks.spookyOakDoor,
                ModBlocks.spookySpruceDoor,
                ModBlocks.spookyBirchDoor,
                ModBlocks.spookyJungleDoor,
                ModBlocks.spookyAcaciaDoor,
                ModBlocks.spookyCherryDoor,
                ModBlocks.spookyDarkOakDoor,
                ModBlocks.spookyMangroveDoor,
                ModBlocks.spookyBambooDoor
        );

        tag(BlockTags.WOODEN_DOORS).add(
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
