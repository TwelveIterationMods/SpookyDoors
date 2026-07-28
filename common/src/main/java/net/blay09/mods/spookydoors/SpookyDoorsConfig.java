package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.config.reflection.Config;

@Config(SpookyDoors.MOD_ID)
public class SpookyDoorsConfig {
    public static SpookyDoorsConfig getActive() {
        return Balm.getConfig().getActiveConfig(SpookyDoorsConfig.class);
    }

    public static void initialize() {
        Balm.getConfig().registerConfig(SpookyDoorsConfig.class);
    }

}
