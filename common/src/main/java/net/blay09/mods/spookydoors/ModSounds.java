package net.blay09.mods.spookydoors;

import net.blay09.mods.balm.api.DeferredObject;
import net.blay09.mods.balm.api.sound.BalmSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ModSounds {
    public static DeferredObject<SoundEvent> doorCreak;

    public static void initialize(BalmSounds sounds) {
        doorCreak = sounds.register(new ResourceLocation(SpookyDoors.MOD_ID, "door_creak"));
    }
}
