package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(net.minecraft.core.registries.Registries.SOUND_EVENT, MineFantasyReforged.MOD_ID);

    private static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(
                Identifier.fromNamespaceAndPath(MineFantasyReforged.MOD_ID, name)));
    }

    public static final DeferredHolder<SoundEvent, SoundEvent> ANVIL_HAMMER = register("anvil.hammer");
    public static final DeferredHolder<SoundEvent, SoundEvent> FORGE_FIRE = register("forge.fire");
    public static final DeferredHolder<SoundEvent, SoundEvent> QUERN_GRIND = register("quern.grind");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOOMERY_HISS = register("bloomery.hiss");
    public static final DeferredHolder<SoundEvent, SoundEvent> BOW_DRAW = register("bow.draw");
    public static final DeferredHolder<SoundEvent, SoundEvent> CROSSBOW_LOAD = register("crossbow.load");
    public static final DeferredHolder<SoundEvent, SoundEvent> SWORD_SWING = register("sword.swing");
    public static final DeferredHolder<SoundEvent, SoundEvent> ARMOR_EQUIP = register("armor.equip");
    public static final DeferredHolder<SoundEvent, SoundEvent> ARROW_HIT = register("arrow.hit");
}
