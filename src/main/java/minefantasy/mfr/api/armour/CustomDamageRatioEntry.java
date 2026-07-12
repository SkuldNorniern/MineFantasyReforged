package minefantasy.mfr.api.armour;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public class CustomDamageRatioEntry {
	public static HashMap<Identifier, CustomDamageRatioEntry> entries = new HashMap<>();
	public static HashMap<Identifier, CustomDamageRatioEntry> entriesProj = new HashMap<>();

	public float[] vars;

	private CustomDamageRatioEntry(float[] vars) {
		this.vars = vars;
	}

	public static void registerItem(ItemStack weapon, float[] vars) {
		Identifier key = BuiltInRegistries.ITEM.getKey(weapon.getItem());
		if (key != null) entries.put(key, new CustomDamageRatioEntry(vars));
	}

	public static void registerEntity(Identifier resourceLocation, float[] vars) {
		entriesProj.put(resourceLocation, new CustomDamageRatioEntry(vars));
	}

	public static float[] getTraits(ItemStack weapon) {
		Identifier key = BuiltInRegistries.ITEM.getKey(weapon.getItem());
		CustomDamageRatioEntry entry = key != null ? entries.get(key) : null;
		return entry != null ? entry.vars : null;
	}

	public static float[] getEntityTraits(Identifier resourceLocation) {
		CustomDamageRatioEntry entry = entriesProj.get(resourceLocation);
		return entry != null ? entry.vars : new float[] {1, 1, 1};
	}
}
