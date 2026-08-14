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

    @Synced
    @Comment("Allows players to use items in the spookydoors:exorcises_doors tag on spooky doors to make them non-spooky. By default, this is honeycomb. This has no effect when Spooky Door Activation is FORCED.")
    public boolean allowItemToExorciseDoors = true;

    @Synced
    @Comment("Allows players to use items in the spookydoors:haunts_doors tag on non-spooky doors to make them spooky. By default, this is a ghast tear. This has no effect when Spooky Door Activation is FORCED.")
    public boolean allowItemToHauntDoors = true;

    @Synced
    @Comment("Turns the player's camera while dragging a spooky door open or closed, to avoid immersion-breaking hard stops.")
    public boolean moveCameraWithDoor = true;

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
