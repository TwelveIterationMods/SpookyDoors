package net.blay09.mods.spookydoors.network;

import net.blay09.mods.spookydoors.block.entity.SpookyDoorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

import static net.blay09.mods.spookydoors.SpookyDoors.id;

public record ServerboundOpenCloseDoorPacket(BlockPos pos, float openness) implements CustomPacketPayload {

    public static final Type<ServerboundOpenCloseDoorPacket> TYPE = new Type<>(id("open_close_door"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ServerboundOpenCloseDoorPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            ServerboundOpenCloseDoorPacket::pos,
            ByteBufCodecs.FLOAT,
            ServerboundOpenCloseDoorPacket::openness,
            ServerboundOpenCloseDoorPacket::new
    );

    public static void handle(ServerPlayer player, ServerboundOpenCloseDoorPacket message) {
        if (!player.isSpectator() && player.isAlive()) {
            player.resetLastActionTime();
            final var level = player.level();
            if (level.getBlockEntity(message.pos) instanceof SpookyDoorBlockEntity doorBlockEntity) {
                doorBlockEntity.setOpennessBy(message.openness, player);
            }
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
