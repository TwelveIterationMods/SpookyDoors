package net.blay09.mods.spookydoors.datagen;

import net.blay09.mods.spookydoors.SpookyDoors;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = SpookyDoors.MOD_ID)
public class SpookyDoorsDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        final var generator = event.getGenerator();
        final var output = generator.getPackOutput();
        final var existingFileHelper = event.getExistingFileHelper();
        final var lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new SpookyDoorsBlockStateProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new SpookyDoorsItemModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeServer(), new LootTableProvider(output,
                Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(SpookyDoorsLootTableProvider::new, LootContextParamSets.BLOCK)),
                lookupProvider));
        generator.addProvider(event.includeServer(), new SpookyDoorsBlockTagsProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new SpookyDoorsItemTagsProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new SpookyDoorsRecipeProvider(output, lookupProvider));
    }

}
