package net.blay09.mods.spookydoors;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SpookyDoors.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SpookyDoors.MOD_ID);

    public static final DeferredItem<BlockItem> SPOOKY_OAK_DOOR_ITEM = ITEMS.registerSimpleBlockItem("spooky_oak_door", ModBlocks.SPOOKY_OAK_DOOR);
    public static final DeferredItem<BlockItem> SPOOKY_SPRUCE_DOOR_ITEM = ITEMS.registerSimpleBlockItem("spooky_spruce_door", ModBlocks.SPOOKY_SPRUCE_DOOR);
    public static final DeferredItem<BlockItem> SPOOKY_BIRCH_DOOR_ITEM = ITEMS.registerSimpleBlockItem("spooky_birch_door", ModBlocks.SPOOKY_BIRCH_DOOR);
    public static final DeferredItem<BlockItem> SPOOKY_JUNGLE_DOOR_ITEM = ITEMS.registerSimpleBlockItem("spooky_jungle_door", ModBlocks.SPOOKY_JUNGLE_DOOR);
    public static final DeferredItem<BlockItem> SPOOKY_ACACIA_DOOR_ITEM = ITEMS.registerSimpleBlockItem("spooky_acacia_door", ModBlocks.SPOOKY_ACACIA_DOOR);
    public static final DeferredItem<BlockItem> SPOOKY_CHERRY_DOOR_ITEM = ITEMS.registerSimpleBlockItem("spooky_cherry_door", ModBlocks.SPOOKY_CHERRY_DOOR);
    public static final DeferredItem<BlockItem> SPOOKY_DARK_OAK_DOOR_ITEM = ITEMS.registerSimpleBlockItem("spooky_dark_oak_door",
            ModBlocks.SPOOKY_DARK_OAK_DOOR);
    public static final DeferredItem<BlockItem> SPOOKY_MANGROVE_DOOR_ITEM = ITEMS.registerSimpleBlockItem("spooky_mangrove_door",
            ModBlocks.SPOOKY_MANGROVE_DOOR);
    public static final DeferredItem<BlockItem> SPOOKY_BAMBOO_DOOR_ITEM = ITEMS.registerSimpleBlockItem("spooky_bamboo_door", ModBlocks.SPOOKY_BAMBOO_DOOR);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_MODE_TAB = CREATIVE_MODE_TABS.register("spookydoors",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.spookydoors"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> SPOOKY_OAK_DOOR_ITEM.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(SPOOKY_OAK_DOOR_ITEM.get());
                        output.accept(SPOOKY_SPRUCE_DOOR_ITEM.get());
                        output.accept(SPOOKY_BIRCH_DOOR_ITEM.get());
                        output.accept(SPOOKY_JUNGLE_DOOR_ITEM.get());
                        output.accept(SPOOKY_ACACIA_DOOR_ITEM.get());
                        output.accept(SPOOKY_CHERRY_DOOR_ITEM.get());
                        output.accept(SPOOKY_DARK_OAK_DOOR_ITEM.get());
                        output.accept(SPOOKY_MANGROVE_DOOR_ITEM.get());
                        output.accept(SPOOKY_BAMBOO_DOOR_ITEM.get());
                    }).build());

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
