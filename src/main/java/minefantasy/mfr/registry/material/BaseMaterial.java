package minefantasy.mfr.registry.material;

import minefantasy.mfr.constants.MFRRarity;
import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;

public class BaseMaterial {
	private static final float armourVsSwordBalance = 2.0F;
	private static final float SWORD_DAMAGE = 5F;
	public static HashMap<String, BaseMaterial> materialMap = new HashMap<>();

	private static float ACrounding = 10F;

	public String name;
	public int maxUses;
	public int harvestLevel;
	public float hardness;
	public float attackDamage;
	public int enchantability;
	public float weight;
	public int requiredLevel;
	public int tier;

	public int hammerTier;
	public int anvilTier;
	public float craftTimeModifier = 2.0F;

	public int workableTemp = 100;
	public int unstableTemp = 150;
	public float fireResistance;
	public float arcaneResistance;
	public boolean isMythic = false;
	public MFRRarity rarity;

	private ArmorMaterialMFR armourConversion;
	private Tier toolConversion;

	public BaseMaterial(String name, int tier, int durability, int harvestLevel, float hardness, float sharpness,
			int enchantability, float weight, int lvl, MFRRarity rarity) {
		this.requiredLevel = lvl;
		this.name = name;
		this.tier = tier;
		this.maxUses = durability;
		this.hardness = hardness;
		this.attackDamage = sharpness;
		this.enchantability = enchantability;
		this.weight = weight;
		this.harvestLevel = harvestLevel;
		this.rarity = rarity;
	}

	public static BaseMaterial addMaterial(String name, int tier, int durability, int harvestLevel, float sharpness,
			int enchantment, float weight, int lvl, MFRRarity rarity) {
		float armorClass = ((sharpness + SWORD_DAMAGE) / armourVsSwordBalance) - 1.0F;
		MFRLogUtil.logDebug("Added Ratio Armour Material " + name + " AR = " + armorClass);
		float initAc = armorClass;
		armorClass = Math.round(armorClass * (100F / ACrounding)) / (100F / ACrounding);
		if (initAc != armorClass) {
			MFRLogUtil.logDebug("Auto-Calculated ArmourRating for tier: " + name + ", modified to " + armorClass);
		}
		return addMaterial(name, tier, durability, harvestLevel, armorClass, sharpness, enchantment, weight, lvl, rarity);
	}

	public static BaseMaterial addMaterial(String name, int tier, int durability, int harvestLevel, float sharpness,
			int enchantment, float weight, int lvl) {
		float armorClass = ((sharpness + SWORD_DAMAGE) / armourVsSwordBalance) - 1.0F;
		MFRLogUtil.logDebug("Added Ratio Armour Material " + name + " AR = " + armorClass);
		float initAc = armorClass;
		armorClass = Math.round(armorClass * (100F / ACrounding)) / (100F / ACrounding);
		if (initAc != armorClass) {
			MFRLogUtil.logDebug("Auto-Calculated ArmourRating for tier: " + name + ", modified to " + armorClass);
		}
		return addMaterial(name, tier, durability, harvestLevel, armorClass, sharpness, enchantment, weight, lvl);
	}

	public static BaseMaterial addMaterial(String name, int tier, int durability, int harvestLevel, float hardness,
			float sharpness, int enchantment, float weight, int lvl, MFRRarity rarity) {
		return register(new BaseMaterial(name, tier, durability, harvestLevel, hardness, sharpness, enchantment, weight, lvl, rarity));
	}

	public static BaseMaterial addMaterial(String name, int tier, int durability, int harvestLevel, float hardness,
			float sharpness, int enchantment, float weight, int lvl) {
		return register(new BaseMaterial(name, tier, durability, harvestLevel, hardness, sharpness, enchantment, weight, lvl, MFRRarity.COMMON));
	}

	public static BaseMaterial register(BaseMaterial material) {
		materialMap.put(material.name.toLowerCase(), material);
		return material;
	}

	public static ArmorMaterialMFR getMFRArmourMaterial(BaseMaterial material) {
		return material.convertToMFArmour();
	}

	public BaseMaterial setForgeStats(int hammer, int anvil, float timer, int workable, int unstable) {
		hammerTier = hammer;
		anvilTier = anvil;
		craftTimeModifier = (timer * 2F) + 2.0F;
		workableTemp = workable;
		unstableTemp = unstable;
		return this;
	}

	public BaseMaterial setResistances(float fire, float arcane) {
		fireResistance = fire;
		arcaneResistance = arcane;
		return this;
	}

	private ArmorMaterialMFR convertToMFArmour() {
		return new ArmorMaterialMFR("MF" + name, maxUses, hardness, enchantability, weight)
				.setFireResistance(fireResistance).setMagicResistance(arcaneResistance).setMythic(isMythic);
	}

	public ArmorMaterialMFR getArmourConversion() {
		if (armourConversion == null) {
			armourConversion = getMFRArmourMaterial(this);
		}
		return armourConversion;
	}

	public static BaseMaterial getMaterial(String name) {
		return materialMap.get(name.toLowerCase());
	}

	private Tier registerAsToolMaterial() {
		TagKey<Block> incorrectBlocks = switch (harvestLevel) {
			case 0 -> BlockTags.INCORRECT_FOR_WOODEN_TOOL;
			case 1 -> BlockTags.INCORRECT_FOR_STONE_TOOL;
			case 2 -> BlockTags.INCORRECT_FOR_IRON_TOOL;
			case 3 -> BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
			default -> BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
		};
		final int uses = maxUses;
		final float speed = 2.0F + (attackDamage * 2F);
		final float damage = attackDamage;
		final int enchant = enchantability;
		final TagKey<Block> tag = incorrectBlocks;
		return new Tier() {
			@Override public int getUses() { return uses; }
			@Override public float getSpeed() { return speed; }
			@Override public float getAttackDamageBonus() { return damage; }
			@Override public TagKey<Block> getIncorrectBlocksForDrops() { return tag; }
			@Override public int getEnchantmentValue() { return enchant; }
			@Override public Ingredient getRepairIngredient() { return Ingredient.EMPTY; }
		};
	}

	public Tier getToolMaterial() {
		if (toolConversion == null) {
			toolConversion = this.registerAsToolMaterial();
		}
		return toolConversion;
	}
}
