package net.blay09.mods.spookydoors.datagen;

import net.blay09.mods.spookydoors.ModBlocks;
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
        final var mineableWithAxe = tag(BlockTags.MINEABLE_WITH_AXE);
        final var woodenDoors = tag(BlockTags.WOODEN_DOORS);
        ModBlocks.spookyDoors.forEach((type, block) -> {
            mineableWithAxe.add(block.asBlock());
            woodenDoors.add(block.asBlock());
        });
    }
}
