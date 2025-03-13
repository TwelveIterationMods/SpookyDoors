package net.blay09.mods.spookydoors.datagen;

import net.blay09.mods.spookydoors.ModBlocks;
import net.blay09.mods.spookydoors.SpookyDoors;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import static net.minecraft.client.data.models.BlockModelGenerators.plainVariant;

public class SpookyDoorsModelProvider extends FabricModelProvider {
    public SpookyDoorsModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        spookyDoor(blockModelGenerators, ModBlocks.spookyOakDoor);
        spookyDoor(blockModelGenerators, ModBlocks.spookySpruceDoor);
        spookyDoor(blockModelGenerators, ModBlocks.spookyBirchDoor);
        spookyDoor(blockModelGenerators, ModBlocks.spookyJungleDoor);
        spookyDoor(blockModelGenerators, ModBlocks.spookyAcaciaDoor);
        spookyDoor(blockModelGenerators, ModBlocks.spookyCherryDoor);
        spookyDoor(blockModelGenerators, ModBlocks.spookyDarkOakDoor);
        spookyDoor(blockModelGenerators, ModBlocks.spookyMangroveDoor);
        spookyDoor(blockModelGenerators, ModBlocks.spookyBambooDoor);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
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
        blockStateModelGenerator.registerSimpleFlatItemModel(block.asItem());
        blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createDoor(block,
                plainVariant(resourceLocation),
                plainVariant(resourceLocation2),
                plainVariant(resourceLocation3),
                plainVariant(resourceLocation4),
                plainVariant(resourceLocation5),
                plainVariant(resourceLocation6),
                plainVariant(resourceLocation7),
                plainVariant(resourceLocation8)));
    }

    private static ResourceLocation modLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(SpookyDoors.MOD_ID, path);
    }
}
