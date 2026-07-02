package minefantasy.mfr.api.stamina;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IWornStaminaItem {
	float getDecayModifier(LivingEntity user, ItemStack item);

	float getRegenModifier(LivingEntity user, ItemStack item);

	float getIdleModifier(LivingEntity user, ItemStack item);
}
