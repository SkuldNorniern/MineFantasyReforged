package minefantasy.mfr.api.armour;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public interface IPowerArmour {

	boolean isFullyArmoured();

	float modifyDamage(LivingEntity user, float damage, DamageSource src);

	boolean isPowered();

	boolean isArmoured(String limb);
}
