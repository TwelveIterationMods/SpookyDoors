package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.block.BalmBlocks;
import net.blay09.mods.spookydoors.block.SpookyDoorBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;

public class ModBlocks {
    public static SpookyDoorBlock spookyOakDoor;
    public static SpookyDoorBlock spookySpruceDoor;
    public static SpookyDoorBlock spookyBirchDoor;
    public static SpookyDoorBlock spookyJungleDoor;
    public static SpookyDoorBlock spookyAcaciaDoor;
    public static SpookyDoorBlock spookyCherryDoor;
    public static SpookyDoorBlock spookyDarkOakDoor;
    public static SpookyDoorBlock spookyMangroveDoor;
    public static SpookyDoorBlock spookyBambooDoor;

    public static void initialize(BalmBlocks blocks) {
        blocks.register(() -> spookyOakDoor = new SpookyDoorBlock(BlockSetType.OAK, doorProperties(Blocks.OAK_PLANKS)),
                () -> itemBlock(spookyOakDoor),
                id("spooky_oak_door"));
        blocks.register(() -> spookySpruceDoor = new SpookyDoorBlock(BlockSetType.SPRUCE, doorProperties(Blocks.SPRUCE_PLANKS)),
                () -> itemBlock(spookySpruceDoor),
                id("spooky_spruce_door"));
        blocks.register(() -> spookyBirchDoor = new SpookyDoorBlock(BlockSetType.BIRCH, doorProperties(Blocks.BIRCH_PLANKS)),
                () -> itemBlock(spookyBirchDoor),
                id("spooky_birch_door"));
        blocks.register(() -> spookyJungleDoor = new SpookyDoorBlock(BlockSetType.JUNGLE, doorProperties(Blocks.JUNGLE_PLANKS)),
                () -> itemBlock(spookyJungleDoor),
                id("spooky_jungle_door"));
        blocks.register(() -> spookyAcaciaDoor = new SpookyDoorBlock(BlockSetType.ACACIA, doorProperties(Blocks.ACACIA_PLANKS)),
                () -> itemBlock(spookyAcaciaDoor),
                id("spooky_acacia_door"));
        blocks.register(() -> spookyCherryDoor = new SpookyDoorBlock(BlockSetType.CHERRY, doorProperties(Blocks.CHERRY_PLANKS)),
                () -> itemBlock(spookyCherryDoor),
                id("spooky_cherry_door"));
        blocks.register(() -> spookyDarkOakDoor = new SpookyDoorBlock(BlockSetType.DARK_OAK, doorProperties(Blocks.DARK_OAK_PLANKS)),
                () -> itemBlock(spookyDarkOakDoor),
                id("spooky_dark_oak_door"));
        blocks.register(() -> spookyMangroveDoor = new SpookyDoorBlock(BlockSetType.MANGROVE, doorProperties(Blocks.MANGROVE_PLANKS)),
                () -> itemBlock(spookyMangroveDoor),
                id("spooky_mangrove_door"));
        blocks.register(() -> spookyBambooDoor = new SpookyDoorBlock(BlockSetType.BAMBOO, doorProperties(Blocks.BAMBOO)),
                () -> itemBlock(spookyBambooDoor),
                id("spooky_bamboo_door"));
    }

    private static BlockBehaviour.Properties doorProperties(Block baseBlock) {
        return BlockBehaviour.Properties.of()
                .mapColor(baseBlock.defaultMapColor())
                .instrument(NoteBlockInstrument.BASS)
                .strength(3f)
                .noOcclusion()
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY);
    }

    private static BlockItem itemBlock(Block block) {
        return new BlockItem(block, Balm.getItems().itemProperties());
    }

    private static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(SpookyDoors.MOD_ID, name);
    }
}
