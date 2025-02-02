package net.blay09.mods.spookydoors.network;

import net.blay09.mods.spookydoors.block.entity.SpookyDoorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record ServerboundOpenCloseDoorPacket(BlockPos pos, float openness) {

    public static void encode(ServerboundOpenCloseDoorPacket message, FriendlyByteBuf buf) {
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
}
