package net.blay09.mods.spookydoors.network;

import net.blay09.mods.spookydoors.core.ClientSpookyDoor;
import net.blay09.mods.spookydoors.core.SpookyDoorProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public record ClientboundDoorStatePacket(BlockPos pos, float openness, boolean spooky) {

    public static void encode(ClientboundDoorStatePacket message, FriendlyByteBuf buf) {
        buf.writeBlockPos(message.pos);
        buf.writeBoolean(message.spooky);
        buf.writeFloat(message.openness);
    }

    public static ClientboundDoorStatePacket decode(FriendlyByteBuf buf) {
        final var pos = buf.readBlockPos();
        final var spooky = buf.readBoolean();
        final var openness = buf.readFloat();
        return new ClientboundDoorStatePacket(pos, openness, spooky);
    }

    public static void handle(Player player, ClientboundDoorStatePacket message) {
        final var level = player.level();
        final var door = SpookyDoorProvider.get(level).at(message.pos);
        door.spooky(message.spooky);
        final var isLocallyControlled = door instanceof ClientSpookyDoor clientSpookyDoor && clientSpookyDoor.locallyControlled();
        if (!isLocallyControlled) {
            door.percentOpen(message.openness);
        }
    }
}
