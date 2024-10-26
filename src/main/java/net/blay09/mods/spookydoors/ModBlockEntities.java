package net.blay09.mods.spookydoors;

import net.blay09.mods.spookydoors.block.entity.SpookyDoorBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, SpookyDoors.MOD_ID);

    public static final Supplier<BlockEntityType<SpookyDoorBlockEntity>> SPOOKY_DOOR = BLOCK_ENTITIES.register("spooky_door",
            () -> BlockEntityType.Builder.of(SpookyDoorBlockEntity::new, ModBlocks.SPOOKY_OAK_DOOR.get(),
                    ModBlocks.SPOOKY_SPRUCE_DOOR.get(),
                    ModBlocks.SPOOKY_BIRCH_DOOR.get(),
                    ModBlocks.SPOOKY_JUNGLE_DOOR.get(),
                    ModBlocks.SPOOKY_ACACIA_DOOR.get(),
                    ModBlocks.SPOOKY_CHERRY_DOOR.get(),
                    ModBlocks.SPOOKY_DARK_OAK_DOOR.get(),
                    ModBlocks.SPOOKY_MANGROVE_DOOR.get(),
                    ModBlocks.SPOOKY_BAMBOO_DOOR.get()).build(null));

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITIES.register(modEventBus);
    }
}
