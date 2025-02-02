package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.api.config.BalmConfigData;
import net.blay09.mods.balm.api.config.Comment;
import net.blay09.mods.balm.api.config.Config;
import net.blay09.mods.balm.api.config.ExpectedType;

import java.util.ArrayList;
import java.util.List;

@Config(SpookyDoors.MOD_ID)
public class SpookyDoorsConfigData implements BalmConfigData {
    @Comment("IDs of inbuilt rulesets to enable. For example, \"waystones:generated_waystones\" makes all generated waystones unbreakable.")
    @ExpectedType(String.class)
    public List<String> rulesets = new ArrayList<>();

    @Comment("List of custom rules with comma-separated parameters in parentheses. Conditions can be defined as comma-separated list in square brackets. Will be applied in order.")
    @ExpectedType(String.class)
    public List<String> rules = new ArrayList<>();
}
