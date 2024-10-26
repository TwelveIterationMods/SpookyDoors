package net.blay09.mods.spookydoors.datagen;

import net.blay09.mods.spookydoors.ModItems;
import net.blay09.mods.spookydoors.SpookyDoors;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class SpookyDoorsItemModelProvider extends ItemModelProvider {
    public SpookyDoorsItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SpookyDoors.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(ModItems.SPOOKY_OAK_DOOR_ITEM.getRegisteredName(), mcLoc("item/oak_door"));
        withExistingParent(ModItems.SPOOKY_SPRUCE_DOOR_ITEM.getRegisteredName(), mcLoc("item/spruce_door"));
        withExistingParent(ModItems.SPOOKY_BIRCH_DOOR_ITEM.getRegisteredName(), mcLoc("item/birch_door"));
        withExistingParent(ModItems.SPOOKY_JUNGLE_DOOR_ITEM.getRegisteredName(), mcLoc("item/jungle_door"));
        withExistingParent(ModItems.SPOOKY_ACACIA_DOOR_ITEM.getRegisteredName(), mcLoc("item/acacia_door"));
        withExistingParent(ModItems.SPOOKY_CHERRY_DOOR_ITEM.getRegisteredName(), mcLoc("item/cherry_door"));
        withExistingParent(ModItems.SPOOKY_DARK_OAK_DOOR_ITEM.getRegisteredName(), mcLoc("item/dark_oak_door"));
        withExistingParent(ModItems.SPOOKY_MANGROVE_DOOR_ITEM.getRegisteredName(), mcLoc("item/mangrove_door"));
        withExistingParent(ModItems.SPOOKY_BAMBOO_DOOR_ITEM.getRegisteredName(), mcLoc("item/bamboo_door"));
    }
}
