package minefantasy.mfr.util;

import minefantasy.mfr.api.armour.ArmourDesign;
import minefantasy.mfr.api.armour.CustomArmourEntry;
import minefantasy.mfr.api.armour.CustomDamageRatioEntry;
import minefantasy.mfr.api.armour.IArmourMFR;
import minefantasy.mfr.api.armour.IArmourPenetrationMob;
import minefantasy.mfr.api.armour.IArmouredEntity;
import minefantasy.mfr.api.armour.ISpecialArmourMFR;
import minefantasy.mfr.api.weapon.IDamageType;
import minefantasy.mfr.config.ConfigArmour;
import minefantasy.mfr.data.IStoredVariable;
import minefantasy.mfr.data.Persistence;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public class ArmourCalculator {
	public static final float[] sizes = new float[]{0.2F, 0.3F, 0.3F, 0.2F};
	public static final float armourRatingScale = 100;
	public static final float[] encumberanceArray = new float[]{10F, 40F};
	public static final float slowAmount = 10F;
	public static final float moveSpeedThresholdMin = 20F;
	public static final float getMoveSpeedThresholdMax = 40F;
	public static final IStoredVariable<Float> WORN_WEIGHT_KEY = IStoredVariable.StoredVariable.ofFloat("wornWeight", Persistence.DIMENSION_CHANGE);
	public static final IStoredVariable<Float> WORN_WEIGHT_NS_KEY = IStoredVariable.StoredVariable.ofFloat("wornWeightNS", Persistence.DIMENSION_CHANGE);

	static {
		// TODO: wire to PlayerData.registerStoredVariables when PlayerData is ported
	}

	private static List<ItemStack> getArmorStacks(LivingEntity entity) {
		List<ItemStack> list = new ArrayList<>();
		for (EquipmentSlot slot : EquipmentSlot.VALUES) {
			if (slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
				list.add(entity.getItemBySlot(slot));
			}
		}
		return list;
	}

	private static float getDefaultSuitWeight(ItemStack armour) {
		return CustomArmourEntry.getEntryVars(armour)[0];
	}

	private static float getDefaultBulk(ItemStack armour) {
		return CustomArmourEntry.getEntryVars(armour)[1];
	}

	public static ArmourDesign getDefaultAD(ItemStack armour) {
		return ArmourDesign.SOLID;
	}

	public static float convertToPercent(float ratio) {
		return (1F - (1F / ratio));
	}

	private static float estimateScale(ItemStack item, EquipmentSlot slot) {
		if (item.isEmpty()) {
			return 0;
		}
		// ItemArmor removed in MC 26 — vanilla armor material distribution no longer accessible this way.
		// Fall back to default slot size ratios.
		return sizes[slot.getIndex()];
	}

	public static float getSpeedModForWeight(Player user) {
		float mod = 0.0F;
		float min = moveSpeedThresholdMin;
		float max = getMoveSpeedThresholdMax - min;
		float mass = getTotalWeightOfWorn(user, true) - min;
		if (mass > 0 && max > 0) {
			mod -= (mass / max) * slowAmount * ConfigArmour.slowRate;
		}
		return mod;
	}

	public static float getTotalWeightOfWorn(Player player, boolean considerSpeed) {
		// TODO: hook into PlayerData when ported
		return setTotalWeightOfWorn(player, considerSpeed);
	}

	public static void updateWeights(Player player) {
		setTotalWeightOfWorn(player, true);
		setTotalWeightOfWorn(player, false);
	}

	public static float setTotalWeightOfWorn(Player player, boolean considerSpeed) {
		float weight = 0.0F;
		for (ItemStack stack : getArmorStacks(player)) {
			EquipmentSlot slot = player.getEquipmentSlotForItem(stack);
			if (!considerSpeed || shouldArmourAlterSpeed(stack)) {
				weight += getPieceWeight(stack, slot);
			}
		}
		// TODO: save to PlayerData when ported
		return weight;
	}

	public static float getPieceWeight(ItemStack item, EquipmentSlot slot) {
		if (item.isEmpty()) {
			return 0.0F;
		}
		if (item.getItem() instanceof IArmourMFR) {
			return ((IArmourMFR) item.getItem()).getPieceWeight(item, slot);
		}
		return getDefaultSuitWeight(item);
	}

	private static boolean shouldArmourAlterSpeed(ItemStack armour) {
		if (armour.isEmpty()) {
			return false;
		}
		return armour.getItem() instanceof IArmourMFR || CustomArmourEntry.doesPieceSlowDown(armour);
	}

	public static float convertKgToIbs(float kg) {
		return kg * 2.5F;
	}

	public static float convertIbsToKg(float pounds) {
		return pounds / 2.5F;
	}

	public static float adjustArmorClassForDamage(DamageSource src, float value, float cuttingProtection, float bluntProtection, float pierceProtection) {
		float[] ratio = getRatioForSource(src);
		if (ratio == null) {
			return value;
		}
		return modifyArmorClassForType(value, ratio[0], ratio[1], ratio[2], cuttingProtection, bluntProtection, pierceProtection, getArmourPenetration(src));
	}

	public static float modifyArmorClassForType(int type, float value, float cuttingProt, float bluntProt, float pierceProt) {
		float[] f = new float[]{0F, 0F, 0F};
		f[type] = 1.0F;
		return modifyArmorClassForType(value, f[0], f[1], f[2], cuttingProt, bluntProt, pierceProt, 0F);
	}

	public static float modifyArmorClassForType(float value, float cutting, float blunt, float pierce, float cuttingProt, float bluntProt, float pierceProt, float specialAP) {
		if (ConfigArmour.advancedDamageTypes) {
			value *= (((cutting * cuttingProt) + (blunt * bluntProt) + (pierce * pierceProt))
					/ (cutting + blunt + pierce));
		}
		float ACModifier = 1.0F + specialAP;
		value *= ACModifier;
		return Math.max(value, 0F);
	}

	public static float getArmourPenetration(DamageSource source) {
		if (source != null && source.getEntity() != null) {
			Entity user = source.getEntity();
			Entity damager = source.getDirectEntity();

			if (user == damager && user instanceof LivingEntity living && !living.getMainHandItem().isEmpty()) {
				if (living.getMainHandItem().getItem() instanceof IDamageType dt) {
					return dt.getPenetrationLevel(living.getMainHandItem());
				}
			}
			if (user != damager) {
				if (damager instanceof IDamageType dt) {
					return dt.getPenetrationLevel(damager);
				}
			}
		}
		return 0F;
	}

	public static float[] getRatioForSource(DamageSource source) {
		if (source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypes.STARVE)) {
			return null;
		}
		// BYPASSES_ARMOR covers magic, starve, and other non-physical damage
		if (source.is(DamageTypeTags.BYPASSES_ARMOR) && !source.is(DamageTypes.STARVE)) {
			return null;
		}

		if (source.is(DamageTypeTags.IS_EXPLOSION) || source.is(DamageTypes.FALLING_ANVIL) || source.is(DamageTypes.FALLING_BLOCK)) {
			return new float[]{0, 1, 0};
		}
		if (source.is(DamageTypes.CACTUS)) {
			return new float[]{0, 0, 1};
		}
		if (source.getDirectEntity() != null && source.getEntity() != null) {
			Entity user = source.getEntity();
			Entity damager = source.getDirectEntity();

			if (user == damager && user instanceof LivingEntity living) {
				return getRatioForMelee(living, living.getMainHandItem());
			}
			return getRatioForIndirect(damager);
		}

		return new float[]{0, 1, 0};
	}

	private static float[] getRatioForIndirect(Entity damager) {
		if (damager == null) {
			return new float[]{1, 1, 1};
		}
		if (damager instanceof IDamageType dt) {
			return dt.getDamageRatio(damager);
		}
		if (damager instanceof AbstractArrow) {
			return new float[]{0, 0, 1};
		}
		if (ConfigArmour.useConfigIndirectDmg) {
			return CustomDamageRatioEntry.getEntityTraits(
					BuiltInRegistries.ENTITY_TYPE.getKey(damager.getType()));
		}
		return new float[]{1, 1, 1};
	}

	public static float[] getRatioForMelee(LivingEntity user, ItemStack weapon) {
		if (weapon.isEmpty()) {
			return getMobDefault(user);
		}
		return getRatioForWeapon(user, weapon);
	}

	public static float[] getRatioForWeapon(ItemStack weapon) {
		return getRatioForWeapon(null, weapon);
	}

	public static float[] getRatioForWeapon(LivingEntity user, ItemStack weapon) {
		if (weapon.getItem() instanceof IDamageType dt) {
			return user != null ? dt.getDamageRatio(weapon, user) : dt.getDamageRatio(weapon);
		}
		if (CustomDamageRatioEntry.getTraits(weapon) != null) {
			return CustomDamageRatioEntry.getTraits(weapon);
		}
		// SwordItem/AxeItem/PickaxeItem/ShovelItem/HoeItem: check via DataComponents.WEAPON/TOOL
		// TODO: refine weapon-type detection when DataComponents approach is finalized
		String itemClass = weapon.getItem().getClass().getSimpleName().toLowerCase();
		if (itemClass.contains("sword")) return new float[]{1, 0, 0};
		if (itemClass.contains("axe")) return new float[]{3, 1, 0};
		if (itemClass.contains("pickaxe")) return new float[]{0, 0, 1};
		if (itemClass.contains("hoe") || itemClass.contains("shovel") || itemClass.contains("spade")) return new float[]{0, 1, 0};

		return CustomDamageRatioEntry.getTraits(weapon);
	}

	private static float[] getMobDefault(LivingEntity user) {
		if (user.getType().builtInRegistryHolder().is(EntityTypeTags.ARTHROPOD)) {
			return new float[]{20F, 10F, 80F};
		}
		if (user instanceof IArmourPenetrationMob apm) {
			return apm.getHitTraits();
		}
		return new float[]{0, 1, 0};
	}

	public static String getEntityRegisterName(Entity entity) {
		if (entity == null) {
			return "generic";
		}
		var key = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
		return key != null ? key.toString() : "generic";
	}

	public static float getTotalBulk(LivingEntity user) {
		if (PowerArmour.isWearingCogwork(user)) {
			return 5F;
		}
		return getEquipmentBulk(user);
	}

	public static float getEquipmentBulk(LivingEntity user) {
		float bulk = 0.0F;
		for (ItemStack stack : getArmorStacks(user)) {
			String s = getArmourClass(stack);
			if (s != null) {
				if (s.equalsIgnoreCase("Medium")) bulk += 1;
				if (s.equalsIgnoreCase("Heavy")) bulk += 2;
			}
		}
		return bulk / 4;
	}

	public static String getArmourClass(ItemStack armour) {
		if (armour.isEmpty()) {
			return null;
		}
		if (armour.getItem() instanceof IArmourMFR) {
			return ((IArmourMFR) armour.getItem()).getSuitWeightType(armour);
		}
		return CustomArmourEntry.getArmourClass(armour);
	}

	@OnlyIn(Dist.CLIENT)
	public static float getDTDisplay(LivingEntity user, int id) {
		float armourDT = 0;
		for (ItemStack stack : getArmorStacks(user)) {
			if (!stack.isEmpty() && stack.getItem() instanceof ISpecialArmourMFR sa) {
				armourDT += getArmourValueMod(stack, sa.getDamageTypeDisplay(stack, id));
			}
		}
		return armourDT;
	}

	@OnlyIn(Dist.CLIENT)
	public static float getDTForDisplayPiece(ItemStack armour, int id) {
		if (!armour.isEmpty() && armour.getItem() instanceof ISpecialArmourMFR sa) {
			return getArmourValueMod(armour, sa.getDamageTypeDisplay(armour, id));
		}
		return 0F;
	}

	@OnlyIn(Dist.CLIENT)
	public static float getDamageReductionForDisplayPiece(ItemStack armour, int id) {
		if (armour.isEmpty()) return 0F;
		if (armour.getItem() instanceof ISpecialArmourMFR sa) {
			return getArmourValueMod(armour, sa.getDamageRatingDisplay(armour, id));
		}
		return 0F;
	}

	@OnlyIn(Dist.CLIENT)
	public static float getDRDisplay(LivingEntity user, int id) {
		float armourDT = 0;
		for (ItemStack stack : getArmorStacks(user)) {
			if (!stack.isEmpty() && stack.getItem() instanceof ISpecialArmourMFR sa) {
				armourDT += getArmourValueMod(stack, sa.getDamageRatingDisplay(stack, id));
			}
		}
		return armourDT;
	}

	public static float getACThreshold(LivingEntity user, DamageSource src) {
		float naturalAC = getACForMob(user);

		if (user instanceof IArmouredEntity ae) {
			naturalAC = ae.getThreshold(src);
		}

		float armourDT = 0;
		for (ItemStack stack : getArmorStacks(user)) {
			if (!stack.isEmpty() && stack.getItem() instanceof ISpecialArmourMFR sa) {
				armourDT += getArmourValueMod(stack, sa.getDamageTypeValue(user, stack, src));
			}
		}
		return naturalAC + armourDT;
	}

	private static float modifyDTOnDura(ItemStack armour) {
		float percentQuality = getPercentQuality(armour);
		float reduction = 0.8F;
		if (percentQuality < reduction) {
			return Math.max(0.1F, percentQuality / reduction);
		}
		return 1.0F;
	}

	private static float getPercentQuality(ItemStack armour) {
		return armour.getMaxDamage() > 0 ? 1F - ((float) armour.getDamageValue() / (float) armour.getMaxDamage()) : 1F;
	}

	private static float getACForMob(LivingEntity user) {
		if (user.getType().builtInRegistryHolder().is(EntityTypeTags.ARTHROPOD)) {
			return user.getMaxHealth() / 10F;
		}
		return 0F;
	}

	public static float getArmourValueMod(ItemStack armour, float DT) {
		CustomData customData = armour.get(DataComponents.CUSTOM_DATA);
		if (customData != null) {
			CompoundTag tag = customData.copyTag();
			if (tag.contains("MF_Inferior")) {
				DT *= (tag.getBoolean("MF_Inferior").orElse(false) ? 0.8F : 1.2F);
			}
		}
		DT *= modifyDTOnDura(armour);
		return DT;
	}

	public static int getDamageToDura(LivingEntity user, DamageSource source, ItemStack armour, float dam) {
		if (source.is(DamageTypeTags.BYPASSES_ARMOR)) {
			return 0;
		}
		if (source.getDirectEntity() != null && source.getDirectEntity() == source.getEntity()) {
			dam *= getMobArmourDamage(source.getDirectEntity());
		}
		if (getPercentQuality(armour) > 0.1F) {
			dam = Math.max(1.0F, dam / 4F);
		}
		return (int) dam;
	}

	private static float getMobArmourDamage(Entity src) {
		if (src != null && src.getType().builtInRegistryHolder().is(EntityTypeTags.ARTHROPOD)) {
			return 3.0F;
		}
		return 0.5F;
	}

	public static void damageArmour(LivingEntity target, int damageAmount) {
		if (!target.level().isClientSide()) {
			for (EquipmentSlot slot : EquipmentSlot.VALUES) {
				if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR) continue;
				ItemStack stack = target.getItemBySlot(slot);
				if (!stack.isEmpty()) {
					stack.hurtAndBreak(damageAmount, target, slot);
					if (stack.isEmpty()) {
						target.level().playSound(null, target.blockPosition(), SoundEvents.SHIELD_BREAK.value(), SoundSource.BLOCKS, 1.0F, 1.0F);
					}
				}
			}
		}
	}

	public static float getParryModifier(LivingEntity user) {
		float bulk = getTotalBulk(user);
		return 1.0F / ((bulk * 0.5F) + 1);
	}

	public static int modifyParryCooldown(LivingEntity user, int ticks) {
		float bulk = getTotalBulk(user);
		return (int) Math.max(5, ticks + (bulk * 5));
	}
}
