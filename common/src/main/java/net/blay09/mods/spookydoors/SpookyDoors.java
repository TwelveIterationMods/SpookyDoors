package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.ChunkTrackingEvent;
import net.blay09.mods.balm.core.BalmRegistrars;
import net.blay09.mods.spookydoors.command.SpookyDoorsCommand;
import net.blay09.mods.spookydoors.legacy.ModMigrations;
import net.blay09.mods.spookydoors.level.SpookyDoorSavedData;
import net.blay09.mods.spookydoors.network.ModNetworking;
import net.blay09.mods.spookydoors.sounds.ModSounds;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpookyDoors {

    public static final Logger logger = LoggerFactory.getLogger(SpookyDoors.class);

    public static final String MOD_ID = "spookydoors";

    public static void initialize(BalmRegistrars registrars) {
        SpookyDoorsConfig.initialize();
        Balm.commands().register(SpookyDoorsCommand::register);
        ModMigrations.initialize(registrars.registrar());
        ModNetworking.initialize(Balm.networking());
        registrars.registrar(Registries.SOUND_EVENT, ModSounds::initialize);
        Balm.getEvents().onEvent(ChunkTrackingEvent.Start.class, SpookyDoors::onStartTrackingChunk);
    }

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    private static void onStartTrackingChunk(ChunkTrackingEvent.Start event) {
        SpookyDoorSavedData.get(event.getLevel()).syncDoorsInChunk(event.getPlayer(), event.getChunkPos());
    }
}
