package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.api.config.BalmConfigData;
import net.blay09.mods.balm.api.config.Comment;
import net.blay09.mods.balm.api.config.Config;
import net.blay09.mods.balm.api.config.ExpectedType;

@Config(SpookyDoors.MOD_ID)
public class SpookyDoorsConfigData implements BalmConfigData {
    @Comment("Make the doors randomly open slightly. Doesn't affect gameplay.")
    @ExpectedType(Boolean.class)
    public Boolean random_spook = Boolean.FALSE;
}
