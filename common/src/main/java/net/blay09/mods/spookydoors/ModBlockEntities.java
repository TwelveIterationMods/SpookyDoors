package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.api.DeferredObject;
import net.blay09.mods.balm.api.block.BalmBlockEntities;
import net.blay09.mods.spookydoors.block.entity.SpookyDoorBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {

    public static DeferredObject<BlockEntityType<SpookyDoorBlockEntity>> spookyDoor;

    public static void initialize(BalmBlockEntities blockEntities) {
        spookyDoor = blockEntities.registerBlockEntity(new ResourceLocation(SpookyDoors.MOD_ID, "spooky_door"), SpookyDoorBlockEntity::new, () -> new Block[]{
                ModBlocks.spookyOakDoor,
                ModBlocks.spookySpruceDoor,
                ModBlocks.spookyBirchDoor,
                ModBlocks.spookyJungleDoor,
                ModBlocks.spookyAcaciaDoor,
                ModBlocks.spookyCherryDoor,
                ModBlocks.spookyDarkOakDoor,
                ModBlocks.spookyMangroveDoor,
                ModBlocks.spookyBambooDoor
        });
    }
}
