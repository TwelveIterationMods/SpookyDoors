package net.blay09.mods.spookydoors.datagen;

import net.blay09.mods.spookydoors.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class SpookyDoorsLootTableProvider extends FabricBlockLootTableProvider {
    protected SpookyDoorsLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(dataOutput, provider);
    }

    @Override
    public void generate() {
        add(ModBlocks.spookyOakDoor, this::createDoorTable);
        add(ModBlocks.spookySpruceDoor, this::createDoorTable);
        add(ModBlocks.spookyBirchDoor, this::createDoorTable);
        add(ModBlocks.spookyJungleDoor, this::createDoorTable);
        add(ModBlocks.spookyAcaciaDoor, this::createDoorTable);
        add(ModBlocks.spookyCherryDoor, this::createDoorTable);
        add(ModBlocks.spookyDarkOakDoor, this::createDoorTable);
        add(ModBlocks.spookyMangroveDoor, this::createDoorTable);
        add(ModBlocks.spookyBambooDoor, this::createDoorTable);
    }

}
