package minefantasy.mfr.registry.material;

import minefantasy.mfr.constants.MFRRarity;
import minefantasy.mfr.registry.material.types.CustomMaterialType;
import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.world.item.crafting.Ingredient;

public class MetalMaterial extends CustomMaterial {

	public MetalMaterial(String name, Ingredient materialIngredient, int[] colourRGB, float hardness,
			float durability, float flexibility, float sharpness, float resistance, float density, int tier, MFRRarity rarity,
			int enchantability, int crafterTier, float craftTimeModifier, Integer meltingPoint,
			Float[] armourProtection, boolean unbreakable) {

		super(name, CustomMaterialType.METAL_MATERIAL, materialIngredient, colourRGB, hardness, durability, flexibility, sharpness,
				resistance, density, tier, rarity, enchantability, crafterTier, Math.min(crafterTier, 4),
				craftTimeModifier, meltingPoint, armourProtection, unbreakable);

		setArmourStats(1.0F, flexibility, 1F / flexibility);

		for (float value : armourProtection) {
			if (value != 1.0) {
				setArmourStats(armourProtection[0], armourProtection[1], armourProtection[2]);
				break;
			}
		}
	}

	public static void addHeatables() {
		for (CustomMaterial customMat : CustomMaterialRegistry.getList(CustomMaterialType.METAL_MATERIAL)) {
			int[] stats = customMat.getHeatableStats();
			MFRLogUtil.logDebug("Set Heatable Stats for " + customMat.getName() + ": " + stats[0] + "," + stats[1] + "," + stats[2]);
			// TODO: setHeatableStats via Heatable registry once ported
		}
	}

	@Override
	public boolean isHeatable() {
		return true;
	}
}
