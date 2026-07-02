package minefantasy.mfr.api.archery;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public interface IArrowMFR {
	float getDamageModifier(ItemStack arrow);

	float getGravityModifier(ItemStack arrow);

	float getBreakChance(Entity entityArrow, ItemStack arrow);

	void onHitEntity(Entity arrowInstance, Entity shooter, Entity hit, float damage);
}
