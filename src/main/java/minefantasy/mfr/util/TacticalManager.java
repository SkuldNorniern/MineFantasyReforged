package minefantasy.mfr.util;

import minefantasy.mfr.api.armour.IElementalResistance;
import minefantasy.mfr.api.weapon.IParryable;
import minefantasy.mfr.api.weapon.ISpecialCombatMob;
import minefantasy.mfr.config.ConfigArmour;
import minefantasy.mfr.config.ConfigStamina;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class TacticalManager {

	public static boolean shouldStaminaBlock = false;
	public static boolean newBalanceSystem = false;
	private static final Random rand = new Random();

	public static boolean canBlock(Entity attacker, LivingEntity defender) {
		return canBlock(attacker, defender, 180);
	}

	public static boolean canBlock(Entity attacker, LivingEntity defender, float blockAngle) {
		if (attacker == null || defender == null)
			return false;
		float yaw = calculateHitAngle(attacker, defender);
		return yaw < blockAngle && yaw > -blockAngle;
	}

	public static boolean isFlankedBy(Entity attacker, LivingEntity defender, float angle) {
		float yaw = calculateHitAngle(attacker, defender);
		float blockAngle = (360 - angle) / 2;
		return !(yaw < blockAngle && yaw > -blockAngle);
	}

	public static boolean canParry(DamageSource source, LivingEntity user, Entity entityHitting, ItemStack weapon) {
		boolean autoParry = false;
		if (shouldStaminaBlock && ConfigStamina.isSystemActive
				&& isStaminaAffected(user) && !hasAnyStamina(user)) {
			return false;
		}
		if (!user.getMainHandItem().isEmpty() && !canWeaponBlock(user.getMainHandItem())) {
			return false;
		}
		if (user instanceof Player player) {
			// TODO: ResearchLogic.hasInfoUnlocked(player, "auto_parry")
			autoParry = false && !player.isUsingItem();
			if (!player.isUsingItem() && !autoParry) {
				return false;
			}
		} else {
			if (!isMobBlocking(user) && didParrySucceed(user, source)) {
				return false;
			}
		}

		// TODO: CombatMechanics.isParryAvailable(user)
		if (!true) {
			return false;
		}

		int confusion = 0;
		var nauseaEffect = user.getEffect(MobEffects.NAUSEA);
		if (nauseaEffect != null) {
			confusion = nauseaEffect.getAmplifier() + 1;
		}
		float arc = 20;

		if (!weapon.isEmpty() && weapon.getItem() instanceof IParryable parry) {
			if (!parry.canUserParry(user)) {
				return false;
			}
			arc = parry.getParryAngle(source, user, weapon);
			if (!parry.canParry(source, user, weapon)) {
				return false;
			}
		}

		if (source.is(DamageTypeTags.BYPASSES_ARMOR)) {
			return false;
		}
		if (source.is(DamageTypeTags.IS_PROJECTILE)) {
			arc *= 0.75F;
		}

		arc *= getHighgroundModifier(user, entityHitting, 1.5F);
		arc = ArmourCalculator.adjustArmorClassForDamage(source, arc, 1.0F, 1.0F, 0.5F);
		if (autoParry) {
			arc *= 0.5F;
		}
		arc *= ArmourCalculator.getParryModifier(user);
		if (confusion > 0 && rand.nextInt(confusion + 1) != 0) {
			return false;
		}
		return arc > 0 && canBlock(entityHitting, user, arc);
	}

	private static boolean canWeaponBlock(ItemStack item) {
		return item.has(net.minecraft.core.component.DataComponents.WEAPON)
				|| item.getItem() instanceof IParryable;
	}

	// TODO: ItemWeaponMFR not yet ported — always returns false for now
	public static boolean checkAllowsOffhandOnBoth(Player player) {
		return false;
	}

	private static boolean isMobBlocking(LivingEntity user) {
		if (!user.fireImmune() && user.isOnFire()) {
			return false;
		}
		// TODO: custom mfr_block UseAnim no longer supported via enum extension;
		//       reimplement when ItemWeaponMFR is ported
		return false;
	}

	private static boolean didParrySucceed(LivingEntity user, DamageSource source) {
		if (user instanceof ISpecialCombatMob mob) {
			return mob.canParry(source);
		}
		return rand.nextInt(5) != 0;
	}

	public static float getHighgroundModifier(Entity target, Entity hitter, float value) {
		if (target == null || hitter == null) {
			return 1.0F;
		}
		float gap = 0.5F;
		if (target.getY() > hitter.getY() + gap)
			return 1.0F * value;
		if (target.getY() < hitter.getY() - gap)
			return 1.0F / value;
		return 1.0F;
	}

	public static void knockbackEntity(Entity target, Entity source, float power, float height) {
		target.push(
				-Mth.sin(source.getYRot() * (float) Math.PI / 180.0F) * power * 0.5F,
				height,
				Mth.cos(source.getYRot() * (float) Math.PI / 180.0F) * power * 0.5F);
	}

	public static void lungeEntity(Entity attacker, Entity target, float power, float height) {
		attacker.push(
				-Mth.sin(attacker.getYRot() * (float) Math.PI / 180.0F) * power * 0.5F,
				height,
				Mth.cos(attacker.getYRot() * (float) Math.PI / 180.0F) * power * 0.5F);
	}

	public static boolean isRanged(DamageSource source) {
		if (source == null) {
			return false;
		}
		if (source.is(DamageTypeTags.IS_PROJECTILE)) {
			return true;
		}
		Entity direct = source.getDirectEntity();
		Entity trueSource = source.getEntity();
		if (direct != null && trueSource != null) {
			return direct != trueSource;
		}
		return false;
	}

	private static float calculateHitAngle(Entity attacker, LivingEntity defender) {
		if (attacker == null) {
			return 0F;
		}
		double xGap = attacker.getX() - defender.getX();
		double zGap;
		for (zGap = attacker.getZ() - defender.getZ();
				xGap * xGap + zGap * zGap < 1.0E-4D;
				zGap = (Math.random() - Math.random()) * 0.01D) {
			xGap = (Math.random() - Math.random()) * 0.01D;
		}
		float yaw = (float) (Math.atan2(zGap, xGap) * 180.0D / Math.PI) - defender.getYRot();
		yaw = yaw - 90;
		while (yaw < -180) yaw += 360;
		while (yaw >= 180) yaw -= 360;
		return yaw;
	}

	public static void applyArmourWeight(Player player) {
		if (player == null || player.getAbilities().instabuild) {
			return;
		}
		float totalSpeed = 100F;

		if (ConfigArmour.shouldSlow && !isImmuneToWeight(player)) {
			totalSpeed += ArmourCalculator.getSpeedModForWeight(player);
			if (totalSpeed <= ConfigArmour.minWeightSpeed) {
				totalSpeed = ConfigArmour.minWeightSpeed;
			}
			float weight = ArmourCalculator.getTotalWeightOfWorn(player, false);
			if (weight > 100F && player.isInWater()) {
				Vec3 m = player.getDeltaMovement();
				player.setDeltaMovement(m.x, m.y - (weight / 20000F), m.z);
			}
		}
		if (totalSpeed != 100F && player.onGround()) {
			Vec3 m = player.getDeltaMovement();
			player.setDeltaMovement(m.x * (totalSpeed / 100F), m.y, m.z * (totalSpeed / 100F));
		}
	}

	// In old MC armorType index was HEAD=0,CHEST=1,LEGS=2,FEET=3.
	// In modern MC EquipmentSlot.getIndex() for armor is FEET=0,LEGS=1,CHEST=2,HEAD=3 (reversed).
	// resistMagic/Fire used sizes[3 - oldIndex] → modern: sizes[newIndex]
	public static float resistMagic(LivingEntity user, DamageSource source) {
		float resistance = 100F;
		for (EquipmentSlot slot : EquipmentSlot.VALUES) {
			if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR) continue;
			ItemStack stack = user.getItemBySlot(slot);
			if (!stack.isEmpty() && stack.getItem() instanceof IElementalResistance res) {
				float modifier = res.getMagicResistance(stack, source);
				modifier *= ArmourCalculator.sizes[slot.getIndex()];
				resistance -= modifier;
			}
		}
		return resistance / 100F;
	}

	public static float resistFire(LivingEntity user, DamageSource source) {
		float resistance = 100F;
		for (EquipmentSlot slot : EquipmentSlot.VALUES) {
			if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR) continue;
			ItemStack stack = user.getItemBySlot(slot);
			if (!stack.isEmpty() && stack.getItem() instanceof IElementalResistance res) {
				float modifier = res.getFireResistance(stack, source);
				modifier *= ArmourCalculator.sizes[slot.getIndex()];
				resistance -= modifier;
			}
		}
		return resistance / 100F;
	}

	// resistArrow/Base used sizes[oldIndex] → modern: sizes[3 - newIndex]
	public static boolean resistArrow(LivingEntity user, DamageSource source, float dam) {
		Entity hitter = source.getEntity();
		if (!isArrow(hitter)) {
			return false;
		}
		float threshold = 0.25F;
		float resistance = 1.0F;
		for (EquipmentSlot slot : EquipmentSlot.VALUES) {
			if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR) continue;
			ItemStack stack = user.getItemBySlot(slot);
			if (!stack.isEmpty() && stack.getItem() instanceof IElementalResistance res) {
				float modifier = res.getArrowDeflection(stack, source);
				modifier *= ArmourCalculator.sizes[3 - slot.getIndex()];
				resistance += modifier;
			}
		}
		threshold *= resistance;
		if (!user.level().isClientSide()) {
			MFRLogUtil.logDebug("Arrow Damage: " + dam + " Projectile Threshold: " + threshold);
		}
		return dam <= threshold && dam > 0;
	}

	public static boolean isArrow(Entity hitter) {
		// TODO: also check EntityArrowMFR when ported
		return hitter instanceof AbstractArrow;
	}

	public static float resistBase(LivingEntity user, DamageSource source) {
		float resistance = 100F;
		for (EquipmentSlot slot : EquipmentSlot.VALUES) {
			if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR) continue;
			ItemStack stack = user.getItemBySlot(slot);
			if (!stack.isEmpty() && stack.getItem() instanceof IElementalResistance res) {
				float modifier = res.getBaseResistance(stack, source);
				modifier *= ArmourCalculator.sizes[3 - slot.getIndex()];
				resistance -= modifier;
			}
		}
		return resistance / 100F;
	}

	public static float getResistance(LivingEntity user, DamageSource source) {
		if (source.is(DamageTypeTags.IS_FIRE)) {
			return resistFire(user, source);
		}
		if (source.is(DamageTypeTags.BYPASSES_ARMOR)) {
			return resistMagic(user, source);
		}
		return resistBase(user, source);
	}

	public static boolean shouldNotAttack(Entity attacker, LivingEntity target) {
		// TODO aggro
		return false;
	}

	// TODO: PlayerData not yet ported — balance pitch/yaw offsets are no-ops for now
	public static void throwPlayerOffBalance(Player player, float balance) {
	}

	public static boolean isEntityMoving(LivingEntity entity) {
		Vec3 motion = entity.getDeltaMovement();
		return Math.abs(motion.x) > 0.005 || Math.abs(motion.z) > 0.005;
	}

	public static boolean isMelee(DamageSource source) {
		Entity direct = source.getDirectEntity();
		Entity trueSource = source.getEntity();
		if (direct != null && trueSource != null) {
			return direct == trueSource && !source.is(DamageTypeTags.IS_PROJECTILE);
		}
		return false;
	}

	public static boolean isUnholyCreature(Entity entityHit) {
		if (!(entityHit instanceof LivingEntity living)) {
			return false;
		}
		if (living.getType().builtInRegistryHolder().is(EntityTypeTags.UNDEAD)) {
			return true;
		}
		if (entityHit instanceof Witch) {
			return true;
		}
		String className = entityHit.getClass().getName();
		return className.contains("Wraith") || className.contains("Werewolf");
	}

	public static boolean isDragon(Entity entityHit) {
		if (!(entityHit instanceof LivingEntity)) {
			return false;
		}
		if (entityHit instanceof EnderDragon) {
			return true;
		}
		// TODO: also check minefantasy.mfr.entity.mob.EntityDragon when ported
		return entityHit.getClass().getName().contains("EntityDragon")
				&& entityHit.getClass().getName().startsWith("minefantasy");
	}

	public static boolean tryDisarm(LivingEntity target) {
		return tryDisarm(null, target, false);
	}

	public static boolean tryDisarm(LivingEntity attacker, LivingEntity target, boolean steal) {
		if (target.getMainHandItem().isEmpty()) {
			return false;
		}
		if (attacker != null && steal && attacker.getMainHandItem().isEmpty()) {
			attacker.setItemSlot(EquipmentSlot.MAINHAND, target.getMainHandItem().copy());
		} else {
			ItemStack targetMainhand = target.getMainHandItem();
			// TODO: EntityMinotaur not yet ported — durability-drain-on-disarm is skipped
			if (target.level() instanceof ServerLevel sl) {
				sl.addFreshEntity(new ItemEntity(sl, target.getX(), target.getY() + 0.5, target.getZ(), targetMainhand));
			}
		}
		target.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
		return true;
	}

	public static boolean isImmuneToWeight(LivingEntity entityLiving) {
		return PowerArmour.isPowered(entityLiving);
	}

	public static void leap(Entity target, float angle, float power, float height) {
		target.push(
				-Mth.sin(angle * (float) Math.PI / 180.0F) * power,
				height,
				Mth.cos(angle * (float) Math.PI / 180.0F) * power);
	}

	// Stubs for unported stamina system
	private static boolean isStaminaAffected(LivingEntity entity) {
		return false; // TODO: StaminaBar.doesAffectEntity(entity)
	}

	private static boolean hasAnyStamina(LivingEntity entity) {
		return true; // TODO: StaminaBar.isAnyStamina(entity, false)
	}
}
