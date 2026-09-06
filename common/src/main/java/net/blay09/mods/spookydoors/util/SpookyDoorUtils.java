package net.blay09.mods.spookydoors.util;

import net.blay09.mods.spookydoors.block.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.function.BiConsumer;

public class SpookyDoorUtils {
    public static boolean isSupportedDoor(BlockState state) {
        return state.is(ModBlockTags.DOORS) && !state.is(ModBlockTags.EXCLUDED_DOORS);
    }

    public static boolean canOperate(BlockState state) {
        return isSupportedDoor(state) && state.getBlock() instanceof DoorBlock doorBlock && doorBlock.type().canOpenByHand();
    }

    public static BlockPos getBasePos(BlockPos pos, BlockState state) {
        if (state.hasProperty(DoorBlock.HALF) && state.getValue(DoorBlock.HALF) == DoubleBlockHalf.UPPER) {
            return pos.below();
        }

        return pos;
    }

    public static float getDefaultOpenness(BlockState state) {
        return state.hasProperty(DoorBlock.OPEN) && state.getValue(DoorBlock.OPEN) ? 1f : 0f;
    }

    public static void forEachSupportedDoor(Level level, LevelChunk chunk, BiConsumer<BlockPos, BlockState> consumer) {
        final var chunkPos = chunk.getPos();
        final var minX = chunkPos.getMinBlockX();
        final var minZ = chunkPos.getMinBlockZ();
        final var sections = chunk.getSections();
        for (var sectionIndex = 0; sectionIndex < sections.length; sectionIndex++) {
            final var section = sections[sectionIndex];
            if (section == null || section.hasOnlyAir() || !section.maybeHas(SpookyDoorUtils::isSupportedDoor)) {
                continue;
            }

            final var minY = level.getSectionYFromSectionIndex(sectionIndex) << 4;
            for (var localY = 0; localY < 16; localY++) {
                for (var localZ = 0; localZ < 16; localZ++) {
                    for (var localX = 0; localX < 16; localX++) {
                        final var state = section.getBlockState(localX, localY, localZ);
                        if (isSupportedDoor(state)) {
                            final var pos = new BlockPos(minX + localX, minY + localY, minZ + localZ);
                            if (getBasePos(pos, state).equals(pos)) {
                                consumer.accept(pos, state);
                            }
                        }
                    }
                }
            }
        }
    }
}
