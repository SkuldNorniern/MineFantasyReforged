package minefantasy.mfr.api.weapon;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public interface ISpecialCombatMob {
	boolean canParry(DamageSource source);

	void onParry(DamageSource source, Entity attacker, float dam);
}
