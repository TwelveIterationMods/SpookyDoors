package net.blay09.mods.spookydoors.datagen;

import net.blay09.mods.spookydoors.ModBlocks;
import net.blay09.mods.spookydoors.SpookyDoors;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class SpookyDoorsModelProvider extends FabricModelProvider {
    public SpookyDoorsModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        spookyDoor(blockStateModelGenerator, ModBlocks.spookyOakDoor);
        spookyDoor(blockStateModelGenerator, ModBlocks.spookySpruceDoor);
        spookyDoor(blockStateModelGenerator, ModBlocks.spookyBirchDoor);
        spookyDoor(blockStateModelGenerator, ModBlocks.spookyJungleDoor);
        spookyDoor(blockStateModelGenerator, ModBlocks.spookyAcaciaDoor);
        spookyDoor(blockStateModelGenerator, ModBlocks.spookyCherryDoor);
        spookyDoor(blockStateModelGenerator, ModBlocks.spookyDarkOakDoor);
        spookyDoor(blockStateModelGenerator, ModBlocks.spookyMangroveDoor);
        spookyDoor(blockStateModelGenerator, ModBlocks.spookyBambooDoor);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
    }

    public void spookyDoor(BlockModelGenerators blockStateModelGenerator, Block block) {
        final var id = BuiltInRegistries.BLOCK.getKey(block);
        final var textureMapping = TextureMapping.door(block);
        textureMapping.put(TextureSlot.TOP, modLoc("block/" + id.getPath() + "_top"));
        textureMapping.put(TextureSlot.BOTTOM, modLoc("block/" + id.getPath() + "_bottom"));
        final var resourceLocation = ModelTemplates.DOOR_BOTTOM_LEFT.create(block, textureMapping, blockStateModelGenerator.modelOutput);
        final var resourceLocation2 = ModelTemplates.DOOR_BOTTOM_LEFT_OPEN.create(block, textureMapping, blockStateModelGenerator.modelOutput);
        final var resourceLocation3 = ModelTemplates.DOOR_BOTTOM_RIGHT.create(block, textureMapping, blockStateModelGenerator.modelOutput);
        final var resourceLocation4 = ModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.create(block, textureMapping, blockStateModelGenerator.modelOutput);
        final var resourceLocation5 = ModelTemplates.DOOR_TOP_LEFT.create(block, textureMapping, blockStateModelGenerator.modelOutput);
        final var resourceLocation6 = ModelTemplates.DOOR_TOP_LEFT_OPEN.create(block, textureMapping, blockStateModelGenerator.modelOutput);
        final var resourceLocation7 = ModelTemplates.DOOR_TOP_RIGHT.create(block, textureMapping, blockStateModelGenerator.modelOutput);
        final var resourceLocation8 = ModelTemplates.DOOR_TOP_RIGHT_OPEN.create(block, textureMapping, blockStateModelGenerator.modelOutput);
        blockStateModelGenerator.createSimpleFlatItemModel(block.asItem());
        blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createDoor(block,
                resourceLocation,
                resourceLocation2,
                resourceLocation3,
                resourceLocation4,
                resourceLocation5,
                resourceLocation6,
                resourceLocation7,
                resourceLocation8));
    }

    private static ResourceLocation modLoc(String path) {
        return new ResourceLocation(SpookyDoors.MOD_ID, path);
    }
}
