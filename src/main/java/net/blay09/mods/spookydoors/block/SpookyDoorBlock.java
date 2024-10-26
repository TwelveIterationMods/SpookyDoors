package net.blay09.mods.spookydoors.block;

import net.blay09.mods.spookydoors.ModBlockEntities;
import net.blay09.mods.spookydoors.block.entity.SpookyDoorBlockEntity;
import net.blay09.mods.spookydoors.client.SpookyDoorsClient;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SpookyDoorBlock extends DoorBlock implements EntityBlock {
    public SpookyDoorBlock(BlockSetType type, Properties properties) {
        super(type, properties);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SpookyDoorBlockEntity(pos, state);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        final var closedShape = super.getShape(state, level, pos, context);
        final var openShape = super.getShape(state.setValue(OPEN, true), level, pos, context);
        final var blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof SpookyDoorBlockEntity spookyDoor) {
            final var baseDoor = spookyDoor.getBaseDoor();
            final var openness = baseDoor.getOpenness();
            if (openness == 0f) {
                return closedShape;
            } else if (openness < 1f) {
                return Shapes.join(closedShape, openShape, BooleanOp.AND);
                // final var centerShape = Shapes.box(0.4f, 0f, 0.4f, 0.6f, 1f, 0.6f);
                // return Shapes.join(Shapes.join(closedShape, openShape, BooleanOp.AND), centerShape, BooleanOp.OR);
            } else {
                return openShape;
            }
        }
        return closedShape;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        final var blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof SpookyDoorBlockEntity spookyDoor) {
            final var baseDoor = spookyDoor.getBaseDoor();
            final var openness = baseDoor.getOpenness();
            if (openness > 0f && openness < 1f) {
                return Shapes.block();
            } else {
                return super.getShape(state, level, pos, context);
            }
        }
        return super.getShape(state, level, pos, context);
    }

    public static VoxelShape getOutlineShape(BlockState state) {
        final var direction = state.getValue(FACING);
        return switch (direction) {
            case NORTH -> NORTH_AABB;
            case WEST -> WEST_AABB;
            case SOUTH -> SOUTH_AABB;
            default -> EAST_AABB;
        };
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        final var isLocalClientPlayer = level.isClientSide && entity instanceof Player player && player.isLocalPlayer();
        final var isRemoteMob = !level.isClientSide && !(entity instanceof Player);
        if (isLocalClientPlayer || isRemoteMob) {
            final var blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SpookyDoorBlockEntity spookyDoor) {
                final var facing = state.getValue(FACING);
                final var baseDoor = spookyDoor.getBaseDoor();
                var openness = baseDoor.getOpenness();

                if (openness > 0 && openness < 1) {
                    // Get the direction the door is facing and the opposite direction
                    var doorFacingDirection = Vec3.atLowerCornerOf(facing.getNormal()).normalize();
                    var entityPosition = entity.position();
                    var doorPosition = Vec3.atCenterOf(pos);

                    // Vector pointing from the door to the entity
                    var doorToEntity = entityPosition.subtract(doorPosition).normalize();

                    // Calculate movement projection onto door's facing direction
                    var entityVelocity = entity.getDeltaMovement();
                    if (entity instanceof Mob) {
                        System.out.println(entityVelocity);
                    }
                    double movementTowardsFacing = doorFacingDirection.dot(entityVelocity);

                    // Check if the entity is in the opposite direction of the door's facing
                    double positionAlignment = doorFacingDirection.dot(doorToEntity);

                    // Use the entity's speed to determine how fast the door opens/closes
                    float adjustmentSpeed = (float) Math.abs(movementTowardsFacing) * 0.9f; // Scale the adjustment speed

                    if (positionAlignment < 0 && movementTowardsFacing > 0) {
                        openness = Math.min(openness + adjustmentSpeed, 1f);
                    } else if (positionAlignment > 0 && movementTowardsFacing < 0 && openness < 0.8f) {
                        openness = Math.max(openness - adjustmentSpeed, 0.0f);
                    }

                    spookyDoor.setOpennessBy(openness, entity);
                    if (isLocalClientPlayer) {
                        SpookyDoorsClient.setActiveDoor(spookyDoor);
                    }
                }
            }
        }
        super.entityInside(state, level, pos, entity);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (blockEntityType != ModBlockEntities.SPOOKY_DOOR.get()) {
            return null;
        }

        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return null;
        }

        return level.isClientSide ? SpookyDoorBlockEntity::clientTick : SpookyDoorBlockEntity::serverTick;
    }

}
