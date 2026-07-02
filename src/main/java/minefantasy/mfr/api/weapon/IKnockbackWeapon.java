package minefantasy.mfr.api.weapon;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IKnockbackWeapon {
	float getAddedKnockback(LivingEntity user, ItemStack item);
}
