package minefantasy.mfr.registry.material;

import minefantasy.mfr.constants.MFRRarity;
import minefantasy.mfr.registry.material.types.CustomMaterialType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class WoodMaterial extends CustomMaterial {

	public WoodMaterial(String name, Ingredient materialIngredient, int[] colourRGB, float hardness,
			float durability, float flexibility, float sharpness, float resistance, float density, int tier, MFRRarity rarity,
			int enchantability, int crafterTier, Float craftTimeModifier, boolean unbreakable) {

		super(name, CustomMaterialType.WOOD_MATERIAL, materialIngredient, colourRGB, hardness, durability, flexibility, sharpness, resistance, density,
				tier, rarity, enchantability, crafterTier, null, craftTimeModifier,
				null, null, unbreakable);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public String getMaterialString() {
		return Component.translatable("materialtype." + this.getType().getSerializedName() + ".name", this.getTier()).getString();
	}
}
