package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.world.item.BalmCreativeModeTabRegistrar;
import net.minecraft.network.chat.Component;

public class ModItems {

    public static void initialize(BalmCreativeModeTabRegistrar creativeModeTabs) {
        creativeModeTabs.register(SpookyDoors.MOD_ID, (id, builder) ->
                builder.title(Component.translatable(id.toLanguageKey("itemGroup")))
                        .icon(() -> ModBlocks.spookyDoors.get(ModBlocks.DoorType.OAK).createStack())
                        .displayItems(((itemDisplayParameters, output) -> {
                            for (final var spookyDoor : ModBlocks.spookyDoors.values()) {
                                output.accept(spookyDoor.asItem());
                            }
                        }))
        );
    }

}
