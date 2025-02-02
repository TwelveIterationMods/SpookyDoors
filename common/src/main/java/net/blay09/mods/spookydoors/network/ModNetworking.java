package net.blay09.mods.spookydoors.network;

import net.blay09.mods.balm.api.network.BalmNetworking;
import net.blay09.mods.spookydoors.SpookyDoors;
import net.minecraft.resources.ResourceLocation;

public class ModNetworking {
    public static void initialize(BalmNetworking networking) {
        networking.registerServerboundPacket(id("open_close_door"), ServerboundOpenCloseDoorPacket.class, ServerboundOpenCloseDoorPacket::encode, ServerboundOpenCloseDoorPacket::decode, ServerboundOpenCloseDoorPacket::handle);
    }

    private static ResourceLocation id(String name) {
        return new ResourceLocation(SpookyDoors.MOD_ID, name);
    }
}
