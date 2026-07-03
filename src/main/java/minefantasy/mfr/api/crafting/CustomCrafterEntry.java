package minefantasy.mfr.api.crafting;

import minefantasy.mfr.constants.Tool;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public class CustomCrafterEntry {
	public static HashMap<ResourceLocation, CustomCrafterEntry> entries = new HashMap<>();
	public Item itemID;
	public String type;
	public float efficiency;
	public int tier;

	private CustomCrafterEntry(Item id, String type, float efficiency, int tier) {
		this.itemID = id;
		this.type = type;
		this.efficiency = efficiency;
		this.tier = tier;
	}

	public static void registerItem(ItemStack piece, String type, float efficiency, int tier) {
		ResourceLocation key = BuiltInRegistries.ITEM.getKey(piece.getItem());
		if (key != null) entries.put(key, new CustomCrafterEntry(piece.getItem(), type, efficiency, tier));
	}

	public static Tool getEntryType(ItemStack piece) {
		CustomCrafterEntry entry = getEntry(piece);
		if (entry != null) return Tool.fromName(entry.type);
		return Tool.OTHER;
	}

	public static float getEntryEfficiency(ItemStack piece) {
		CustomCrafterEntry entry = getEntry(piece);
		return entry != null ? entry.efficiency : 2.0F;
	}

	public static int getEntryTier(ItemStack piece) {
		CustomCrafterEntry entry = getEntry(piece);
		return entry != null ? entry.tier : -1;
	}

	public static CustomCrafterEntry getEntry(ItemStack piece) {
		if (piece != null) {
			ResourceLocation key = BuiltInRegistries.ITEM.getKey(piece.getItem());
			if (key != null && entries.containsKey(key)) return entries.get(key);
		}
		return null;
	}
}
