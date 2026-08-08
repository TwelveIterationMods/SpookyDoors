package net.blay09.mods.spookydoors.client;

import net.blay09.mods.spookydoors.core.ClientSpookyDoor;
import net.blay09.mods.spookydoors.core.SpookyDoor;
import net.blay09.mods.spookydoors.core.SpookyDoorProvider;
import net.blay09.mods.spookydoors.util.SpookyDoorUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.*;

public final class SpookyDoorClientTracking implements SpookyDoorProvider {

    private static final Map<Level, SpookyDoorClientTracking> LEVEL_STATES = new WeakHashMap<>();

    private final WeakReference<Level> level;
    private final Map<Long, ClientSpookyDoor> doors = new HashMap<>();

    private SpookyDoorClientTracking(Level level) {
        this.level = new WeakReference<>(level);
    }

    public static SpookyDoorClientTracking get(Level level) {
        return LEVEL_STATES.computeIfAbsent(level, SpookyDoorClientTracking::new);
    }

    @Override
    public ClientSpookyDoor at(BlockPos pos) {
        final var level = Objects.requireNonNull(this.level.get());
        final var basePos = SpookyDoorUtils.getBasePos(pos, level.getBlockState(pos));
        return getOrCreate(basePos);
    }

    @Override
    public SpookyDoor of(BlockPos pos, BlockState state) {
        return getOrCreate(SpookyDoorUtils.getBasePos(pos, state));
    }

    private ClientSpookyDoor getOrCreate(BlockPos basePos) {
        return doors.computeIfAbsent(basePos.asLong(), it -> new ClientSpookyDoor(level, basePos));
    }

    @Override
    public void remove(BlockPos pos, BlockState state) {
        doors.remove(SpookyDoorUtils.getBasePos(pos, state).asLong());
    }

    @Nullable
    public ClientSpookyDoor get(BlockPos basePos) {
        return doors.get(basePos.asLong());
    }

    public List<BlockPos> doorPositions() {
        return doors.keySet().stream()
                .map(BlockPos::of)
                .toList();
    }

    public void untrackDoorsInChunk(int chunkX, int chunkZ) {
        doors.entrySet().removeIf(entry -> {
            final var pos = BlockPos.of(entry.getKey());
            return pos.getX() >> 4 == chunkX && pos.getZ() >> 4 == chunkZ;
        });
    }

    public void trackDoorsInChunk(LevelChunk chunk) {
        final var level = Objects.requireNonNull(this.level.get());
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
                        if (SpookyDoorUtils.isSupportedDoor(state)
                                && state.hasProperty(DoorBlock.HALF)
                                && state.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER) {
                            final var pos = new BlockPos(minX + localX, minY + localY, minZ + localZ);
                            get(level).of(pos, state);
                        }
                    }
                }
            }
        }
    }
}
