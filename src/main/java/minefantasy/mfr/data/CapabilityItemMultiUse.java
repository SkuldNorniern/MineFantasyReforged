package minefantasy.mfr.data;

import minefantasy.mfr.init.ModDataComponents;
import net.minecraft.world.item.ItemStack;

/**
 * Multi-use item tracking (replaces Forge item capability).
 * Now backed by DataComponentType rather than a capability.
 */
public class CapabilityItemMultiUse {

	public static int getCurrentBites(ItemStack stack) {
		return stack.getOrDefault(ModDataComponents.MULTI_USE_BITES.get(), 0);
	}

	public static void setCurrentBites(ItemStack stack, int currentBites) {
		stack.set(ModDataComponents.MULTI_USE_BITES.get(), currentBites);
	}
}
