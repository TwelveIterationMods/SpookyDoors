package net.blay09.mods.spookydoors.network;

import net.blay09.mods.spookydoors.SpookyDoors;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;

@EventBusSubscriber(modid = SpookyDoors.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModNetworking {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final var registrar = event.registrar("1").executesOn(HandlerThread.MAIN);
        registrar.playToServer(ServerboundOpenCloseDoorPacket.TYPE, ServerboundOpenCloseDoorPacket.STREAM_CODEC, ServerboundOpenCloseDoorPacket::handle);
    }
}
