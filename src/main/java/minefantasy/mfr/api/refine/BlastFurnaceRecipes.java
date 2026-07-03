package minefantasy.mfr.api.refine;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class BlastFurnaceRecipes {
	private static final BlastFurnaceRecipes smeltingBase = new BlastFurnaceRecipes();
	private Map<Item, ItemStack> smeltingList = new HashMap<>();

	private BlastFurnaceRecipes() {
	}

	public static BlastFurnaceRecipes smelting() {
		return smeltingBase;
	}

	public Map<Item, ItemStack> getSmeltingList() {
		return smeltingList;
	}

	public void addRecipe(Block input, ItemStack output) {
		addRecipe(input.asItem(), output);
	}

	public void addRecipe(Item input, ItemStack output) {
		smeltingList.put(input, output);
	}

	public void addRecipe(ItemStack input, ItemStack output) {
		addRecipe(input.getItem(), output);
	}

	public void removeRecipe(ItemStack input, ItemStack output) {
		if (output.isEmpty()) return;
		for (Iterator<Entry<Item, ItemStack>> it = smeltingList.entrySet().iterator(); it.hasNext(); ) {
			Entry<Item, ItemStack> entry = it.next();
			if (!input.isEmpty() && entry.getKey() != input.getItem()) continue;
			if (ItemStack.isSameItem(entry.getValue(), output)) it.remove();
		}
	}

	public ItemStack getSmeltingResult(ItemStack input) {
		ItemStack result = smeltingList.get(input.getItem());
		return result != null ? result : ItemStack.EMPTY;
	}
}
