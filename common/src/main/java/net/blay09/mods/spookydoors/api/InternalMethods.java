package net.blay09.mods.spookydoors.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public interface InternalMethods {
    float getPercentOpen(Level level, BlockPos pos);

    void operateDoor(Level level, BlockPos pos, @Nullable Entity entity, float percentOpen);
}
