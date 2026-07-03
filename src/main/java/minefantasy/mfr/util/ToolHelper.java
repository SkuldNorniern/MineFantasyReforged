package minefantasy.mfr.util;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.api.crafting.CustomCrafterEntry;
import minefantasy.mfr.api.tier.IToolMaterial;
import minefantasy.mfr.api.tool.IToolMFR;
import minefantasy.mfr.api.weapon.ISharpenable;
import minefantasy.mfr.constants.Constants;
import minefantasy.mfr.constants.Tool;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

import java.util.ArrayList;

public class ToolHelper {

	public static final String sharpnessLevelNBT = "MF_Sharpness_Level";
	private static final String specialItem = "MF_SpecialItemType";

	public static boolean shouldShowTooltip(ItemStack stack) {
		Tool tool = getToolTypeFromStack(stack);
		return !(tool == Tool.HANDS || tool == Tool.OTHER);
	}

	public static float getCrafterEfficiency(ItemStack tool) {
		if (tool.isEmpty()) {
			return 1F;
		}
		if (tool.getItem() instanceof IToolMFR) {
			return ((IToolMFR) tool.getItem()).getEfficiency(tool);
		}
		return CustomCrafterEntry.getEntryEfficiency(tool);
	}

	public static int getCrafterTier(ItemStack tool) {
		if (tool.isEmpty()) {
			return 0;
		}
		if (tool.getItem() instanceof IToolMFR) {
			return ((IToolMFR) tool.getItem()).getTier(tool);
		}
		return CustomCrafterEntry.getEntryTier(tool);
	}

	public static boolean isStackValidWashTool(ItemStack stack) {
		return ToolHelper.getToolTypeFromStack(stack) == Tool.WASH && stack.getDamageValue() != ToolHelper.getWashMaxUses(stack);
	}

	public static int getWashMaxUses(ItemStack stack) {
		// TODO: port ItemWashCloth first
		return 0;
	}

	// TODO: performBlockTransformation — depends on CraftingManagerTransformation (not yet ported)

	public static Tool getToolTypeFromStack(ItemStack stack) {
		if (stack == null) {
			MineFantasyReforged.LOG.warn("Attempted to get the tool type of a null ItemStack");
			return Tool.HANDS;
		}

		if (stack.isEmpty()) {
			return Tool.HANDS;
		}

		if (stack.getItem() instanceof IToolMFR) {
			return ((IToolMFR) stack.getItem()).getToolType(stack);
		}

		if (CustomCrafterEntry.getEntry(stack) != null) {
			return CustomCrafterEntry.getEntryType(stack);
		}

		return Tool.OTHER;
	}

	public static boolean isItemMaterial(ItemStack stack, Tier material) {
		if (!stack.isEmpty()) {
			if (stack.getItem() instanceof IToolMaterial mat) {
				return mat.getMaterial() != null && mat.getMaterial() == material;
			}
		}
		return false;
	}

	public static ItemStack setQuality(ItemStack item, float qualityLvl) {
		if (item.getMaxStackSize() > 0)
			return item;

		CompoundTag nbt = minefantasy.mfr.util.NbtUtils.getOrCreateNBT(item);
		nbt.putFloat("MFCraftQuality", qualityLvl);
		minefantasy.mfr.util.NbtUtils.saveNBT(item, nbt);

		return item;
	}

	public static float getQualityLevel(ItemStack stack) {
		if (stack.getMaxStackSize() == 1) {
			CompoundTag nbt = minefantasy.mfr.util.NbtUtils.getOrCreateNBT(stack);
			if (nbt.contains("MFCraftQuality")) {
				return nbt.getFloat("MFCraftQuality");
			}
		}
		return 100.0F;
	}

	public static int setDuraOnQuality(ItemStack item, int dura) {
		float quality = getQualityLevel(item);
		CompoundTag nbt = minefantasy.mfr.util.NbtUtils.getOrCreateNBT(item);
		if (nbt.contains("MF_Inferior")) {
			if (nbt.getBoolean("MF_Inferior")) {
				dura /= 2;
			} else {
				dura *= 2;
			}
		}

		if (quality > 100) {
			dura += ((dura) / 100F * (quality - 100));
		}
		if (quality < 100) {
			dura -= ((dura * 0.75) / 100 * (100F - quality));
		}
		return dura;
	}

	public static float modifyDigOnQuality(ItemStack item, float digspeed) {
		CompoundTag nbt = minefantasy.mfr.util.NbtUtils.getOrCreateNBT(item);
		if (nbt.contains("MF_Inferior")) {
			if (nbt.getBoolean("MF_Inferior")) {
				digspeed /= 1.25F;
			} else {
				digspeed *= 1.25F;
			}
		}
		float quality = getQualityLevel(item);

		if (quality > 100) {
			digspeed += ((digspeed * 0.5F) / 100F * (quality - 100));
		}
		if (quality < 100) {
			digspeed -= ((digspeed * 0.5) / 100 * (100F - quality));
		}
		return digspeed;
	}

