package minefantasy.mfr.registry.material;

import minefantasy.mfr.constants.MFRRarity;
import minefantasy.mfr.registry.material.types.CustomMaterialType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class CustomMaterial {
	private static final int[] flameResistArray = new int[] {100, 300};

	private final String name;
	private final CustomMaterialType type;

	protected Ingredient materialIngredient;
	private final int[] colourRGB;
	private final Float hardness;
	private final Float durability;
	private final Float flexibility;
	private final Float sharpness;
	private final Float resistance;
	private final Float density;
	private final Integer tier;
	private final MFRRarity rarity;
	private final Integer enchantability;
	private final Integer crafterTier;
	private final Integer crafterAnvilTier;
	private final Float craftTimeModifier;
	private final Integer meltingPoint;
	private Float[] armourProtection;
	private final Boolean unbreakable;

	public CustomMaterial(String name, CustomMaterialType type, Ingredient materialIngredient, int[] colourRGB, Float hardness,
			Float durability, Float flexibility, Float sharpness, Float resistance, Float density, Integer tier, MFRRarity rarity,
			Integer enchantability, Integer crafterTier, Integer crafterAnvilTier, Float craftTimeModifier, Integer meltingPoint,
			Float[] armourProtection, Boolean unbreakable) {
		this.name = name;
		this.type = type;
		this.materialIngredient = materialIngredient;
		this.colourRGB = colourRGB;
		this.hardness = hardness;
		this.durability = durability;
		this.flexibility = flexibility;
		this.sharpness = sharpness;
		this.resistance = resistance;
		this.density = density;
		this.tier = tier;
		this.rarity = rarity;
		this.enchantability = enchantability;
		this.crafterTier = crafterTier;
		this.crafterAnvilTier = crafterAnvilTier;
		this.craftTimeModifier = craftTimeModifier;
		this.meltingPoint = meltingPoint;
		this.armourProtection = armourProtection;
		this.unbreakable = unbreakable;
	}

	public String getName() {
		return name.toLowerCase();
	}

	public CustomMaterialType getType() {
		return type;
	}

	public void setMaterialIngredient(Ingredient materialIngredient) {
		this.materialIngredient = materialIngredient;
	}

	public Ingredient getMaterialIngredient() {
		return materialIngredient;
	}

	public int[] getColourRGB() {
		return colourRGB;
	}

	public int getColourInt() {
		return (colourRGB[0] << 16) + (colourRGB[1] << 8) + colourRGB[2];
	}

	public Float getHardness() {
		return hardness;
	}

	public Float getDurability() {
		return durability;
	}

	public Float getFlexibility() {
		return flexibility;
	}

	public Float getSharpness() {
		return sharpness;
	}

	public Float getResistance() {
		return resistance;
	}

	public Float getDensity() {
		return density;
	}

	public Integer getTier() {
		return tier;
	}

	public MFRRarity getRarity() {
		return rarity;
	}

	public Integer getEnchantability() {
		return enchantability;
	}

	public Integer getCrafterTier() {
		return crafterTier;
	}

	public Integer getCrafterAnvilTier() {
		return crafterAnvilTier;
	}

	public Float getCraftTimeModifier() {
		return craftTimeModifier;
	}

	public Integer getMeltingPoint() {
		return meltingPoint;
	}

	public void setArmourStats(float cutting, float blunt, float piercing) {
		armourProtection = new Float[] {cutting, blunt, piercing};
	}

	public Float[] getArmourProtection() {
		return armourProtection;
	}

	public Boolean isUnbreakable() {
		return unbreakable;
	}

	@OnlyIn(Dist.CLIENT)
	public String getMaterialString() {
		return Component.translatable("materialtype." + this.type.getSerializedName() + ".name", this.crafterTier).getString();
	}

	public float getArmourProtection(int id) {
		return armourProtection[id];
	}

	public float getFireResistance() {
		if (meltingPoint > flameResistArray[0]) {
			float max = flameResistArray[1] - flameResistArray[0];
			float heat = meltingPoint - flameResistArray[0];
			int res = (int) (heat / max * 100F);
			return Math.min(100, res);
		}
		return 0F;
	}

	public int[] getHeatableStats() {
		int workableTemp = meltingPoint;
		int unstableTemp = (int) (workableTemp * 1.5F);
		int maxTemp = (int) (workableTemp * 2F);
		return new int[] {workableTemp, unstableTemp, maxTemp};
	}

	public boolean isHeatable() {
		return false;
	}
}
