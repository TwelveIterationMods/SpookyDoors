package net.blay09.mods.spookydoors;

import net.blay09.mods.spookydoors.block.SpookyDoorBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(SpookyDoors.MOD_ID);
    public static final DeferredBlock<SpookyDoorBlock> SPOOKY_OAK_DOOR = BLOCKS.register("spooky_oak_door",
            () -> new SpookyDoorBlock(BlockSetType.OAK, doorProperties(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<SpookyDoorBlock> SPOOKY_SPRUCE_DOOR = BLOCKS.register("spooky_spruce_door",
            () -> new SpookyDoorBlock(BlockSetType.SPRUCE, doorProperties(Blocks.SPRUCE_PLANKS)));
    public static final DeferredBlock<SpookyDoorBlock> SPOOKY_BIRCH_DOOR = BLOCKS.register("spooky_birch_door",
            () -> new SpookyDoorBlock(BlockSetType.BIRCH, doorProperties(Blocks.BIRCH_PLANKS)));
    public static final DeferredBlock<SpookyDoorBlock> SPOOKY_JUNGLE_DOOR = BLOCKS.register("spooky_jungle_door",
            () -> new SpookyDoorBlock(BlockSetType.JUNGLE, doorProperties(Blocks.JUNGLE_PLANKS)));
    public static final DeferredBlock<SpookyDoorBlock> SPOOKY_ACACIA_DOOR = BLOCKS.register("spooky_acacia_door",
            () -> new SpookyDoorBlock(BlockSetType.ACACIA, doorProperties(Blocks.ACACIA_PLANKS)));
    public static final DeferredBlock<SpookyDoorBlock> SPOOKY_CHERRY_DOOR = BLOCKS.register("spooky_cherry_door",
            () -> new SpookyDoorBlock(BlockSetType.CHERRY, doorProperties(Blocks.CHERRY_PLANKS)));
    public static final DeferredBlock<SpookyDoorBlock> SPOOKY_DARK_OAK_DOOR = BLOCKS.register("spooky_dark_oak_door",
            () -> new SpookyDoorBlock(BlockSetType.DARK_OAK, doorProperties(Blocks.DARK_OAK_PLANKS)));
    public static final DeferredBlock<SpookyDoorBlock> SPOOKY_MANGROVE_DOOR = BLOCKS.register("spooky_mangrove_door",
            () -> new SpookyDoorBlock(BlockSetType.MANGROVE, doorProperties(Blocks.MANGROVE_PLANKS)));
    public static final DeferredBlock<SpookyDoorBlock> SPOOKY_BAMBOO_DOOR = BLOCKS.register("spooky_bamboo_door",
            () -> new SpookyDoorBlock(BlockSetType.BAMBOO, doorProperties(Blocks.BAMBOO_PLANKS)));

    private static BlockBehaviour.Properties doorProperties(Block baseBlock) {
        return BlockBehaviour.Properties.of()
                .mapColor(baseBlock.defaultMapColor())
                .instrument(NoteBlockInstrument.BASS)
                .strength(3f)
                .noOcclusion()
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY);
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
