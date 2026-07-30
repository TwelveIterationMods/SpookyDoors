package net.blay09.mods.spookydoors.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

public class SpookyDoorsAPI {

    private static final InternalMethods internalMethods = loadInternalMethods();

    private static InternalMethods loadInternalMethods() {
        try {
            return (InternalMethods) Class.forName("net.blay09.mods.spookydoors.InternalMethodsImpl").getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to load Spooky Doors API", e);
        }
    }

    public static float getPercentOpen(Level level, BlockPos pos) {
        return internalMethods.getPercentOpen(level, pos);
    }

    public static void operateDoor(Level level, BlockPos pos, float percentOpen) {
        operateDoor(level, pos, null, percentOpen);
    }

    public static void operateDoor(Level level, BlockPos pos, @Nullable Entity entity, float percentOpen) {
        internalMethods.operateDoor(level, pos, entity, percentOpen);
    }
}
