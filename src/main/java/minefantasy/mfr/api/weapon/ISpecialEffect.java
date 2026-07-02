package minefantasy.mfr.api.weapon;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ISpecialEffect {
	void onProperHit(LivingEntity user, ItemStack weapon, Entity hit, float dam);
}
