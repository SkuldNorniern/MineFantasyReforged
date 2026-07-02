package minefantasy.mfr.api.weapon;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IDamageModifier {
	float modifyDamage(ItemStack item, LivingEntity wielder, Entity hit, float initialDam, boolean properHit);
}
