package net.blay09.mods.spookydoors.network;

import net.blay09.mods.spookydoors.SpookyDoors;
import net.blay09.mods.spookydoors.block.entity.SpookyDoorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record ServerboundOpenCloseDoorPacket(BlockPos pos, float openness) implements CustomPacketPayload {

    public static Type<ServerboundOpenCloseDoorPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SpookyDoors.MOD_ID, "open_close_door"));

    public static void encode(FriendlyByteBuf buf, ServerboundOpenCloseDoorPacket message) {
        buf.writeBlockPos(message.pos);
        buf.writeFloat(message.openness);
    }

    public static ServerboundOpenCloseDoorPacket decode(FriendlyByteBuf buf) {
        final var pos = buf.readBlockPos();
        final var openness = buf.readFloat();
        return new ServerboundOpenCloseDoorPacket(pos, openness);
    }

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
