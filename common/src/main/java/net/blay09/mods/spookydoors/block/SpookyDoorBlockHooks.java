package net.blay09.mods.spookydoors.block;

import net.blay09.mods.spookydoors.SpookyDoorsConfig;
import net.blay09.mods.spookydoors.client.SpookyDoorsClient;
import net.blay09.mods.spookydoors.core.ServerSpookyDoor;
import net.blay09.mods.spookydoors.core.SpookyDoorProvider;
import net.blay09.mods.spookydoors.item.ModItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SpookyDoorBlockHooks {

    @Nullable
    public static RenderShape getRenderShape(BlockState state) {
        return isDoor(state) ? RenderShape.INVISIBLE : null;
    }

    @Nullable
    public static VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos) {
        if (level instanceof Level worldLevel && isSpookyDoor(state, worldLevel, pos)) {
            final var openness = SpookyDoorProvider.get(worldLevel).of(pos, state).percentOpen();
            final var closedShape = SpookyDoorShapes.getDoorGeometry(state, false).shape();
            if (openness <= 0f) {
                return closedShape;
            } else if (openness >= 1f) {
                return SpookyDoorShapes.getDoorGeometry(state, true).shape();
            }

            // For partially opened doors, only the hinge should actually be collision. The player can run through the
            // partially opened door to open or close it with their body.
            return Shapes.join(closedShape, SpookyDoorShapes.getDoorGeometry(state, true).shape(), BooleanOp.AND);
        }

        return null;
    }

    @Nullable
    public static VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        if (level instanceof Level worldLevel && isSpookyDoor(state, worldLevel, pos)) {
            final var door = SpookyDoorProvider.get(worldLevel).of(pos, state);
            final var openness = door.percentOpen();
            return SpookyDoorShapes.getInteractionShape(state, openness);
        }

        return null;
    }

    public static void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!isSpookyDoor(state, level, pos)) {
            return;
        }

        final var isLocalClientPlayer = level.isClientSide && entity instanceof Player player && player.isLocalPlayer();
        final var isRemoteMob = !level.isClientSide && !(entity instanceof Player);
        if (!isLocalClientPlayer && !isRemoteMob) {
            return;
        }

        final var facing = state.getValue(DoorBlock.FACING);
        final var door = SpookyDoorProvider.get(level).of(pos, state);
        var percentOpen = door.percentOpen();
        if (percentOpen > 0 && percentOpen < 1) {
            final var doorFacingDirection = Vec3.atLowerCornerOf(facing.getNormal()).normalize();
            final var entityPosition = entity.position();
            final var doorPosition = Vec3.atCenterOf(pos);
            final var doorToEntity = entityPosition.subtract(doorPosition).normalize();
            final var entityVelocity = entity.getDeltaMovement();
            final var movementTowardsFacing = doorFacingDirection.dot(entityVelocity);
            final var positionAlignment = doorFacingDirection.dot(doorToEntity);
            final var adjustmentSpeed = (float) Math.abs(movementTowardsFacing) * 0.9f;

            if (positionAlignment < 0 && movementTowardsFacing > 0) {
                percentOpen = Math.min(percentOpen + adjustmentSpeed, 1f);
            } else if (positionAlignment > 0 && movementTowardsFacing < 0 && percentOpen < 0.8f) {
                percentOpen = Math.max(percentOpen - adjustmentSpeed, 0.0f);
            }

            door.operate(entity, percentOpen);
            if (isLocalClientPlayer) {
                SpookyDoorsClient.setActiveDoorAndDirty(level, door.pos());
            }
        }
    }

    public static void onPlace(BlockState state, Level level, BlockPos pos, BlockState previousState) {
        if (!isSpookyDoor(state, level, pos)) {
            return;
        }

        // TODO Is this really necessary?
        float openness = -1f;
        if (!previousState.is(state.getBlock())) {
            openness = state.getValue(DoorBlock.OPEN) ? 1f : 0f;
        } else if (state.hasProperty(DoorBlock.POWERED) && previousState.hasProperty(DoorBlock.POWERED)) {
            final var previouslyPowered = previousState.getValue(DoorBlock.POWERED);
            final var powered = state.getValue(DoorBlock.POWERED);
            if (powered != previouslyPowered) {
                openness = state.getValue(DoorBlock.OPEN) ? 1f : 0f;
            }
        }

        if (openness != -1f) {
            SpookyDoorProvider.get(level).of(pos, state).percentOpen(openness);
        }
    }

    public static void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState) {
        if (isDoor(state) && !state.is(newState.getBlock())) {
            SpookyDoorProvider.get(level).remove(pos, state);
        }
    }

    @Nullable
    public static InteractionResult use(DoorBlock doorBlock, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand) {
        final var ghastTearResult = useGhastTear(state, level, pos, player, hand);
        if (ghastTearResult != null) {
            return ghastTearResult;
        }

        if (!isSpookyDoor(state, level, pos)) {
            return null;
        }

        final var honeycombResult = useHoneycomb(state, level, pos, player, hand);
        if (honeycombResult != null) {
            return honeycombResult;
        }

        if (!doorBlock.type().canOpenByHand()) {
            return null;
        }

        final var targetOpen = !state.getValue(DoorBlock.OPEN);
        final var door = SpookyDoorProvider.get(level).of(pos, state);
        door.operate(player, targetOpen ? 1f : 0f);
        level.gameEvent(player, targetOpen ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Nullable
    private static InteractionResult useGhastTear(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand) {
        final var config = SpookyDoorsConfig.getActive();
        if (!config.allowItemToHauntDoors || config.spookyDoorActivation == SpookyDoorsConfig.SpookyDoorActivation.FORCED) {
            return null;
        }

        final var itemStack = player.getItemInHand(hand);
        if (!itemStack.is(ModItemTags.HAUNTS_DOORS)) {
            return null;
        }

        final var door = SpookyDoorProvider.get(level).of(pos, state);
        if (door.spooky()) {
            return null;
        }

        door.spooky(true);

        if (!level.isClientSide) {
            if (door instanceof ServerSpookyDoor serverSpookyDoor) {
                serverSpookyDoor.syncToClients();
            }

            level.playSound(null, pos, SoundEvents.GHAST_WARN, SoundSource.BLOCKS, 1f, 1f);
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Nullable
    private static InteractionResult useHoneycomb(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand) {
        final var config = SpookyDoorsConfig.getActive();
        if (!config.allowItemToExorciseDoors || config.spookyDoorActivation == SpookyDoorsConfig.SpookyDoorActivation.FORCED) {
            return null;
        }

        final var itemStack = player.getItemInHand(hand);
        if (!itemStack.is(ModItemTags.EXORCISES_DOORS)) {
            return null;
        }

        final var door = SpookyDoorProvider.get(level).of(pos, state);
        door.spooky(false);

        if (!level.isClientSide) {
            if (door instanceof ServerSpookyDoor serverSpookyDoor) {
                serverSpookyDoor.syncToClients();
            }

            level.playSound(null, pos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1f, 1f);
            level.levelEvent(player, 3003, pos, 0);
            player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    public static void setOpen(DoorBlock doorBlock, Level level, BlockState state, BlockPos pos, boolean open) {
        if (state.is(doorBlock) && isSpookyDoor(state, level, pos) && state.getValue(DoorBlock.OPEN) != open) {
            final var openness = open ? 1f : 0f;
            final var door = SpookyDoorProvider.get(level).of(pos, state);
            door.percentOpen(openness);
            if (door instanceof ServerSpookyDoor serverSpookyDoor) {
                serverSpookyDoor.syncToClients();
            }
        }
    }

    private static boolean isDoor(BlockState state) {
        return state.getBlock() instanceof DoorBlock;
    }

    public static boolean isSpookyDoor(BlockState state, Level level, BlockPos pos) {
        return isDoor(state) && SpookyDoorProvider.get(level).of(pos, state).spooky();
    }
}
