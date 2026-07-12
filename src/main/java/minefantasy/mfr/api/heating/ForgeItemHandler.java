package minefantasy.mfr.api.heating;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ForgeItemHandler {
	public static List<ForgeFuel> forgeFuel = new ArrayList<>();
	public static int forgeMaxTemp = 0;

	public static void addFuel(ItemStack item, int fuel, int heat, boolean willLight) {
		forgeFuel.add(new ForgeFuel(item, fuel, heat, willLight));
	}

	public static ForgeFuel getStats(ItemStack item) {
		if (item.isEmpty()) return null;
		for (ForgeFuel fuel : forgeFuel) {
			if (fuel != null && fuel.fuel.getItem() == item.getItem()) {
				return fuel;
			}
		}
		return null;
	}

	public static float getForgeFuel(ItemStack item) {
		if (item.isEmpty()) return 0;
		for (ForgeFuel fuel : forgeFuel) {
			if (fuel != null && fuel.fuel.getItem() == item.getItem()) {
				return fuel.duration;
			}
		}
		return 0;
	}

	public static boolean willLight(ItemStack item) {
		if (item.isEmpty()) return false;
		for (ForgeFuel fuel : forgeFuel) {
			if (fuel != null && fuel.fuel.getItem() == item.getItem()) {
				return fuel.doesLight;
			}
		}
		return false;
	}

	public static float getForgeFuelWithoutSubid(Item id) {
		for (ForgeFuel fuel : forgeFuel) {
			if (fuel != null && fuel.fuel.getItem() == id) {
				return fuel.baseHeat;
			}
		}
		return 0;
	}

	public static int getForgeHeat(ItemStack item) {
		if (item.isEmpty()) return 0;
		for (ForgeFuel fuel : forgeFuel) {
			if (fuel != null && fuel.fuel.getItem() == item.getItem()) {
				return fuel.baseHeat;
			}
		}
		return 0;
	}

	public static int getForgeHeat(Item item) {
		for (ForgeFuel fuel : forgeFuel) {
			if (fuel != null && fuel.fuel.getItem() == item) {
				return fuel.baseHeat;
			}
		}
		return 0;
	}
}
