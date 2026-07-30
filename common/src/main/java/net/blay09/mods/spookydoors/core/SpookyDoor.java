package net.blay09.mods.spookydoors.core;

import net.blay09.mods.spookydoors.sounds.SpookyDoorSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public interface SpookyDoor {

    Level level();

    BlockPos pos();

    default BlockState state() {
        return level().getBlockState(pos());
    }

    float percentOpen();

    void percentOpen(float percentOpen);

    boolean spooky();

    void spooky(boolean spooky);

    long nextCreakSoundGameTime();

    void nextCreakSoundGameTime(long gameTime);

    default void operate(float percentOpen) {
        operate(null, percentOpen);
    }

    default void operate(@Nullable Entity entity, float percentOpen) {
        operate(entity, percentOpen(), percentOpen);
    }

    default void operate(@Nullable Entity entity, float previousPercentOpen, float percentOpen) {
        if (previousPercentOpen != percentOpen) {
            SpookyDoorSounds.playTransition(this, entity, previousPercentOpen, percentOpen);
        }
        percentOpen(percentOpen);
    }
}
