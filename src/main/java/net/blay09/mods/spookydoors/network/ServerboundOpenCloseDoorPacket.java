package net.blay09.mods.spookydoors.network;

import io.netty.buffer.ByteBuf;
import net.blay09.mods.spookydoors.SpookyDoors;
import net.blay09.mods.spookydoors.block.entity.SpookyDoorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ServerboundOpenCloseDoorPacket(BlockPos pos, float openness) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ServerboundOpenCloseDoorPacket> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(SpookyDoors.MOD_ID, "open_close_door"));

    public static final StreamCodec<ByteBuf, ServerboundOpenCloseDoorPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            ServerboundOpenCloseDoorPacket::pos,
            ByteBufCodecs.FLOAT,
            ServerboundOpenCloseDoorPacket::openness,
            ServerboundOpenCloseDoorPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        if (context.player() instanceof ServerPlayer player && !player.isSpectator() && player.isAlive()) {
            player.resetLastActionTime();
            final var level = player.level();
            if (level.getBlockEntity(pos) instanceof SpookyDoorBlockEntity doorBlockEntity) {
                doorBlockEntity.setOpennessBy(openness, player);
            }
        }
    }
}
