package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.config.reflection.Comment;
import net.blay09.mods.balm.api.config.reflection.Config;
import net.blay09.mods.balm.api.config.reflection.Synced;

@Config(SpookyDoors.MOD_ID)
public class SpookyDoorsConfig {
    @Synced
    @Comment("Controls which doors Spooky Doors affects. OPTIONAL requires doors to be toggled on with the command, DEFAULT affects doors unless toggled off, and FORCED affects all doors.")
    public SpookyDoorActivation spookyDoorActivation = SpookyDoorActivation.DEFAULT;

    public static SpookyDoorsConfig getActive() {
        return Balm.getConfig().getActiveConfig(SpookyDoorsConfig.class);
    }

    public static void initialize() {
        Balm.getConfig().registerConfig(SpookyDoorsConfig.class);
    }

    public enum SpookyDoorActivation {
        OPTIONAL,
        DEFAULT,
        FORCED
    }
}
