package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.api.DeferredObject;
import net.blay09.mods.balm.api.item.BalmItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

public class ModItems {

    public static DeferredObject<CreativeModeTab> creativeModeTab;

    public static void initialize(BalmItems items) {
        creativeModeTab = items.registerCreativeModeTab(() -> new ItemStack(ModBlocks.spookyOakDoor), id("spookydoors"));
    }

    private static ResourceLocation id(String name) {
        return new ResourceLocation(SpookyDoors.MOD_ID, name);
    }
}
