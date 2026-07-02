package minefantasy.mfr.api.weapon;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IParryable {
	boolean canParry(DamageSource source, LivingEntity blocker, ItemStack item);

	float getParryAngle(DamageSource source, LivingEntity blocker, ItemStack item);

	void onParry(DamageSource source, LivingEntity user, Entity attacker, float dam);

	boolean playCustomParrySound(LivingEntity blocker, Entity attacker, ItemStack weapon);

	float getMaxDamageParry(LivingEntity user, ItemStack weapon);

	boolean canUserParry(LivingEntity user);

	float getParryStaminaDecay(DamageSource source, ItemStack weapon);

	int getParryCooldown(DamageSource source, float dam, ItemStack weapon);
}
