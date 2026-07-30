package net.blay09.mods.spookydoors.core;

import net.blay09.mods.spookydoors.client.SpookyDoorClientTracking;
import net.blay09.mods.spookydoors.level.SpookyDoorSavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface SpookyDoorProvider {
    static SpookyDoorProvider get(Level level) {
        if (level instanceof ServerLevel serverLevel) {
            return SpookyDoorSavedData.get(serverLevel);
        }

        return SpookyDoorClientTracking.get(level);
    }

    SpookyDoor at(BlockPos pos);

    SpookyDoor of(BlockPos pos, BlockState state);

    void remove(BlockPos pos, BlockState state);
}
