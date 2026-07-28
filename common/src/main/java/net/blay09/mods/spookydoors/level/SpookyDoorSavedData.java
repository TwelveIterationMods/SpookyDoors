package net.blay09.mods.spookydoors.level;

import net.blay09.mods.spookydoors.SpookyDoors;
import net.blay09.mods.spookydoors.core.ServerSpookyDoor;
import net.blay09.mods.spookydoors.core.SpookyDoor;
import net.blay09.mods.spookydoors.core.SpookyDoorProvider;
import net.blay09.mods.spookydoors.util.SpookyDoorUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class SpookyDoorSavedData extends SavedData implements SpookyDoorProvider {

    public static final String ID = SpookyDoors.MOD_ID;

    private final Map<Long, Float> opennessByPos = new HashMap<>();
    private final Map<Long, Long> nextCreakSoundGameTimeByPos = new HashMap<>();
    private ServerLevel level;

    public static SpookyDoorSavedData get(ServerLevel level) {
        final var data = level.getDataStorage().computeIfAbsent(SpookyDoorSavedData::load, SpookyDoorSavedData::new, ID);
        data.level = level;
        return data;
    }

    public static SpookyDoorSavedData load(CompoundTag tag) {
        final var data = new SpookyDoorSavedData();
        final var doors = tag.getList("Doors", Tag.TAG_COMPOUND);
        for (int i = 0; i < doors.size(); i++) {
            final var doorTag = doors.getCompound(i);
            data.opennessByPos.put(doorTag.getLong("Pos"), doorTag.getFloat("Openness"));
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        final var doors = new ListTag();
        for (final var entry : opennessByPos.entrySet()) {
            final var doorTag = new CompoundTag();
            doorTag.putLong("Pos", entry.getKey());
            doorTag.putFloat("Openness", entry.getValue());
            doors.add(doorTag);
        }
        tag.put("Doors", doors);
        return tag;
    }

    @Override
    public SpookyDoor at(BlockPos pos) {
        final var basePos = SpookyDoorUtils.getBasePos(pos, level.getBlockState(pos));
        return new ServerSpookyDoor(level, this, basePos);
    }

    @Override
    public SpookyDoor of(BlockPos pos, BlockState state) {
        return new ServerSpookyDoor(level, this, SpookyDoorUtils.getBasePos(pos, state));
    }

    @Nullable
    public Float getOpenness(BlockPos pos) {
        return opennessByPos.get(pos.asLong());
    }

    public void setOpenness(BlockPos pos, float openness) {
        if (openness == 0f) {
            opennessByPos.remove(pos.asLong());
        } else {
            opennessByPos.put(pos.asLong(), Mth.clamp(openness, 0f, 1f));
        }
        setDirty();
    }

    @Override
    public void remove(BlockPos pos, BlockState state) {
        remove(SpookyDoorUtils.getBasePos(pos, state));
    }

    public boolean remove(BlockPos pos) {
        final var key = pos.asLong();
        final var removedCreakTime = nextCreakSoundGameTimeByPos.remove(key) != null;
        final var removedOpenness = opennessByPos.remove(key) != null;
        if (removedOpenness) {
            setDirty();
        }
        return removedCreakTime || removedOpenness;
    }

    public long getNextCreakSoundGameTime(BlockPos pos) {
        return nextCreakSoundGameTimeByPos.getOrDefault(pos.asLong(), 0L);
    }

    public void setNextCreakSoundGameTime(BlockPos pos, long gameTime) {
        nextCreakSoundGameTimeByPos.put(pos.asLong(), gameTime);
    }
}
