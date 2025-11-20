package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.world.level.block.entity.BalmBlockEntityTypeRegistrar;
import net.blay09.mods.spookydoors.block.entity.SpookyDoorBlockEntity;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {

    public static Holder<BlockEntityType<SpookyDoorBlockEntity>> spookyDoor;

    public static void initialize(BalmBlockEntityTypeRegistrar blockEntities) {
        spookyDoor = blockEntities.register("spooky_door", SpookyDoorBlockEntity::new, ModBlocks.spookyDoors.values()).asHolder();
    }
}
