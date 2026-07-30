package net.blay09.mods.spookydoors.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.commands.BalmCommands;
import net.blay09.mods.spookydoors.SpookyDoorsConfig;
import net.blay09.mods.spookydoors.core.ServerSpookyDoor;
import net.blay09.mods.spookydoors.level.SpookyDoorSavedData;
import net.blay09.mods.spookydoors.network.ClientboundDoorStatePacket;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.world.level.block.DoorBlock;

import static net.blay09.mods.spookydoors.SpookyDoors.id;

public class SpookyDoorsCommand {

    private static final Identifier PERMISSION_TOGGLE = id("command.spookydoors.toggle");

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        BalmCommands.registerPermission(PERMISSION_TOGGLE, Permissions.COMMANDS_GAMEMASTER);

        dispatcher.register(Commands.literal("spookydoors")
                .requires(BalmCommands.requirePermission(PERMISSION_TOGGLE))
                .then(Commands.literal("on")
                        .then(Commands.argument("pos", BlockPosArgument.blockPos())
                                .executes(context -> setDoorSpooky(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "pos"), true))))
                .then(Commands.literal("off")
                        .then(Commands.argument("pos", BlockPosArgument.blockPos())
                                .executes(context -> setDoorSpooky(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "pos"), false))))
                .then(Commands.literal("toggle")
                        .then(Commands.argument("pos", BlockPosArgument.blockPos())
                                .executes(context -> toggleDoorSpooky(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "pos"))))));
    }

    private static int toggleDoorSpooky(CommandSourceStack source, BlockPos pos) {
        final var level = source.getLevel();
        final var state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof DoorBlock)) {
            source.sendFailure(Component.translatable("commands.spookydoors.not_door", pos.toShortString()));
            return 0;
        }

        final var savedData = SpookyDoorSavedData.get(level);
        final var door = savedData.of(pos, state);
        final var newActive = !savedData.isIndividuallySpooky(door.pos());
        return setDoorSpooky(source, pos, newActive);
    }

    private static int setDoorSpooky(CommandSourceStack source, BlockPos pos, boolean newActive) {
        final var level = source.getLevel();
        final var state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof DoorBlock)) {
            source.sendFailure(Component.translatable("commands.spookydoors.not_door", pos.toShortString()));
            return 0;
        }

        final var savedData = SpookyDoorSavedData.get(level);
        final var door = savedData.of(pos, state);
        door.spooky(newActive);

        if (door instanceof ServerSpookyDoor serverSpookyDoor) {
            serverSpookyDoor.syncToClients();
            if (source.getEntity() instanceof ServerPlayer player) {
                Balm.networking().sendTo(player, new ClientboundDoorStatePacket(serverSpookyDoor.pos(), serverSpookyDoor.percentOpen(), serverSpookyDoor.spooky()));
            }
        }

        final var forced = SpookyDoorsConfig.getActive().spookyDoorActivation == SpookyDoorsConfig.SpookyDoorActivation.FORCED;
        final var newActiveText = Component.translatable(newActive ? "commands.spookydoors.state.on" : "commands.spookydoors.state.off");
        source.sendSuccess(() -> Component.translatable(forced ? "commands.spookydoors.set_forced" : "commands.spookydoors.set", newActiveText), true);
        return 1;
    }
}
