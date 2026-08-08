package net.blay09.mods.spookydoors;

import net.blay09.mods.spookydoors.api.InternalMethods;
import net.blay09.mods.spookydoors.core.SpookyDoorProvider;
import net.blay09.mods.spookydoors.util.SpookyDoorUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class InternalMethodsImpl implements InternalMethods {

    @Override
    public float getPercentOpen(Level level, BlockPos pos) {
        final var state = level.getBlockState(pos);
        if (!SpookyDoorUtils.isSupportedDoor(state)) {
            return SpookyDoorUtils.getDefaultOpenness(state);
        }

        return SpookyDoorProvider.get(level).at(pos).percentOpen();
    }

    @Override
    public void operateDoor(Level level, BlockPos pos, @Nullable Entity entity, float percentOpen) {
        if (SpookyDoorUtils.isSupportedDoor(level.getBlockState(pos))) {
            SpookyDoorProvider.get(level).at(pos).operate(entity, percentOpen);
        }
    }
}
