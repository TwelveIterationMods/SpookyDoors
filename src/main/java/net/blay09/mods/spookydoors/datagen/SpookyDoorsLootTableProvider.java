package net.blay09.mods.spookydoors.datagen;

import net.blay09.mods.spookydoors.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Set;

public class SpookyDoorsLootTableProvider extends BlockLootSubProvider {
    protected SpookyDoorsLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries()
                .stream()
                .map(it -> (Block) it.value())
                .toList();
    }

    @Override
    protected void generate() {
        add(ModBlocks.SPOOKY_OAK_DOOR.get(), this::createDoorTable);
        add(ModBlocks.SPOOKY_SPRUCE_DOOR.get(), this::createDoorTable);
        add(ModBlocks.SPOOKY_BIRCH_DOOR.get(), this::createDoorTable);
        add(ModBlocks.SPOOKY_JUNGLE_DOOR.get(), this::createDoorTable);
        add(ModBlocks.SPOOKY_ACACIA_DOOR.get(), this::createDoorTable);
        add(ModBlocks.SPOOKY_CHERRY_DOOR.get(), this::createDoorTable);
        add(ModBlocks.SPOOKY_DARK_OAK_DOOR.get(), this::createDoorTable);
        add(ModBlocks.SPOOKY_MANGROVE_DOOR.get(), this::createDoorTable);
        add(ModBlocks.SPOOKY_BAMBOO_DOOR.get(), this::createDoorTable);
    }

    @Override
    protected void add(Block block, LootTable.Builder builder) {
        super.add(block, builder);
    }
}
