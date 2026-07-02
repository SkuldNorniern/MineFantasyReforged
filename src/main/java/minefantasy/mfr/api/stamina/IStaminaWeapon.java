package minefantasy.mfr.api.stamina;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IStaminaWeapon {
	float getStaminaDrainOnHit(LivingEntity user, ItemStack item);
}
