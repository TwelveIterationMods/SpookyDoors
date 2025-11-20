package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.world.level.block.BalmBlockRegistrar;
import net.blay09.mods.balm.world.level.block.DiscriminatedBlocks;
import net.blay09.mods.spookydoors.block.SpookyDoorBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;

import java.util.Set;

public class ModBlocks {

    public record DoorType(BlockSetType blockSetType, Block baseBlock) {
        public static final DoorType OAK = new DoorType(BlockSetType.OAK, Blocks.OAK_PLANKS);
        public static final DoorType SPRUCE = new DoorType(BlockSetType.SPRUCE, Blocks.SPRUCE_PLANKS);
        public static final DoorType BIRCH = new DoorType(BlockSetType.BIRCH, Blocks.BIRCH_PLANKS);
        public static final DoorType JUNGLE = new DoorType(BlockSetType.JUNGLE, Blocks.JUNGLE_PLANKS);
        public static final DoorType ACACIA = new DoorType(BlockSetType.ACACIA, Blocks.ACACIA_PLANKS);
        public static final DoorType CHERRY = new DoorType(BlockSetType.CHERRY, Blocks.CHERRY_PLANKS);
        public static final DoorType DARK_OAK = new DoorType(BlockSetType.DARK_OAK, Blocks.DARK_OAK_PLANKS);
        public static final DoorType MANGROVE = new DoorType(BlockSetType.MANGROVE, Blocks.MANGROVE_PLANKS);
        public static final DoorType BAMBOO = new DoorType(BlockSetType.BAMBOO, Blocks.BAMBOO_PLANKS);
    }

    public static DiscriminatedBlocks<DoorType> spookyDoors;

    public static void initialize(BalmBlockRegistrar blocks) {
        final var doorTypes = Set.of(
                DoorType.OAK,
                DoorType.SPRUCE,
                DoorType.BIRCH,
                DoorType.JUNGLE,
                DoorType.ACACIA,
                DoorType.CHERRY,
                DoorType.DARK_OAK,
                DoorType.MANGROVE,
                DoorType.BAMBOO
        );
        spookyDoors = blocks.registerDiscriminated(
                doorTypes,
                it -> "spooky_" + it.blockSetType().name(),
                (type, properties) -> new SpookyDoorBlock(type.blockSetType(), properties),
                (type, properties) -> doorProperties(type.baseBlock())
        ).asDiscriminatedBlocks();
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

}
