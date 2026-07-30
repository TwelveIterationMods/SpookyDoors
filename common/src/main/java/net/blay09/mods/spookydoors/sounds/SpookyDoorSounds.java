package net.blay09.mods.spookydoors.sounds;

import net.blay09.mods.spookydoors.core.SpookyDoor;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.jetbrains.annotations.Nullable;

public class SpookyDoorSounds {

    private static final int CREAK_SOUND_COOLDOWN_TICKS = 2;

    public static void playTransition(SpookyDoor door, @Nullable Entity entity, float previousPercentOpen, float percentOpen) {
        final var level = door.level();
        final var basePos = door.pos();
        final var baseState = door.state();
        if (previousPercentOpen <= 0 && percentOpen > 0) {
            final var type = baseState.getBlock() instanceof DoorBlock doorBlock ? doorBlock.type() : BlockSetType.OAK;
            level.playSound(entity,
                    basePos,
                    type.doorOpen(),
                    SoundSource.BLOCKS,
                    level.getRandom().nextFloat() * 0.2f + 0.9f,
                    level.getRandom().nextFloat() * 0.2f + 0.9f);
        } else if (previousPercentOpen > 0 && percentOpen <= 0f) {
            final var type = baseState.getBlock() instanceof DoorBlock doorBlock ? doorBlock.type() : BlockSetType.OAK;
            level.playSound(entity,
                    basePos,
                    type.doorClose(),
                    SoundSource.BLOCKS,
                    level.getRandom().nextFloat() * 0.2f + 0.9f,
                    level.getRandom().nextFloat() * 0.2f + 0.9f);
        } else if (level.getGameTime() >= door.nextCreakSoundGameTime()) {
            level.playSound(entity,
                    basePos,
                    ModSounds.doorCreak.value(),
                    SoundSource.BLOCKS,
                    level.getRandom().nextFloat() * 0.2f + 0.5f,
                    level.getRandom().nextFloat() * 0.4f + 0.8f);
            door.nextCreakSoundGameTime(level.getGameTime() + CREAK_SOUND_COOLDOWN_TICKS);
        }
    }
}
