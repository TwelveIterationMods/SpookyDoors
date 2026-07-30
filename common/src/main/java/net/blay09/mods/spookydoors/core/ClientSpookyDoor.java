package net.blay09.mods.spookydoors.core;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.spookydoors.SpookyDoorsConfig;
import net.blay09.mods.spookydoors.network.ServerboundOperateDoorPacket;
import net.blay09.mods.spookydoors.util.SpookyDoorUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;

public class ClientSpookyDoor implements SpookyDoor {

    private final WeakReference<Level> level;
    private final BlockPos basePos;
    private float percentOpen;
    @Nullable
    private Boolean spooky;
    private boolean locallyControlled;
    private long nextCreakSoundGameTime;

    public ClientSpookyDoor(WeakReference<Level> level, BlockPos basePos) {
        this.level = level;
        this.basePos = basePos;
        percentOpen = SpookyDoorUtils.getDefaultOpenness(state());
    }

    @Override
    public Level level() {
        final var level = this.level.get();
        if (level == null) {
            throw new IllegalStateException("Client level has been garbage collected");
        }

        return level;
    }

    @Override
    public BlockPos pos() {
        return basePos;
    }

    @Override
    public float percentOpen() {
        return percentOpen;
    }

    @Override
    public void percentOpen(float percentOpen) {
        this.percentOpen = Mth.clamp(percentOpen, 0f, 1f);
    }

    public boolean locallyControlled() {
        return locallyControlled;
    }

    public void locallyControlled(boolean locallyControlled) {
        this.locallyControlled = locallyControlled;
    }

    @Override
    public boolean spooky() {
        if (SpookyDoorsConfig.getActive().spookyDoorActivation == SpookyDoorsConfig.SpookyDoorActivation.FORCED) {
            return true;
        }

        if (spooky != null) {
            return spooky;
        }

        return SpookyDoorsConfig.getActive().spookyDoorActivation == SpookyDoorsConfig.SpookyDoorActivation.DEFAULT;
    }

    @Override
    public void spooky(boolean spooky) {
        this.spooky = spooky;
    }

    @Override
    public long nextCreakSoundGameTime() {
        return nextCreakSoundGameTime;
    }

    @Override
    public void nextCreakSoundGameTime(long gameTime) {
        nextCreakSoundGameTime = gameTime;
    }

    public void syncToServer() {
        Balm.networking().sendToServer(new ServerboundOperateDoorPacket(pos(), percentOpen()));
    }
}
