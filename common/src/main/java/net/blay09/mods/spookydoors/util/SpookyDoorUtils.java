package net.blay09.mods.spookydoors.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class SpookyDoorUtils {
    public static boolean canOperate(BlockState state) {
        return state.getBlock() instanceof DoorBlock doorBlock && doorBlock.type().canOpenByHand();
    }

    public static BlockPos getBasePos(BlockPos pos, BlockState state) {
        if (state.hasProperty(DoorBlock.HALF) && state.getValue(DoorBlock.HALF) == DoubleBlockHalf.UPPER) {
            return pos.below();
        }

        return pos;
    }

    public static float getDefaultOpenness(BlockState state) {
        return state.hasProperty(DoorBlock.OPEN) && state.getValue(DoorBlock.OPEN) ? 1f : 0f;
    }
}
