package net.blay09.mods.spookydoors.network;

import net.blay09.mods.balm.network.BalmNetworking;

public class ModNetworking {
    public static void initialize(BalmNetworking networking) {
        networking.registerServerboundPacket(ServerboundOpenCloseDoorPacket.TYPE, ServerboundOpenCloseDoorPacket.class, ServerboundOpenCloseDoorPacket.STREAM_CODEC, ServerboundOpenCloseDoorPacket::handle);
    }
}