	public static float modifyDamOnQuality(ItemStack item, float damage) {
		float quality = getQualityLevel(item);
		CompoundTag nbt = minefantasy.mfr.util.NbtUtils.getOrCreateNBT(item);
		if (nbt.contains("MF_Inferior")) {
			if (nbt.getBoolean("MF_Inferior")) {
				damage /= 1.25F;
			} else {
				damage *= 1.25F;
			}
		}
		if (quality > 100) {
			damage += ((damage * 0.25F) / 100F * (quality - 100));
		}
		if (quality < 100) {
			damage -= ((damage * 0.25) / 100 * (100F - quality));
		}
		return damage;
	}

	public static float modifyArmourRating(ItemStack item, float rating) {
		float quality = getQualityLevel(item);
		CompoundTag nbt = minefantasy.mfr.util.NbtUtils.getOrCreateNBT(item);
		if (nbt.contains("MF_Inferior")) {
			if (nbt.getBoolean("MF_Inferior")) {
				rating /= 1.25F;
			} else {
				rating *= 1.25F;
			}
		}

		if (quality > 100) {
			rating += ((rating * 0.5F) / 100F * (quality - 100));
		}
		if (quality < 100) {
			rating -= ((rating * 0.5) / 100 * (100F - quality));
		}
		return rating;
	}

	public static boolean hasCustomQualityTag(ItemStack item) {
		return minefantasy.mfr.util.NbtUtils.getOrCreateNBT(item).contains("MFCraftQuality");
	}

	public static void setSpecial(ItemStack item, String type) {
		CompoundTag nbt = minefantasy.mfr.util.NbtUtils.getOrCreateNBT(item);
		nbt.putString(specialItem, type);
		minefantasy.mfr.util.NbtUtils.saveNBT(item, nbt);
	}

	public static boolean isSpecial(ItemStack item, String type) {
		return getSpecial(item) != null && getSpecial(item).equals(type);
	}

	public static String getSpecial(ItemStack item) {
		CompoundTag nbt = minefantasy.mfr.util.NbtUtils.getOrCreateNBT(item);
		if (nbt.contains(specialItem)) {
			return nbt.getString(specialItem);
		}
		return null;
	}

	public static void setToolSharpness(ItemStack item, float level) {
		CompoundTag nbt = minefantasy.mfr.util.NbtUtils.getOrCreateNBT(item);
		float currentLevel = getSharpnessLevel(item);
		float maxLevel = getMaxSharpness(item);
		nbt.putFloat(sharpnessLevelNBT, Math.min(maxLevel, currentLevel + level));
		minefantasy.mfr.util.NbtUtils.saveNBT(item, nbt);
	}

	public static float getSharpnessLevel(ItemStack item) {
		CompoundTag nbt = minefantasy.mfr.util.NbtUtils.getOrCreateNBT(item);
		if (nbt.contains(sharpnessLevelNBT)) {
			return nbt.getFloat(sharpnessLevelNBT);
		}
		return 0F;
	}

	public static boolean canBeSharpened(ItemStack itemstack, float level) {
		if (itemstack.isEmpty())
			return false;
		return false;
	}

	public static float getMaxSharpness(ItemStack item) {
		return getSharpnessTraits(item)[0];
	}

	public static float getMaxSharpnessPercent(ItemStack item) {
		return getSharpnessTraits(item)[1];
	}

	public static float getSharpUsesModifier(ItemStack item) {
		return getSharpnessTraits(item)[2];
	}

	public static float[] getSharpnessTraits(ItemStack item) {
		float[] list = new float[] {100, 20F, 3F};

		if (!item.isEmpty() && item.getItem() instanceof ISharpenable instance) {
			list[0] = instance.getMaxSharpness(item);
			list[1] = instance.getDamagePercentMax(item);
			list[2] = instance.getSharpUsesModifier(item);
		}
		return list;
	}

	public static String[] breakdownLineForResearchArray(String string) {
		String temp = "";
		ArrayList<String> entries = new ArrayList<>();

		for (int a = 0; a < string.length(); a++) {
			if (a == string.length() - 1) {
				temp = temp + string.charAt(a);
			}
			if (string.charAt(a) == " ".charAt(0) || a == string.length() - 1) {
				entries.add(temp);
			} else {
				if (string.charAt(a) != " ".charAt(0)) {
					temp = temp + string.charAt(a);
				}
			}
		}
		int size = entries.size();
		String[] stringList = new String[size];
		for (int i = 0; i < size; i++) {
			stringList[i] = entries.get(i);
		}
		return stringList;
	}

	public static boolean isToolSufficient(ItemStack heldItem, Tool toolNeeded, int toolTierNeeded) {
		Tool tool = getToolTypeFromStack(heldItem);
		int tier = getCrafterTier(heldItem);
		return tool == toolNeeded && tier >= toolTierNeeded;
	}

	public static void setUnbreakable(ItemStack tool, boolean isUnbreakable) {
		CompoundTag nbt = minefantasy.mfr.util.NbtUtils.getOrCreateNBT(tool);
		nbt.putBoolean(Constants.UNBREAKABLE_TAG, isUnbreakable);
		minefantasy.mfr.util.NbtUtils.saveNBT(tool, nbt);
	}
}
