package minefantasy.mfr.api.armour;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public class CustomDamageRatioEntry {
	public static HashMap<ResourceLocation, CustomDamageRatioEntry> entries = new HashMap<>();
	public static HashMap<ResourceLocation, CustomDamageRatioEntry> entriesProj = new HashMap<>();

	public float[] vars;

	private CustomDamageRatioEntry(float[] vars) {
		this.vars = vars;
	}

	public static void registerItem(ItemStack weapon, float[] vars) {
		ResourceLocation key = BuiltInRegistries.ITEM.getKey(weapon.getItem());
		if (key != null) entries.put(key, new CustomDamageRatioEntry(vars));
	}

	public static void registerEntity(ResourceLocation resourceLocation, float[] vars) {
		entriesProj.put(resourceLocation, new CustomDamageRatioEntry(vars));
	}

	public static float[] getTraits(ItemStack weapon) {
		ResourceLocation key = BuiltInRegistries.ITEM.getKey(weapon.getItem());
		CustomDamageRatioEntry entry = key != null ? entries.get(key) : null;
		return entry != null ? entry.vars : null;
	}

	public static float[] getEntityTraits(ResourceLocation resourceLocation) {
		CustomDamageRatioEntry entry = entriesProj.get(resourceLocation);
		return entry != null ? entry.vars : new float[] {1, 1, 1};
	}
}
