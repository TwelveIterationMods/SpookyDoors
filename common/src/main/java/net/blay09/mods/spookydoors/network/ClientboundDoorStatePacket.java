package net.blay09.mods.spookydoors.network;

import net.blay09.mods.spookydoors.core.SpookyDoor;
import net.blay09.mods.spookydoors.core.SpookyDoorProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.DoorBlock;

public record ClientboundDoorStatePacket(BlockPos pos, float openness) {

    public static void encode(ClientboundDoorStatePacket message, FriendlyByteBuf buf) {
        buf.writeBlockPos(message.pos);
        buf.writeFloat(message.openness);
    }

    public static ClientboundDoorStatePacket decode(FriendlyByteBuf buf) {
        final var pos = buf.readBlockPos();
        final var openness = buf.readFloat();
        return new ClientboundDoorStatePacket(pos, openness);
    }

    public static void handle(Player player, ClientboundDoorStatePacket message) {
        final var level = player.level();
        final var state = level.getBlockState(message.pos);
        if (state.getBlock() instanceof DoorBlock) {
            final var door = SpookyDoorProvider.get(level).of(message.pos, state);
            door.percentOpen(message.openness);
        }
    }
}
