package net.blay09.mods.spookydoors.network;

import net.blay09.mods.spookydoors.SpookyDoors;
import net.blay09.mods.spookydoors.core.SpookyDoorProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.DoorBlock;

public record ClientboundDoorStatePacket(BlockPos pos, float openness, boolean spooky) implements CustomPacketPayload {

    public static final Type<ClientboundDoorStatePacket> TYPE = new Type<>(SpookyDoors.id("door_state"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundDoorStatePacket> STREAM_CODEC = StreamCodec.of(ClientboundDoorStatePacket::encode, ClientboundDoorStatePacket::decode);

    public static void encode(RegistryFriendlyByteBuf buf, ClientboundDoorStatePacket message) {
        buf.writeBlockPos(message.pos);
        buf.writeBoolean(message.spooky);
        buf.writeFloat(message.openness);
    }

    public static ClientboundDoorStatePacket decode(RegistryFriendlyByteBuf buf) {
        final var pos = buf.readBlockPos();
        final var spooky = buf.readBoolean();
        final var openness = buf.readFloat();
        return new ClientboundDoorStatePacket(pos, openness, spooky);
    }

    public static void handle(Player player, ClientboundDoorStatePacket message) {
        final var level = player.level();
        final var state = level.getBlockState(message.pos);
        if (state.getBlock() instanceof DoorBlock) {
            final var door = SpookyDoorProvider.get(level).of(message.pos, state);
            door.spooky(message.spooky);
            door.percentOpen(message.openness);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
