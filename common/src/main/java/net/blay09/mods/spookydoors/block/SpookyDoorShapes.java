package net.blay09.mods.spookydoors.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SpookyDoorShapes {

    private static final DoorGeometry SOUTH_SHAPE = doorGeometry(0, 0, 16, 3);
    private static final DoorGeometry NORTH_SHAPE = doorGeometry(0, 13, 16, 16);
    private static final DoorGeometry WEST_SHAPE = doorGeometry(13, 0, 16, 16);
    private static final DoorGeometry EAST_SHAPE = doorGeometry(0, 0, 3, 16);

    public static VoxelShape getInteractionShape(BlockState state, float openness) {
        if (openness <= 0f) {
            return getDoorGeometry(state, false).shape();
        } else if (openness >= 1f) {
            return getDoorGeometry(state, true).shape();
        }

        return getRotatedShape(state, openness);
    }

    public static DoorGeometry getDoorGeometry(BlockState state, boolean open) {
        final var direction = state.getValue(DoorBlock.FACING);
        final var rightHinge = state.getValue(DoorBlock.HINGE) == DoorHingeSide.RIGHT;
        return switch (direction) {
            case EAST -> !open ? EAST_SHAPE : rightHinge ? NORTH_SHAPE : SOUTH_SHAPE;
            case SOUTH -> !open ? SOUTH_SHAPE : rightHinge ? EAST_SHAPE : WEST_SHAPE;
            case WEST -> !open ? WEST_SHAPE : rightHinge ? SOUTH_SHAPE : NORTH_SHAPE;
            case NORTH -> !open ? NORTH_SHAPE : rightHinge ? WEST_SHAPE : EAST_SHAPE;
            default -> EAST_SHAPE;
        };
    }

    private static VoxelShape getRotatedShape(BlockState state, float openness) {
        final var segments = 8;
        final var facing = state.getValue(DoorBlock.FACING);
        final var hinge = state.getValue(DoorBlock.HINGE);
        final var angle = openness * Math.PI / 2 * (hinge == DoorHingeSide.LEFT ? 1 : -1);
        final var pivot = getPivot(facing, hinge);
        final var bounds = getDoorGeometry(state, false).bounds();

        // VoxelShapes are axis-aligned boxes, so we approximate the rotated shape with a few boxes
        var shape = Shapes.empty();
        for (var i = 0; i < segments; i++) {
            final double minX;
            final double maxX;
            final double minZ;
            final double maxZ;
            if (bounds.maxX - bounds.minX > bounds.maxZ - bounds.minZ) {
                minX = bounds.minX + (bounds.maxX - bounds.minX) * i / segments;
                maxX = bounds.minX + (bounds.maxX - bounds.minX) * (i + 1) / segments;
                minZ = bounds.minZ;
                maxZ = bounds.maxZ;
            } else {
                minX = bounds.minX;
                maxX = bounds.maxX;
                minZ = bounds.minZ + (bounds.maxZ - bounds.minZ) * i / segments;
                maxZ = bounds.minZ + (bounds.maxZ - bounds.minZ) * (i + 1) / segments;
            }

            final var cornerA = rotate(minX, minZ, pivot, angle);
            final var cornerB = rotate(minX, maxZ, pivot, angle);
            final var cornerC = rotate(maxX, minZ, pivot, angle);
            final var cornerD = rotate(maxX, maxZ, pivot, angle);

            shape = Shapes.or(shape, Block.box(
                    clipToBlock(Math.min(Math.min(cornerA.x, cornerB.x), Math.min(cornerC.x, cornerD.x))),
                    0,
                    clipToBlock(Math.min(Math.min(cornerA.z, cornerB.z), Math.min(cornerC.z, cornerD.z))),
                    clipToBlock(Math.max(Math.max(cornerA.x, cornerB.x), Math.max(cornerC.x, cornerD.x))),
                    16,
                    clipToBlock(Math.max(Math.max(cornerA.z, cornerB.z), Math.max(cornerC.z, cornerD.z)))));
        }
        return shape;
    }

    public static Vec2d getPivot(Direction facing, DoorHingeSide hinge) {
        final var low = 1.5;
        final var high = 14.5;
        return switch (hinge) {
            case LEFT -> switch (facing) {
                case WEST -> new Vec2d(high, high);
                case SOUTH -> new Vec2d(high, low);
                case NORTH -> new Vec2d(low, high);
                default -> new Vec2d(low, low);
            };
            case RIGHT -> switch (facing) {
                case WEST -> new Vec2d(high, low);
                case SOUTH -> new Vec2d(low, low);
                case NORTH -> new Vec2d(high, high);
                default -> new Vec2d(low, high);
            };
        };
    }

    private static Vec2d rotate(double x, double z, Vec2d pivot, double angle) {
        final var cos = Math.cos(angle);
        final var sin = Math.sin(angle);
        final var deltaX = x - pivot.x;
        final var deltaZ = z - pivot.z;
        return new Vec2d(pivot.x + cos * deltaX + sin * deltaZ, pivot.z - sin * deltaX + cos * deltaZ);
    }

    private static double clipToBlock(double value) {
        return Math.max(0, Math.min(16, value));
    }

    private static DoorGeometry doorGeometry(double minX, double minZ, double maxX, double maxZ) {
        return new DoorGeometry(Block.box(minX, 0, minZ, maxX, 16, maxZ), new Rect2d(minX, minZ, maxX, maxZ));
    }

    public record DoorGeometry(VoxelShape shape, Rect2d bounds) {
    }

    public record Rect2d(double minX, double minZ, double maxX, double maxZ) {
    }

    public record Vec2d(double x, double z) {
    }
}
