package minefantasy.mfr.api.armour;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;

public interface IElementalResistance {
	float getArrowDeflection(ItemStack item, DamageSource source);

	float getMagicResistance(ItemStack item, DamageSource source);

	float getFireResistance(ItemStack item, DamageSource source);

	float getBaseResistance(ItemStack item, DamageSource source);
}
