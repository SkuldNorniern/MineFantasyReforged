package minefantasy.mfr.api.weapon;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IPowerAttack {
	int getParryModifier(ItemStack weapon, LivingEntity user, Entity target);

	void onPowerAttack(float dam, LivingEntity user, Entity target, boolean properHit);
}
