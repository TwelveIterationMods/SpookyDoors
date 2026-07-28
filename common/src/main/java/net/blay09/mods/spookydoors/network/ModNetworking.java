package net.blay09.mods.spookydoors.network;

import net.blay09.mods.balm.api.network.BalmNetworking;
import net.blay09.mods.spookydoors.SpookyDoors;
import net.minecraft.resources.ResourceLocation;

public class ModNetworking {
    public static void initialize(BalmNetworking networking) {
        networking.registerServerboundPacket(id("operate_door"), ServerboundOperateDoorPacket.class, ServerboundOperateDoorPacket::encode, ServerboundOperateDoorPacket::decode, ServerboundOperateDoorPacket::handle);
        networking.registerClientboundPacket(id("door_state"), ClientboundDoorStatePacket.class, ClientboundDoorStatePacket::encode, ClientboundDoorStatePacket::decode, ClientboundDoorStatePacket::handle);
    }

    private static ResourceLocation id(String name) {
        return new ResourceLocation(SpookyDoors.MOD_ID, name);
    }
}
