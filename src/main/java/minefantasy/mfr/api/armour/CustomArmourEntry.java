package minefantasy.mfr.api.armour;

import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public class CustomArmourEntry {
	public static HashMap<Identifier, CustomArmourEntry> entries = new HashMap<>();
	public boolean alterSpeed;
	public Identifier item;
	public float weight;
	public float bulkiness;
	public String AC;

	private CustomArmourEntry(Identifier item, float weight, float bulk, boolean alterSpeed, String armorClass) {
		this.alterSpeed = alterSpeed;
		this.item = item;
		this.weight = weight;
		this.bulkiness = bulk;
		this.AC = armorClass;
	}

	public static void registerItem(Item piece, ArmourDesign template) {
		registerItem(piece, template, 1.0F, template.getGroup());
	}

	public static void registerItem(Item piece, ArmourDesign template, float weightMod, String weightType) {
		// TODO: ArmourCalculator.sizes not yet ported — using 0.25 for all slots
		float divider = 0.25F;
		registerItem(piece, template.getWeight() * weightMod, template.getBulk() * divider, weightType);
	}

	public static void registerItem(ItemStack piece, ArmourDesign template, float weightMod, String weightType) {
		// TODO: ArmourCalculator.sizes not yet ported — using 0.25 for all slots
		float divider = 0.25F;
		registerItem(piece, template.getWeight() * weightMod, template.getBulk() * divider, weightType);
	}

	public static void registerItem(Item piece, float weight, float bulk, String AC) {
		registerItem(piece, weight, bulk, true, AC);
	}

	public static void registerItem(ItemStack piece, float weight, float bulk, String AC) {
		registerItem(piece, weight, bulk, true, AC);
	}

	public static void registerItem(Item piece, float weight, float bulk, boolean alterSpeed, String AC) {
		MFRLogUtil.logDebug("Added Custom " + AC + " armour: " + piece.getDescriptionId() + " Traits = " + weight + "," + bulk + " alter speed = " + alterSpeed);
		Identifier key = BuiltInRegistries.ITEM.getKey(piece);
		if (key != null) {
			entries.put(key, new CustomArmourEntry(key, weight, bulk, alterSpeed, AC));
		}
	}

	public static void registerItem(ItemStack piece, float weight, float bulk, boolean alterSpeed, String AC) {
		MFRLogUtil.logDebug("Added Custom " + AC + " armour: " + piece.getItem().getDescriptionId() + " Traits = " + weight + "," + bulk + " alter speed = " + alterSpeed);
		Identifier key = BuiltInRegistries.ITEM.getKey(piece.getItem());
		if (key != null) {
			entries.put(key, new CustomArmourEntry(key, weight, bulk, alterSpeed, AC));
		}
	}

	public static String getArmourClass(ItemStack piece) {
		CustomArmourEntry entry = getEntry(piece);
		if (entry != null) {
			return entry.AC;
		}
		// TODO: ArmourCalculator.getDefaultAD not yet ported
		return ArmourDesign.NONE.getGroup();
	}

	public static float[] getEntryVars(ItemStack piece) {
		CustomArmourEntry entry = getEntry(piece);
		if (entry != null) {
			return new float[] {entry.weight, entry.bulkiness};
		}
		// TODO: ArmourCalculator.getDefaultAD not yet ported
		return new float[] {ArmourDesign.NONE.getWeight(), ArmourDesign.NONE.getBulk()};
	}

	public static boolean doesPieceSlowDown(ItemStack piece) {
		if (!piece.isEmpty()) {
			CustomArmourEntry entry = getEntry(piece);
			if (entry != null) {
				return entry.alterSpeed;
			}
		}
		return false;
	}

	public static CustomArmourEntry getEntry(ItemStack piece) {
		if (piece != null) {
			Identifier key = BuiltInRegistries.ITEM.getKey(piece.getItem());
			if (key != null) {
				return entries.get(key);
			}
		}
		return null;
	}
}
