package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.core.BalmRegistrars;
import net.blay09.mods.balm.platform.event.callback.ServerPlayerCallback;
import net.blay09.mods.spookydoors.command.SpookyDoorsCommand;
import net.blay09.mods.spookydoors.legacy.ModMigrations;
import net.blay09.mods.spookydoors.level.SpookyDoorSavedData;
import net.blay09.mods.spookydoors.network.ModNetworking;
import net.blay09.mods.spookydoors.sounds.ModSounds;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
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
        ServerPlayerCallback.ChunkTracking.START.register(SpookyDoors::onStartTrackingChunk);
    }

    private static void onStartTrackingChunk(ServerLevel level, ServerPlayer player, ChunkPos chunkPos) {
        SpookyDoorSavedData.get(level).syncDoorsInChunk(player, chunkPos);
    }

    public static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }

}
