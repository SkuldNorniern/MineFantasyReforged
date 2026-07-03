package minefantasy.mfr.registry.material;

public class ArmorMaterialMFR {

	public final String name;
	public final int durability;
	public final float baseArmorRating;
	public final int enchantability;
	public final float armourWeight;
	public boolean isMythic = false;

	public float magicResistanceModifier = 0F;
	public float fireResistanceModifier = 0F;

	public ArmorMaterialMFR(String title, int durability, float armorClass, int enchantability, float armourWeight) {
		name = title;
		this.durability = durability;
		baseArmorRating = armorClass;
		this.enchantability = enchantability;
		this.armourWeight = armourWeight;
	}

	public ArmorMaterialMFR setMagicResistance(float magic) {
		magicResistanceModifier = magic;
		return this;
	}

	public ArmorMaterialMFR setFireResistance(float fire) {
		fireResistanceModifier = fire;
		return this;
	}

	public ArmorMaterialMFR setMythic(boolean flag) {
		isMythic = flag;
		return this;
	}
}
