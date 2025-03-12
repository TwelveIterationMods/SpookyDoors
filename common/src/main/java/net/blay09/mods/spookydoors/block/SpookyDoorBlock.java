package net.blay09.mods.spookydoors.block;

import net.blay09.mods.spookydoors.ModBlockEntities;
import net.blay09.mods.spookydoors.block.entity.SpookyDoorBlockEntity;
import net.blay09.mods.spookydoors.client.SpookyDoorsClient;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class SpookyDoorBlock extends DoorBlock implements EntityBlock {
    private static final Map<Direction, VoxelShape> SHAPES = Shapes.rotateHorizontal(Block.boxZ(16f, 13f, 16f));

    public SpookyDoorBlock(BlockSetType type, Properties properties) {
        super(type, properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SpookyDoorBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
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
            } else {
                return openShape;
            }
        }
        return closedShape;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
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
        return SHAPES.get(direction);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier) {
        final var isLocalClientPlayer = level.isClientSide && entity instanceof Player player && player.isLocalPlayer();
        final var isRemoteMob = !level.isClientSide && !(entity instanceof Player);
        if (isLocalClientPlayer || isRemoteMob) {
            final var blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SpookyDoorBlockEntity spookyDoor) {
                final var facing = state.getValue(FACING);
                final var baseDoor = spookyDoor.getBaseDoor();
                var openness = baseDoor.getOpenness();

                if (openness > 0 && openness < 1) {
                    var doorFacingDirection = Vec3.atLowerCornerOf(facing.getUnitVec3i()).normalize();
                    var entityPosition = entity.position();
                    var doorPosition = Vec3.atCenterOf(pos);

                    var doorToEntity = entityPosition.subtract(doorPosition).normalize();

                    var entityVelocity = entity.getDeltaMovement();
                    double movementTowardsFacing = doorFacingDirection.dot(entityVelocity);

                    double positionAlignment = doorFacingDirection.dot(doorToEntity);

                    float adjustmentSpeed = (float) Math.abs(movementTowardsFacing) * 0.9f;

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
        super.entityInside(state, level, pos, entity, effectApplier);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (blockEntityType != ModBlockEntities.spookyDoor.get()) {
            return null;
        }

        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return null;
        }

        return level.isClientSide ? SpookyDoorBlockEntity::clientTick : SpookyDoorBlockEntity::serverTick;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState previousState, boolean wat) {
        float openness = -1f;
        // When initially placed, respect the openness from the state
        if (!previousState.is(state.getBlock())) {
            openness = state.getValue(OPEN) ? 1f : 0f;
        } else {
            // If powered was toggled, respect the openness from the state
            // We don't just blindly always slam the door open/shut on OPEN changes because we also toggle open=true at the halfway point of openness
            final var previouslyPowered = previousState.getValue(POWERED);
            final var powered = state.getValue(POWERED);
            if (powered != previouslyPowered) {
                openness = state.getValue(OPEN) ? 1f : 0f;
            }
        }
        if (openness != -1f) {
            if (level.getBlockEntity(pos) instanceof SpookyDoorBlockEntity spookyDoor) {
                spookyDoor.setOpennessBy(openness, null);
            }
        }
    }
}
