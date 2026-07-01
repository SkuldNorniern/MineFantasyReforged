package minefantasy.mfr.api.weapon;

import net.minecraft.world.item.ItemStack;

public interface ISharpenable {
	float getMaxSharpness(ItemStack item);

	float getDamagePercentMax(ItemStack item);

	float getSharpUsesModifier(ItemStack item);
}
