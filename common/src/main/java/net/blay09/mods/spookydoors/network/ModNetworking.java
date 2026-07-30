package net.blay09.mods.spookydoors.network;

import net.blay09.mods.balm.network.BalmNetworking;

public class ModNetworking {
    public static void initialize(BalmNetworking networking) {
        networking.registerServerboundPacket(ServerboundOperateDoorPacket.TYPE, ServerboundOperateDoorPacket.class, ServerboundOperateDoorPacket.STREAM_CODEC, ServerboundOperateDoorPacket::handle);
        networking.registerClientboundPacket(ClientboundDoorStatePacket.TYPE, ClientboundDoorStatePacket.class, ClientboundDoorStatePacket.STREAM_CODEC, ClientboundDoorStatePacket::handle);
    }
}
