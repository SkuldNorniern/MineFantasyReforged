package minefantasy.mfr.api.weapon;

import net.minecraft.world.entity.LivingEntity;

public interface IWeightedWeapon {
	float getBalance(LivingEntity user);
}
