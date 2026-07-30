package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.ChunkTrackingEvent;
import net.blay09.mods.spookydoors.command.SpookyDoorsCommand;
import net.blay09.mods.spookydoors.legacy.ModMigrations;
import net.blay09.mods.spookydoors.level.SpookyDoorSavedData;
import net.blay09.mods.spookydoors.network.ModNetworking;
import net.blay09.mods.spookydoors.sounds.ModSounds;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpookyDoors {

    public static final Logger logger = LoggerFactory.getLogger(SpookyDoors.class);

    public static final String MOD_ID = "spookydoors";

    public static void initialize() {
        SpookyDoorsConfig.initialize();
        Balm.getCommands().register(SpookyDoorsCommand::register);
        ModMigrations.initialize();
        ModNetworking.initialize(Balm.getNetworking());
        ModSounds.initialize(Balm.getSounds());
        Balm.getEvents().onEvent(ChunkTrackingEvent.Start.class, SpookyDoors::onStartTrackingChunk);
    }

    public static ResourceLocation id(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    private static void onStartTrackingChunk(ChunkTrackingEvent.Start event) {
        SpookyDoorSavedData.get(event.getLevel()).syncDoorsInChunk(event.getPlayer(), event.getChunkPos());
    }
}
