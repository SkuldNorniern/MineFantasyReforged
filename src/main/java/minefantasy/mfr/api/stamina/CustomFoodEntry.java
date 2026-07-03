package minefantasy.mfr.api.stamina;

import minefantasy.mfr.config.ConfigStamina;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public class CustomFoodEntry {
	public static HashMap<String, CustomFoodEntry> entries = new HashMap<>();

	public float staminaRestore;
	public int staminaSeconds;
	public float staminaBuff;
	public int staminaRegenSeconds;
	public float staminaRegenBuff;
	public float eatDelay;
	public float fatAccumulation;
	public boolean hasEffect;
	public boolean staminaInMinutes;
	public boolean staminaInHours;
	public boolean staminaRegenInMinutes;

	private CustomFoodEntry(int tier, float sugar, float carbs, float fats) {
		sugar = sugar * tier;
		carbs = carbs * tier;
		fats = fats * tier;

		this.staminaRestore = sugar * 50;

		this.staminaSeconds = (int) (carbs * 3600F);
		this.staminaBuff = 50 * carbs;

		this.staminaRegenSeconds = (int) ((sugar / 30) * 60);
		this.staminaRegenBuff = sugar;

		this.eatDelay = fats * ConfigStamina.eatDelayModifier;
		this.fatAccumulation = fats * ConfigStamina.fatAccumulationModifier;

		staminaInMinutes = staminaSeconds > 60;
		staminaInHours = staminaSeconds > 3600;
		staminaRegenInMinutes = staminaRegenSeconds > 60;

		hasEffect = staminaRestore > 0 || staminaBuff > 0 || staminaRegenSeconds > 0 || staminaRegenBuff > 0 || eatDelay > 0 || fatAccumulation > 0;
	}

	public static void registerItem(ItemStack piece, int tier, float sugar, float carbs, float fats) {
		ResourceLocation key = BuiltInRegistries.ITEM.getKey(piece.getItem());
		if (key != null) {
			entries.put(key.toString(), new CustomFoodEntry(tier, sugar, carbs, fats));
		}
	}

	public static CustomFoodEntry getEntry(ItemStack item) {
		if (item != null) {
			ResourceLocation key = BuiltInRegistries.ITEM.getKey(item.getItem());
			if (key != null) {
				return entries.get(key.toString());
			}
		}
		return null;
	}
}
