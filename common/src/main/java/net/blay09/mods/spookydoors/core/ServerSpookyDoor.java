package net.blay09.mods.spookydoors.core;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.spookydoors.level.SpookyDoorSavedData;
import net.blay09.mods.spookydoors.network.ClientboundDoorStatePacket;
import net.blay09.mods.spookydoors.util.SpookyDoorUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.DoorBlock;
import org.jetbrains.annotations.Nullable;

public record ServerSpookyDoor(ServerLevel level, SpookyDoorSavedData savedData, BlockPos pos) implements SpookyDoor {

    @Override
    public float percentOpen() {
        final var percentOpen = savedData.getOpenness(pos);
        return percentOpen != null ? percentOpen : SpookyDoorUtils.getDefaultOpenness(state());
    }

    @Override
    public void percentOpen(float percentOpen) {
        savedData.setOpenness(pos, percentOpen);
    }

    @Override
    public long nextCreakSoundGameTime() {
        return savedData.getNextCreakSoundGameTime(pos);
    }

    @Override
    public void nextCreakSoundGameTime(long gameTime) {
        savedData.setNextCreakSoundGameTime(pos, gameTime);
    }

    public void syncToClients() {
        Balm.getNetworking().sendToTracking(level, pos, new ClientboundDoorStatePacket(pos, percentOpen()));
    }

    @Override
    public void operate(@Nullable Entity entity, float previousPercentOpen, float percentOpen) {
        SpookyDoor.super.operate(entity, previousPercentOpen, percentOpen);

        updateBlockState(percentOpen > 0.5f);
        syncToClients();
    }

    private void updateBlockState(boolean open) {
        final var state = state();
        if (state.getBlock() instanceof DoorBlock && state.getValue(DoorBlock.OPEN) != open) {
            level.setBlock(pos, state.setValue(DoorBlock.OPEN, open), 10);
        }
    }
}
