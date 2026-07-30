package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.platform.config.reflection.Comment;
import net.blay09.mods.balm.platform.config.reflection.Config;
import net.blay09.mods.balm.platform.config.reflection.Synced;
import net.minecraft.util.StringRepresentable;

import java.util.Locale;

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

    public static SpookyDoorsConfig getActive() {
        return Balm.config().getActiveConfig(SpookyDoorsConfig.class);
    }

    public static void initialize() {
        Balm.config().registerConfig(SpookyDoorsConfig.class);
    }

    public enum SpookyDoorActivation implements StringRepresentable {
        OPTIONAL,
        DEFAULT,
        FORCED;

        @Override
        public String getSerializedName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
