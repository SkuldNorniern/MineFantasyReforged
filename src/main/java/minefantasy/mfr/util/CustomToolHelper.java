package minefantasy.mfr.util;

import minefantasy.mfr.api.crafting.IMaterialComponent;
import minefantasy.mfr.api.crafting.IMaterialDoubleComponent;
import minefantasy.mfr.api.crafting.IMaterialSingleComponent;
import minefantasy.mfr.api.crafting.exotic.ISpecialDesign;
import minefantasy.mfr.registry.material.CustomMaterial;
import minefantasy.mfr.registry.material.CustomMaterialRegistry;
import minefantasy.mfr.constants.MFRRarity;
import minefantasy.mfr.registry.material.types.CustomMaterialType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomToolHelper {
	public static final String slot_main = "main_material";
	public static final String slot_haft = "haft_material";

	public static CustomMaterial getCustomPrimaryMaterial(ItemStack item) {
		if (item.isEmpty())
			return CustomMaterialRegistry.NONE;

		return CustomMaterialRegistry.getMaterialFor(item, slot_main);
	}

	public static CustomMaterial getCustomSecondaryMaterial(ItemStack item) {
		if (item.isEmpty()) {
			return CustomMaterialRegistry.NONE;
		}

		return CustomMaterialRegistry.getMaterialFor(item, slot_haft);
	}

	public static ItemStack constructWithDefaultWood(Item base, String main) {
		return construct(base, main, "oak_wood");
	}

	public static ItemStack construct(Item base, String main, String haft) {
		ItemStack stack = new ItemStack(base);
		CustomMaterialRegistry.addMaterial(stack, slot_main, main.toLowerCase());
		if (haft != null) {
			CustomMaterialRegistry.addMaterial(stack, slot_haft, haft.toLowerCase());
		}
		return stack;
	}

	public static ItemStack construct(Item base, CustomMaterial main, CustomMaterial haft) {
		ItemStack stack = new ItemStack(base);
		CustomMaterialRegistry.addMaterial(stack, slot_main, main.getName().toLowerCase());
		if (haft != CustomMaterialRegistry.NONE) {
			CustomMaterialRegistry.addMaterial(stack, slot_haft, haft.getName().toLowerCase());
		}
		return stack;
	}

	public static ItemStack construct(ItemStack stack, CustomMaterial main, CustomMaterial haft) {
		CustomMaterialRegistry.addMaterial(stack, slot_main, main.getName().toLowerCase());
		if (haft != CustomMaterialRegistry.NONE) {
			CustomMaterialRegistry.addMaterial(stack, slot_haft, haft.getName().toLowerCase());
		}
		return stack;
	}

	public static ItemStack constructMainSlot(Item base, String main) {
		ItemStack item = new ItemStack(base);
		CustomMaterialRegistry.addMaterial(item, slot_main, main.toLowerCase());
		return item;
	}

	public static ItemStack constructMainSlot(Item base, CustomMaterial main) {
		ItemStack item = new ItemStack(base);
		CustomMaterialRegistry.addMaterial(item, slot_main, main);
		return item;
	}

	public static ItemStack constructMainSlot(ItemStack inputStack, String main) {
		ItemStack stack = inputStack.copy();
		CustomMaterialRegistry.addMaterial(stack, slot_main, main.toLowerCase());
		return stack;
	}

	public static ItemStack constructMainSlot(ItemStack inputStack, CustomMaterial main) {
		ItemStack stack = inputStack.copy();
		CustomMaterialRegistry.addMaterial(stack, slot_main, main);
		return stack;
	}

	public static ItemStack constructSecondarySlot(Item base, String secondary) {
		ItemStack item = new ItemStack(base);
		CustomMaterialRegistry.addMaterial(item, slot_haft, secondary.toLowerCase());
		return item;
	}

	public static ItemStack constructSecondarySlot(Item base, CustomMaterial secondary) {
		ItemStack item = new ItemStack(base);
		CustomMaterialRegistry.addMaterial(item, slot_haft, secondary);
		return item;
	}

	public static List<ItemStack> constructAllVariants(Item item) {
		List<ItemStack> allVariants = new ArrayList<>();

		if (item instanceof IMaterialComponent) {
			if (item instanceof IMaterialSingleComponent) {
				CustomMaterialType type = ((IMaterialSingleComponent) item).getMaterialType();
				if (type == CustomMaterialType.METAL_MATERIAL) {
					for (CustomMaterial m : CustomMaterialRegistry.getList(CustomMaterialType.METAL_MATERIAL)) {
						allVariants.add(constructMainSlot(item, m));
					}
				} else {
					for (CustomMaterial m : CustomMaterialRegistry.getList(CustomMaterialType.WOOD_MATERIAL)) {
						allVariants.add(constructMainSlot(item, m));
					}
				}
			} else {
				CustomMaterialType primaryType = ((IMaterialDoubleComponent) item).getPrimaryMaterialType();
				CustomMaterialType secondaryType = ((IMaterialDoubleComponent) item).getSecondaryMaterialType();
				for (CustomMaterial secondary : CustomMaterialRegistry.getList(secondaryType)) {
					for (CustomMaterial primary : CustomMaterialRegistry.getList(primaryType)) {
						allVariants.add(construct(item, primary, secondary));
					}
				}
			}
		}

		return allVariants;
	}

	public static List<ItemStack> constructAllVariants(ItemStack inputStack) {
		ItemStack stack = inputStack.copy();
		Item item = stack.getItem();
		List<ItemStack> allVariants = new ArrayList<>();

		if (item instanceof IMaterialComponent) {
			if (item instanceof IMaterialSingleComponent
					&& ((IMaterialSingleComponent) item).getMaterialType() != CustomMaterialType.NONE) {

				CustomMaterialType type = ((IMaterialSingleComponent) item).getMaterialType();
				if (type == CustomMaterialType.METAL_MATERIAL) {
					for (CustomMaterial m : CustomMaterialRegistry.getList(CustomMaterialType.METAL_MATERIAL)) {
						allVariants.add(constructMainSlot(stack, m));
					}
				} else {
					for (CustomMaterial m : CustomMaterialRegistry.getList(CustomMaterialType.WOOD_MATERIAL)) {
						allVariants.add(constructMainSlot(stack, m));
					}
				}
			} else if (item instanceof IMaterialDoubleComponent
					&& ((IMaterialDoubleComponent) item).getPrimaryMaterialType() != CustomMaterialType.NONE
					&& ((IMaterialDoubleComponent) item).getSecondaryMaterialType() != CustomMaterialType.NONE) {

				CustomMaterialType primaryType = ((IMaterialDoubleComponent) item).getPrimaryMaterialType();
				CustomMaterialType secondaryType = ((IMaterialDoubleComponent) item).getSecondaryMaterialType();

				for (CustomMaterial secondary : CustomMaterialRegistry.getList(secondaryType)) {
					for (CustomMaterial primary : CustomMaterialRegistry.getList(primaryType)) {
						allVariants.add(construct(item, primary, secondary));
					}
				}
			} else {
				allVariants.add(inputStack);
			}
		}

		return allVariants;
	}

	public static List<ItemStack> constructModifiedCounts(ItemStack stack, CustomMaterialType fallbackType) {
		List<ItemStack> stacks = new ArrayList<>();
		CustomMaterialType type;
		if (stack.getItem() instanceof IMaterialComponent) {
			if (stack.getItem() instanceof IMaterialDoubleComponent) {
				type = ((IMaterialDoubleComponent) stack.getItem()).getPrimaryMaterialType();
			} else {
				type = ((IMaterialSingleComponent) stack.getItem()).getMaterialType();
			}
		} else {
			type = fallbackType;
		}

		for (CustomMaterial material : CustomMaterialRegistry.getList(type)) {
			ItemStack copy = stack.copy();
			int modifiedCount = Mth.clamp(
					material.getTier() * copy.getCount(),
					1,
					copy.getMaxStackSize());
			copy.setCount(modifiedCount);
			stacks.add(copy);
		}
		return stacks;
	}

	public static void modifyCounts(List<ItemStack> stacks) {
		for (ItemStack stackToModify : stacks) {
			CustomMaterial material = CustomToolHelper.getCustomPrimaryMaterial(stackToModify);
			int modifiedCount = Mth.clamp(
					material.getTier() * stackToModify.getCount(),
					1,
					stackToModify.getMaxStackSize());
			stackToModify.setCount(modifiedCount);
		}
	}

	public static MFRRarity getRarity(ItemStack item, MFRRarity itemRarity) {
		int lvl = itemRarity.value;
		CustomMaterial material = CustomMaterialRegistry.getMaterialFor(item, slot_main);
		if (material != null) {
			lvl = material.getRarity().value;
		}

		if (item.isEnchanted()) {
			if (lvl == 0) {
				lvl++;
			}
			lvl++;
		}
		if (lvl >= MFRRarity.values().length) {
			lvl = MFRRarity.values().length - 1;
		}
		return MFRRarity.fromValue(lvl);
	}

	public static int getMaxDamage(ItemStack stack, int dura) {
		CustomMaterial head = getCustomPrimaryMaterial(stack);
		CustomMaterial haft = getCustomSecondaryMaterial(stack);
		if (head != null && head != CustomMaterialRegistry.NONE) {
			dura = (int) (head.getDurability() * 100);
		}
		if (haft != null && haft != CustomMaterialRegistry.NONE) {
			dura += (int) (haft.getDurability() * 100);
		}
		return ToolHelper.setDuraOnQuality(stack, dura);
	}

	public static int getColourFromItemStack(ItemStack item, int layer) {
		if (layer == 0) {
			CustomMaterial material = CustomMaterialRegistry.getMaterialFor(item, slot_main);
			if (material != null && material != CustomMaterialRegistry.NONE) {
				return material.getColourInt();
			}
		}
		if (layer == 1) {
			CustomMaterial material = CustomMaterialRegistry.getMaterialFor(item, slot_haft);
			if (material != null && material != CustomMaterialRegistry.NONE) {
				return material.getColourInt();
			}
		}
		return CustomMaterialRegistry.NONE.getColourInt();
	}

	public static float getWeightModifier(ItemStack item, float base) {
		CustomMaterial metal = getCustomPrimaryMaterial(item);
		CustomMaterial wood = getCustomSecondaryMaterial(item);

		if (metal != CustomMaterialRegistry.NONE) {
			base = (metal.getDensity() / 2.5F) * base;
		}
		if (wood != CustomMaterialRegistry.NONE) {
			base += (wood.getDensity() / 2.5F);
		}
		return base;
	}

	public static float getMeleeDamage(ItemStack item, float defaultModifier) {
		CustomMaterial custom = getCustomPrimaryMaterial(item);
		if (custom != null && custom != CustomMaterialRegistry.NONE) {
			return custom.getSharpness();
		}
		return defaultModifier;
	}

	public static float getBowDamage(ItemStack item, float defaultModifier) {
		CustomMaterial base = getCustomSecondaryMaterial(item);
		CustomMaterial joints = getCustomPrimaryMaterial(item);

		if (base != null && base != CustomMaterialRegistry.NONE) {
			defaultModifier = base.getFlexibility();
		}
		if (joints != null && joints != CustomMaterialRegistry.NONE) {
			defaultModifier *= joints.getFlexibility();
		}
		return defaultModifier;
	}

	public static float getBaseDamages(ItemStack item, float defaultModifier) {
		CustomMaterial custom = getCustomPrimaryMaterial(item);
		if (custom != null && custom != CustomMaterialRegistry.NONE) {
			return getBaseDamage(custom.getSharpness() * custom.getFlexibility());
		}
		return getBaseDamage(defaultModifier);
	}

	public static float getBaseDamage(float modifier) {
		return 4F + modifier;
	}

	public static float getEfficiencyForHds(ItemStack item, float value, float mod) {
		CustomMaterial custom = getCustomPrimaryMaterial(item);
		if (custom != null && custom != CustomMaterialRegistry.NONE) {
			value = 2.0F + (custom.getHardness() * 4F);
		}
		return ToolHelper.modifyDigOnQuality(item, value) * mod;
	}

	public static float getEfficiency(ItemStack item, float value, float mod) {
		CustomMaterial custom = getCustomPrimaryMaterial(item);
		if (custom != null && custom != CustomMaterialRegistry.NONE) {
			value = 2.0F + (custom.getSharpness() * 2F);
		}
		return ToolHelper.modifyDigOnQuality(item, value) * mod;
	}

	public static int getCrafterTier(ItemStack item, int value) {
		CustomMaterial custom = getCustomPrimaryMaterial(item);
		if (custom != null && custom != CustomMaterialRegistry.NONE) {
			return custom.getCrafterTier();
		}
		return value;
	}

	public static int getHarvestLevel(ItemStack item, int value) {
		if (value <= 0) {
			return value;
		}

		CustomMaterial custom = getCustomPrimaryMaterial(item);
		if (custom != null && custom != CustomMaterialRegistry.NONE) {
			if (custom.getTier() == 0)
				return 1;
			if (custom.getTier() <= 2)
				return 2;
			return Math.max(custom.getTier(), 2);
		}
		return value;
	}

	@OnlyIn(Dist.CLIENT)
	public static void addInformation(ItemStack item, List<String> list) {
		CustomMaterial secondaryMaterial = getCustomSecondaryMaterial(item);

		if (materialOnTooltip()) {
			CustomMaterial mainMaterial = getCustomPrimaryMaterial(item);
			if (mainMaterial != null && mainMaterial != CustomMaterialRegistry.NONE) {
				String matName = net.minecraft.client.resources.language.I18n.get(
						Utils.convertSnakeCaseToSplitCapitalized(mainMaterial.getName()));
				list.add(ChatFormatting.GOLD + matName);
			}
		}

		if (secondaryMaterial != null && secondaryMaterial != CustomMaterialRegistry.NONE) {
			String name = secondaryMaterial.getName();
			String localized = net.minecraft.client.resources.language.I18n.get("material." + name + ".name");
			if (!localized.endsWith(".name")) {
				name = localized;
			}
			String matName = net.minecraft.client.resources.language.I18n.get(
					"item.mod_haft.name", Utils.convertSnakeCaseToSplitCapitalized(name));
			list.add(ChatFormatting.GOLD + matName);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static boolean materialOnTooltip() {
		String cfg = net.minecraft.client.resources.language.I18n.get("languagecfg.tooltiptier");
		return cfg.equalsIgnoreCase("true");
	}

	@OnlyIn(Dist.CLIENT)
	public static String getMaterialNameForTooltip(CustomMaterial customMaterial) {
		return net.minecraft.client.resources.language.I18n.get(
				Utils.convertSnakeCaseToSplitCapitalized(customMaterial.getName()));
	}

	@OnlyIn(Dist.CLIENT)
	public static void addBowInformation(ItemStack item, List<String> list) {
		CustomMaterial material = getCustomPrimaryMaterial(item);
		if (material != null && material != CustomMaterialRegistry.NONE) {
			String name = material.getName();
			String localized = net.minecraft.client.resources.language.I18n.get("material." + name + ".name");
			if (!localized.endsWith(".name")) {
				name = localized;
			}
			String matName = net.minecraft.client.resources.language.I18n.get(
					"item.mod_joint.name",
					net.minecraft.client.resources.language.I18n.get(Utils.convertSnakeCaseToSplitCapitalized(name)));
			list.add(ChatFormatting.GOLD + matName);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static String getSecondaryLocalisedName(ItemStack item, String unlocalizedName) {
		if (materialOnTooltip()) {
			return net.minecraft.client.resources.language.I18n.get(unlocalizedName);
		}

		CustomMaterial material = getCustomSecondaryMaterial(item);
		String name = "any";
		String localizedMaterial = null;
		if (material != null && material != CustomMaterialRegistry.NONE) {
			name = material.getName();
			localizedMaterial = net.minecraft.client.resources.language.I18n.get("material." + name + ".name");
		}
		if (localizedMaterial != null && !localizedMaterial.endsWith(".name")) {
			name = localizedMaterial;
		}
		return net.minecraft.client.resources.language.I18n.get(
				unlocalizedName,
				net.minecraft.client.resources.language.I18n.get(Utils.convertSnakeCaseToSplitCapitalized(name)));
	}

	@OnlyIn(Dist.CLIENT)
	public static String getLocalisedName(ItemStack item, String unlocalizedName) {
		if (materialOnTooltip()) {
			return net.minecraft.client.resources.language.I18n.get(unlocalizedName);
		}

		CustomMaterial material = getCustomPrimaryMaterial(item);
		String name = "any";
		String localizedMaterial = null;
		if (material != null && material != CustomMaterialRegistry.NONE) {
			name = material.getName();
			localizedMaterial = net.minecraft.client.resources.language.I18n.get("material." + name + ".name");
		}
		if (localizedMaterial != null && !localizedMaterial.endsWith(".name")) {
			name = localizedMaterial;
		}
		return net.minecraft.client.resources.language.I18n.get(
				unlocalizedName,
				net.minecraft.client.resources.language.I18n.get(Utils.convertSnakeCaseToSplitCapitalized(name)));
	}

	public static boolean areEqual(ItemStack recipeItem, ItemStack inputItem) {
		if (recipeItem.isEmpty()) {
			return inputItem.isEmpty();
		}
		if (inputItem.isEmpty())
			return false;

		return ItemStack.isSameItem(recipeItem, inputItem) && doesMainMatchForRecipe(recipeItem, inputItem)
				&& doesHaftMatchForRecipe(recipeItem, inputItem);
	}

	public static boolean doesMatchForRecipe(ItemStack recipeItem, ItemStack inputItem) {
		return doesMainMatchForRecipe(recipeItem, inputItem) && doesHaftMatchForRecipe(recipeItem, inputItem);
	}

	public static boolean doesMatchForRecipe(Ingredient ingredient, ItemStack inputItem) {
		return ingredient.items()
				.map(holder -> new ItemStack(holder))
				.anyMatch(itemStack -> doesMainMatchForRecipe(itemStack, inputItem) && doesHaftMatchForRecipe(itemStack, inputItem));
	}

	public static boolean doesMainMatchForRecipe(ItemStack recipeItem, ItemStack inputItem) {
		CustomMaterial recipeMat = CustomToolHelper.getCustomPrimaryMaterial(recipeItem);
		CustomMaterial inputMat = CustomToolHelper.getCustomPrimaryMaterial(inputItem);

		if (recipeMat == null || recipeMat == CustomMaterialRegistry.NONE) {
			return true;
		}

		if ((inputMat == null || inputMat == CustomMaterialRegistry.NONE) && recipeMat != null) {
			return false;
		}
		return recipeMat == inputMat;
	}

	public static boolean doesHaftMatchForRecipe(ItemStack recipeItem, ItemStack inputItem) {
		CustomMaterial recipeMat = CustomToolHelper.getCustomSecondaryMaterial(recipeItem);
		CustomMaterial inputMat = CustomToolHelper.getCustomSecondaryMaterial(inputItem);

		if (recipeMat == null || recipeMat == CustomMaterialRegistry.NONE) {
			return true;
		}

		if ((inputMat == null || inputMat == CustomMaterialRegistry.NONE) && recipeMat != null) {
			return false;
		}
		return recipeMat == inputMat;
	}

	@OnlyIn(Dist.CLIENT)
	public static void addComponentString(List<String> list, CustomMaterial base) {
		addComponentString(list, base, 1);
	}

	@OnlyIn(Dist.CLIENT)
	public static void addComponentString(List<String> list, CustomMaterial base, float units) {
		if (base != null) {
			float mass = base.getDensity() * units;
			if (base != CustomMaterialRegistry.NONE) {
				list.add(ChatFormatting.GOLD + base.getMaterialString());
			}
			if (mass > 0) {
				list.add(CustomMaterialRegistry.getWeightString(mass));
			}

			if (base.isHeatable()) {
				int maxTemp = base.getHeatableStats()[0];
				int beyondMax = base.getHeatableStats()[1];
				list.add(net.minecraft.client.resources.language.I18n.get("materialtype.workable.name", maxTemp, beyondMax));
			}
		}
	}

	public static float getBurnModifier(ItemStack fuel) {
		CustomMaterial mat = CustomMaterialRegistry.getMaterialFor(fuel, slot_main);
		if (mat != null && mat != CustomMaterialRegistry.NONE && mat.getType() == CustomMaterialType.WOOD_MATERIAL) {
			return (2 * mat.getDensity()) + 0.5F;
		}
		return 1.0F;
	}

	public static String getReferenceName(ItemStack item) {
		// In modern MC, items don't use damage metadata for variants — always use "any"
		return getReferenceName(item, "any");
	}

	public static String getReferenceName(ItemStack item, String dam) {
		return getReferenceName(item, dam, true);
	}

	public static String getReferenceName(ItemStack item, String dam, boolean tiered) {
		String reference = getSimpleReferenceName(item.getItem(), dam);

		if (tiered) {
			CustomMaterial base = getCustomPrimaryMaterial(item);
			CustomMaterial haft = getCustomSecondaryMaterial(item);

			if (base != null && base != CustomMaterialRegistry.NONE) {
				reference += "_" + base.getName();
			}
			if (haft != null && haft != CustomMaterialRegistry.NONE) {
				reference += "_" + haft.getName();
			}
		}

		return reference;
	}

	public static String getSimpleReferenceName(Item item, String dam) {
		Identifier key = BuiltInRegistries.ITEM.getKey(item);
		if (key == null) {
			return "";
		}
		return key.toString().toLowerCase() + "_@" + dam;
	}

	public static String getSimpleReferenceName(Item item) {
		return getSimpleReferenceName(item, "any");
	}

	public static boolean areToolsSame(ItemStack item1, ItemStack item2) {
		CustomMaterial mainMaterial1 = getCustomPrimaryMaterial(item1);
		CustomMaterial secondaryMaterial1 = getCustomSecondaryMaterial(item2);
		CustomMaterial mainMaterial2 = getCustomPrimaryMaterial(item1);
		CustomMaterial secondaryMaterial2 = getCustomSecondaryMaterial(item2);
		if (((mainMaterial1 == null || mainMaterial1 == CustomMaterialRegistry.NONE) && secondaryMaterial1 != null && secondaryMaterial1 != CustomMaterialRegistry.NONE) || ((secondaryMaterial1 == null || secondaryMaterial1 == CustomMaterialRegistry.NONE) && mainMaterial1 != null && mainMaterial1 != CustomMaterialRegistry.NONE))
			return false;
		if (((mainMaterial2 == null || mainMaterial2 == CustomMaterialRegistry.NONE) && secondaryMaterial2 != null && secondaryMaterial2 != CustomMaterialRegistry.NONE) || ((secondaryMaterial2 == null || secondaryMaterial2 == CustomMaterialRegistry.NONE) && mainMaterial2 != null && mainMaterial2 != CustomMaterialRegistry.NONE))
			return false;

		if (mainMaterial1 != null && secondaryMaterial1 != null && mainMaterial1 != secondaryMaterial1)
			return false;
		return mainMaterial2 == null || secondaryMaterial2 == null || mainMaterial2 == secondaryMaterial2;
	}

	public static boolean isMythic(ItemStack result) {
		CustomMaterial main1 = getCustomPrimaryMaterial(result);
		CustomMaterial haft1 = getCustomPrimaryMaterial(result);
		if (main1 != null && main1 != CustomMaterialRegistry.NONE && main1.isUnbreakable()) {
			return true;
		}
		return haft1 != null && haft1 != CustomMaterialRegistry.NONE && haft1.isUnbreakable();
	}

	// TODO: getComponentMaterial depends on ItemHeated (not yet ported)
	public static String getComponentMaterial(ItemStack item, CustomMaterialType type) {
		if (item.isEmpty() || type == null)
			return null;

		CustomMaterial material = CustomToolHelper.getCustomPrimaryMaterial(item);
		if (material != null && material != CustomMaterialRegistry.NONE) {
			return material.getType() == type ? material.getName() : null;
		}
		return null;
	}

	public static boolean hasAnyMaterial(ItemStack item) {
		return (getCustomPrimaryMaterial(item) != null && getCustomPrimaryMaterial(item) != CustomMaterialRegistry.NONE)
				|| (getCustomSecondaryMaterial(item) != null && getCustomSecondaryMaterial(item) != CustomMaterialRegistry.NONE);
	}

	public static void tryDeconstruct(ItemStack outputItem, ItemStack inputItem) {
		CustomMaterialType type = null;
		if (!outputItem.isEmpty() && outputItem.getItem() instanceof IMaterialSingleComponent singleComp) {
			type = singleComp.getMaterialType();
		}

		if (type != null) {
			CustomMaterial primary = CustomToolHelper.getCustomPrimaryMaterial(inputItem);
			CustomMaterial secondary = CustomToolHelper.getCustomSecondaryMaterial(inputItem);

			if (primary != null && primary != CustomMaterialRegistry.NONE && primary.getType() == type) {
				CustomMaterialRegistry.addMaterial(outputItem, slot_main, primary.getName());
			} else {
				if (secondary != null && secondary != CustomMaterialRegistry.NONE && secondary.getType() == type) {
					CustomMaterialRegistry.addMaterial(outputItem, slot_main, secondary.getName());
				}
			}
		}
	}

	public static String getCustomStyle(ItemStack weapon) {
		if (!weapon.isEmpty() && weapon.getItem() instanceof ISpecialDesign specialDesign) {
			return specialDesign.getDesign(weapon);
		}
		return null;
	}
}
