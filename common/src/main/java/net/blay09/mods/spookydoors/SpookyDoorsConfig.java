package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.api.Balm;

public class SpookyDoorsConfig {
    public static SpookyDoorsConfigData getActive() {
        return Balm.getConfig().getActive(SpookyDoorsConfigData.class);
    }

    public static void initialize() {
        Balm.getConfig().registerConfig(SpookyDoorsConfigData.class, null);
    }

}
