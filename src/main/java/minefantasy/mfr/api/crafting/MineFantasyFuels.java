package minefantasy.mfr.api.crafting;

import minefantasy.mfr.api.heating.ForgeFuel;
import minefantasy.mfr.api.heating.ForgeItemHandler;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public class MineFantasyFuels {

	// Replaces OreDictionary "Carbon-N" ore entries
	private static final Map<Item, Integer> carbonMap = new HashMap<>();

	public static void addForgeFuel(Object input, float time, int temperature) {
		addForgeFuel(input, time, temperature, false, false);
	}

	public static void addForgeFuel(Object input, float time, int temperature, boolean willLight) {
		addForgeFuel(input, time, temperature, willLight, false);
	}

	public static void addForgeFuel(Object input, float time, int temperature, boolean willLight, boolean refined) {
		ItemStack item = convert(input);
		if (!item.isEmpty()) {
			ForgeItemHandler.forgeFuel.add(new ForgeFuel(item, time, temperature, willLight, refined));
			if ((int) (temperature * 1.25) > ForgeItemHandler.forgeMaxTemp) {
				ForgeItemHandler.forgeMaxTemp = (int) (temperature * 1.25);
			}
		}
	}

	public static void addCarbon(Object input, int uses) {
		ItemStack itemstack = convert(input);
		if (!itemstack.isEmpty()) {
			carbonMap.put(itemstack.getItem(), uses);
		}
	}

	public static int getCarbon(ItemStack item) {
		if (item.isEmpty()) return 0;
		Integer uses = carbonMap.get(item.getItem());
		return uses != null ? uses : 0;
	}

	public static boolean isCarbon(ItemStack item) {
		return getCarbon(item) > 0;
	}

	public static ItemStack convert(Object input) {
		if (input == null) return ItemStack.EMPTY;
		if (input instanceof ItemStack stack) return stack;
		if (input instanceof Block block) return new ItemStack(block.asItem());
		if (input instanceof Item item) return new ItemStack(item);
		return ItemStack.EMPTY;
	}
}
