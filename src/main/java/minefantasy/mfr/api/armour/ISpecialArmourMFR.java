package minefantasy.mfr.api.armour;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public interface ISpecialArmourMFR {
	float getDamageTypeValue(LivingEntity user, ItemStack armour, DamageSource src);

	@OnlyIn(Dist.CLIENT)
	float getDamageTypeDisplay(ItemStack armour, int damageType);

	float getDamageRatingValue(LivingEntity user, ItemStack armour, DamageSource src);

	@OnlyIn(Dist.CLIENT)
	float getDamageRatingDisplay(ItemStack armour, int damageType);
}
