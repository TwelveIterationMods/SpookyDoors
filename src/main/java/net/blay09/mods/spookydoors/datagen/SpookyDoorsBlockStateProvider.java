package net.blay09.mods.spookydoors.datagen;

import net.blay09.mods.spookydoors.ModBlocks;
import net.blay09.mods.spookydoors.SpookyDoors;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.DoorBlock;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class SpookyDoorsBlockStateProvider extends BlockStateProvider {
    public SpookyDoorsBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SpookyDoors.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        spookyDoor(ModBlocks.SPOOKY_OAK_DOOR.get());
        spookyDoor(ModBlocks.SPOOKY_SPRUCE_DOOR.get());
        spookyDoor(ModBlocks.SPOOKY_BIRCH_DOOR.get());
        spookyDoor(ModBlocks.SPOOKY_JUNGLE_DOOR.get());
        spookyDoor(ModBlocks.SPOOKY_ACACIA_DOOR.get());
        spookyDoor(ModBlocks.SPOOKY_CHERRY_DOOR.get());
        spookyDoor(ModBlocks.SPOOKY_DARK_OAK_DOOR.get());
        spookyDoor(ModBlocks.SPOOKY_MANGROVE_DOOR.get());
        spookyDoor(ModBlocks.SPOOKY_BAMBOO_DOOR.get());
    }

    private void spookyDoor(DoorBlock block) {
        final var id = BuiltInRegistries.BLOCK.getKey(block);
        final var bottomTexture = modLoc("block/" + id.getPath() + "_bottom");
        final var topTexture = modLoc("block/" + id.getPath() + "_top");
        doorBlock(block,
                doorModel(id.getPath() + "_bottom_left", modLoc("block/door_bottom_left"), bottomTexture, topTexture),
                models().doorBottomLeftOpen(id.getPath() + "_bottom_left_open", bottomTexture, topTexture),
                doorModel(id.getPath() + "_bottom_right", modLoc("block/door_bottom_right"), bottomTexture, topTexture),
                models().doorBottomRightOpen(id.getPath() + "_bottom_right_open", bottomTexture, topTexture),
                doorModel(id.getPath() + "_top_left", modLoc("block/door_top_left"), bottomTexture, topTexture),
                models().doorTopLeftOpen(id.getPath() + "_top_left_open", bottomTexture, topTexture),
                doorModel(id.getPath() + "_top_right", modLoc("block/door_top_right"), bottomTexture, topTexture),
                models().doorTopRightOpen(id.getPath() + "_top_right_open", bottomTexture, topTexture));
    }

    private BlockModelBuilder doorModel(String name, ResourceLocation model, ResourceLocation bottomTexture, ResourceLocation topTexture) {
        return models().withExistingParent(name, model).texture("bottom", bottomTexture).texture("top", topTexture);
    }
}

