package minefantasy.mfr.api.archery;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public interface ISpecialBow {
	Entity modifyArrow(ItemStack bow, Entity arrow);

	float getMaxCharge(ItemStack bow);

	float getVelocity(ItemStack bow);

	float getSpread(ItemStack bow);
}
