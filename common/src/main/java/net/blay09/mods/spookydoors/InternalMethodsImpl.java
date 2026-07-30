package net.blay09.mods.spookydoors;

import net.blay09.mods.spookydoors.api.InternalMethods;
import net.blay09.mods.spookydoors.core.SpookyDoorProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class InternalMethodsImpl implements InternalMethods {

    @Override
    public float getPercentOpen(Level level, BlockPos pos) {
        return SpookyDoorProvider.get(level).at(pos).percentOpen();
    }

    @Override
    public void operateDoor(Level level, BlockPos pos, @Nullable Entity entity, float percentOpen) {
        SpookyDoorProvider.get(level).at(pos).operate(entity, percentOpen);
    }
}
