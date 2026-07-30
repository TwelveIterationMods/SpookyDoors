package net.blay09.mods.spookydoors.network;

import net.blay09.mods.spookydoors.core.SpookyDoorProvider;
import net.blay09.mods.spookydoors.util.SpookyDoorUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;

public record ServerboundOperateDoorPacket(BlockPos pos, float openness) {

    private static final double MAX_INTERACTION_DISTANCE_SQ = 64;

    public ServerboundOperateDoorPacket {
        openness = Float.isFinite(openness) ? Mth.clamp(openness, 0f, 1f) : 0f;
    }

    public static void encode(ServerboundOperateDoorPacket message, FriendlyByteBuf buf) {
        buf.writeBlockPos(message.pos);
        buf.writeFloat(message.openness);
    }

    public static ServerboundOperateDoorPacket decode(FriendlyByteBuf buf) {
        final var pos = buf.readBlockPos();
        final var openness = buf.readFloat();
        return new ServerboundOperateDoorPacket(pos, openness);
    }

    public static void handle(ServerPlayer player, ServerboundOperateDoorPacket message) {
        if (player.isSpectator() || !player.isAlive()) {
            return;
        }

        final var level = player.level();
        if (!level.isLoaded(message.pos)
                || !level.getWorldBorder().isWithinBounds(message.pos)
                || !(player.distanceToSqr(message.pos.getX() + 0.5, message.pos.getY() + 0.5, message.pos.getZ() + 0.5) <= MAX_INTERACTION_DISTANCE_SQ)) {
            return;
        }

        final var state = level.getBlockState(message.pos);
        if (SpookyDoorUtils.canOperate(state)) {
            player.resetLastActionTime();
            final var door = SpookyDoorProvider.get(level).of(message.pos, state);
            if (door.spooky()) {
                door.operate(player, message.openness);
            }
        }
    }

}
