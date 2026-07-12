package minefantasy.mfr.api.farming;

import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public class CustomHoeEntry {
	public static HashMap<Identifier, CustomHoeEntry> entries = new HashMap<>();
	public Identifier item;
	public float efficiency;

	private CustomHoeEntry(Identifier hoe, float efficiency) {
		this.item = hoe;
		this.efficiency = efficiency;
	}

	public static void registerItem(ItemStack hoeStack, float efficiency) {
		Identifier key = BuiltInRegistries.ITEM.getKey(hoeStack.getItem());
		if (key == null) return;
		MFRLogUtil.logDebug("Added Custom hoe: " + hoeStack.getItem().getDescriptionId() + " Efficiency = " + efficiency);
		entries.put(key, new CustomHoeEntry(key, efficiency));
	}

	public static float getEntryEfficiency(ItemStack piece, float defaultValue) {
		CustomHoeEntry entry = getEntry(piece);
		return entry != null ? entry.efficiency : defaultValue;
	}

	public static CustomHoeEntry getEntry(ItemStack hoe) {
		if (hoe != null) {
			Identifier key = BuiltInRegistries.ITEM.getKey(hoe.getItem());
			if (key != null && entries.containsKey(key)) return entries.get(key);
		}
		return null;
	}
}
