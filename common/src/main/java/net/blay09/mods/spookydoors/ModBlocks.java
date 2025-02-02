package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.api.block.BalmBlocks;
import net.blay09.mods.spookydoors.block.SpookyDoorBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
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
        blocks.register((identifier) -> spookyOakDoor = new SpookyDoorBlock(BlockSetType.OAK, doorProperties(identifier, Blocks.OAK_PLANKS)),
                ModBlocks::itemBlock,
                id("spooky_oak_door"));
        blocks.register((identifier) -> spookySpruceDoor = new SpookyDoorBlock(BlockSetType.SPRUCE, doorProperties(identifier, Blocks.SPRUCE_PLANKS)),
                ModBlocks::itemBlock,
                id("spooky_spruce_door"));
        blocks.register((identifier) -> spookyBirchDoor = new SpookyDoorBlock(BlockSetType.BIRCH, doorProperties(identifier, Blocks.BIRCH_PLANKS)),
                ModBlocks::itemBlock,
                id("spooky_birch_door"));
        blocks.register((identifier) -> spookyJungleDoor = new SpookyDoorBlock(BlockSetType.JUNGLE, doorProperties(identifier, Blocks.JUNGLE_PLANKS)),
                ModBlocks::itemBlock,
                id("spooky_jungle_door"));
        blocks.register((identifier) -> spookyAcaciaDoor = new SpookyDoorBlock(BlockSetType.ACACIA, doorProperties(identifier, Blocks.ACACIA_PLANKS)),
                ModBlocks::itemBlock,
                id("spooky_acacia_door"));
        blocks.register((identifier) -> spookyCherryDoor = new SpookyDoorBlock(BlockSetType.CHERRY, doorProperties(identifier, Blocks.CHERRY_PLANKS)),
                ModBlocks::itemBlock,
                id("spooky_cherry_door"));
        blocks.register((identifier) -> spookyDarkOakDoor = new SpookyDoorBlock(BlockSetType.DARK_OAK, doorProperties(identifier, Blocks.DARK_OAK_PLANKS)),
                ModBlocks::itemBlock,
                id("spooky_dark_oak_door"));
        blocks.register((identifier) -> spookyMangroveDoor = new SpookyDoorBlock(BlockSetType.MANGROVE, doorProperties(identifier, Blocks.MANGROVE_PLANKS)),
                ModBlocks::itemBlock,
                id("spooky_mangrove_door"));
        blocks.register((identifier) -> spookyBambooDoor = new SpookyDoorBlock(BlockSetType.BAMBOO, doorProperties(identifier, Blocks.BAMBOO)),
                ModBlocks::itemBlock,
                id("spooky_bamboo_door"));
    }

    private static BlockBehaviour.Properties doorProperties(ResourceLocation identifier, Block baseBlock) {
        return BlockBehaviour.Properties.of()
                .setId(blockId(identifier))
                .mapColor(baseBlock.defaultMapColor())
                .instrument(NoteBlockInstrument.BASS)
                .strength(3f)
                .noOcclusion()
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY);
    }

    private static BlockItem itemBlock(Block block, ResourceLocation name) {
        return new BlockItem(block, defaultItemProperties(name));
    }

    private static Item.Properties defaultItemProperties(ResourceLocation identifier) {
        return new Item.Properties().setId(itemId(identifier));
    }

    private static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(SpookyDoors.MOD_ID, name);
    }

    private static ResourceKey<Block> blockId(ResourceLocation identifier) {
        return ResourceKey.create(Registries.BLOCK, identifier);
    }

    private static ResourceKey<Item> itemId(ResourceLocation identifier) {
        return ResourceKey.create(Registries.ITEM, identifier);
    }

}
