package net.blay09.mods.spookydoors.block.entity;

import net.blay09.mods.spookydoors.ModBlockEntities;
import net.blay09.mods.spookydoors.ModSounds;
import net.blay09.mods.spookydoors.block.SpookyDoorBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;

public class SpookyDoorBlockEntity extends BlockEntity {

    private static final int SYNC_INTERVAL = 10;
    private int ticksSinceLastSync = 0;
    private int soundCooldownTicks = 0;
    private boolean isDirty;

    private float openness;

    private boolean clientControl;

    public SpookyDoorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SPOOKY_DOOR.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putFloat("Openness", openness);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (!clientControl) {
            setOpennessBy(tag.getFloat("Openness"), null);
        }
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        final var tag = new CompoundTag();
        tag.putFloat("Openness", openness);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.handleUpdateTag(tag, lookupProvider);
        if (!clientControl) {
            setOpennessBy(openness, null);
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();
        isDirty = true;
    }

    public float getOpenness() {
        return openness;
    }

    public void setOpennessBy(float openness, @Nullable Entity entity) {
        final var prevOpenness = this.openness;
        this.openness = Math.clamp(0f, 1f, openness);
        setChanged();
        updateBlockState();
        playSounds(entity, prevOpenness, this.openness);
    }

    public void playSounds(Entity entity, float prevOpenness, float openness) {
        if (level != null) {
            final var speed = Math.abs(openness - prevOpenness);
            if (prevOpenness <= 0 && openness > 0) {
                final var type = getBlockState().getBlock() instanceof DoorBlock doorBlock ? doorBlock.type() : BlockSetType.OAK;
                level.playSound(entity,
                        worldPosition,
                        type.doorOpen(),
                        SoundSource.BLOCKS,
                        level.getRandom().nextFloat() * 0.2f + 0.9f,
                        level.getRandom().nextFloat() * 0.2f + 0.9f);
            } else if (prevOpenness > 0 && openness == 0f) {
                final var type = getBlockState().getBlock() instanceof DoorBlock doorBlock ? doorBlock.type() : BlockSetType.OAK;
                level.playSound(entity,
                        worldPosition,
                        type.doorClose(),
                        SoundSource.BLOCKS,
                        level.getRandom().nextFloat() * 0.2f + 0.9f,
                        level.getRandom().nextFloat() * 0.2f + 0.9f);
            } else if (soundCooldownTicks <= 0) {
                level.playSound(entity,
                        worldPosition,
                        ModSounds.DOOR_CREAK.get(),
                        SoundSource.BLOCKS,
                        level.getRandom().nextFloat() * 0.2f + 0.5f,
                        level.getRandom().nextFloat() * 0.4f + 0.8f);
                soundCooldownTicks = 2;
            }
        }
    }

    public void updateBlockState() {
        if (level != null) {
            final var state = getBlockState();
            final var newState = state.setValue(SpookyDoorBlock.OPEN, openness > 0.5f);
            if (state.getValue(SpookyDoorBlock.OPEN) != newState.getValue(SpookyDoorBlock.OPEN)) {
                level.setBlock(worldPosition, newState, 10);
            }
        }
    }

    public void sync() {
        if (level != null && !level.isClientSide) {
            ((ServerLevel) level).getChunkSource().blockChanged(worldPosition);
        }
    }

    public void serverTick() {
        ticksSinceLastSync++;
        if (ticksSinceLastSync >= SYNC_INTERVAL) {
            if (isDirty) {
                sync();
            }
            ticksSinceLastSync = 0;
            isDirty = false;
        }

        if (soundCooldownTicks > 0) {
            soundCooldownTicks--;
        }
    }

    public void clientTick() {
        if (soundCooldownTicks > 0) {
            soundCooldownTicks--;
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (blockEntity instanceof SpookyDoorBlockEntity spookyDoor) {
            spookyDoor.serverTick();
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (blockEntity instanceof SpookyDoorBlockEntity spookyDoor) {
            spookyDoor.clientTick();
        }
    }

    public SpookyDoorBlockEntity getBaseDoor() {
        if (level != null && getBlockState().getValue(SpookyDoorBlock.HALF) == DoubleBlockHalf.UPPER) {
            final var lowerBlockEntity = level.getBlockEntity(worldPosition.below());
            if (lowerBlockEntity instanceof SpookyDoorBlockEntity lowerSpookyDoor) {
                return lowerSpookyDoor;
            }
        }
        return this;
    }

    public void setClientControl(boolean clientControl) {
        this.clientControl = clientControl;
    }
}
