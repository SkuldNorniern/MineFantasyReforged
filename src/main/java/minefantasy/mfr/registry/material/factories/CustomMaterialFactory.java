package minefantasy.mfr.registry.material.factories;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.constants.MFRRarity;
import minefantasy.mfr.init.ModItems;
import minefantasy.mfr.registry.material.CustomMaterial;
import minefantasy.mfr.registry.material.CustomMaterialRegistry;
import minefantasy.mfr.registry.material.MetalMaterial;
import minefantasy.mfr.registry.material.WoodMaterial;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Ported from legacy {@code registry/material/factories/CustomMaterialFactory.java}. Reads the
 * material-definition JSON that already exists in the mod's own resources at
 * {@code assets/minefantasyreforged/registry/materials_mfr/minefantasyreforged/*.json} (byte-
 * identical carryover from the 1.12.2 branch — see the port plan).
 * <p>
 * Simplifications vs. legacy:
 * <ul>
 *   <li>Only the {@code minefantasyreforged} namespace file is read. The other 32 per-mod files
 *       under {@code materials_mfr/} (astralsorcery, thaumcraft, ...) are cross-mod OreDictionary
 *       compat for mods that mostly don't exist on this MC version — deferred indefinitely.</li>
 *   <li>Legacy's {@code materialIngredient} field was resolved lazily via a deferred
 *       {@code JsonContext}/OreDictionary lookup (needed because other mods' items might not be
 *       registered yet at parse time). OreDictionary is gone; this resolves eagerly against a
 *       small fixed lookup table of this mod's own already-registered items instead — see
 *       {@link #resolveIngredient(JsonElement)}.</li>
 *   <li>Plain GSON + {@link GsonHelper} parsing (matching {@code JsonUtils}' modern replacement)
 *       rather than a full Mojang {@code Codec} — the schema's polymorphic
 *       {@code materialIngredient} field (OreDict-ref vs. item[+legacy metadata]) doesn't map
 *       cleanly onto a declarative record codec. Read via the classloader (like a bundled config
 *       file) rather than through the datapack {@code ResourceManager}/reload-listener system,
 *       since this doesn't need runtime datapack override support.</li>
 * </ul>
 */
public class CustomMaterialFactory {

	private static final String BASE_PATH = "assets/minefantasyreforged/registry/materials_mfr/minefantasyreforged/";

	private static final Map<String, Supplier<Item>> INGREDIENT_LOOKUP = buildIngredientLookup();

	public static void load() {
		loadMetals();
		loadWoods();
	}

	private static void loadMetals() {
		JsonObject root = readJson("metal_types.json");
		if (root == null || !root.has("metal_material")) {
			return;
		}
		for (JsonElement entry : root.getAsJsonArray("metal_material")) {
			try {
				CustomMaterialRegistry.addMaterial(parseMetal(entry.getAsJsonObject()));
			} catch (Exception e) {
				MineFantasyReforged.LOG.error("Failed to parse metal material entry {}", entry, e);
			}
		}
	}

	private static void loadWoods() {
		JsonObject root = readJson("wood_types.json");
		if (root == null || !root.has("wood_material")) {
			return;
		}
		for (JsonElement entry : root.getAsJsonArray("wood_material")) {
			try {
				CustomMaterialRegistry.addMaterial(parseWood(entry.getAsJsonObject()));
			} catch (Exception e) {
				MineFantasyReforged.LOG.error("Failed to parse wood material entry {}", entry, e);
			}
		}
	}

	private static CustomMaterial parseMetal(JsonObject json) {
		String name = GsonHelper.getAsString(json, "name");
		Ingredient ingredient = resolveIngredient(json.get("materialIngredient"));

		JsonObject properties = GsonHelper.getAsJsonObject(json, "properties");
		float durability = GsonHelper.getAsFloat(properties, "durability");
		float flexibility = GsonHelper.getAsFloat(properties, "flexibility");
		float sharpness = GsonHelper.getAsFloat(properties, "sharpness");
		float hardness = GsonHelper.getAsFloat(properties, "hardness");
		float resistance = GsonHelper.getAsFloat(properties, "resistance");
		float density = GsonHelper.getAsFloat(properties, "density");
		int tier = GsonHelper.getAsInt(properties, "tier");
		int meltingPoint = GsonHelper.getAsInt(properties, "melting_point");
		MFRRarity rarity = MFRRarity.valueOf(GsonHelper.getAsString(properties, "rarity"));
		int enchantability = GsonHelper.getAsInt(properties, "enchantability");
		int craftTier = GsonHelper.getAsInt(properties, "craft_tier");
		float craftTimeModifier = GsonHelper.getAsFloat(properties, "craft_time_modifier");
		boolean unbreakable = GsonHelper.getAsBoolean(properties, "unbreakable");

		JsonObject armourStats = GsonHelper.getAsJsonObject(json, "armour_stats");
		Float[] armour = {
				GsonHelper.getAsFloat(armourStats, "cutting"),
				GsonHelper.getAsFloat(armourStats, "blunt"),
				GsonHelper.getAsFloat(armourStats, "piercing")};

		JsonObject color = GsonHelper.getAsJsonObject(json, "color");
		int[] colors = {
				GsonHelper.getAsInt(color, "red"),
				GsonHelper.getAsInt(color, "green"),
				GsonHelper.getAsInt(color, "blue")};

		return new MetalMaterial(name, ingredient, colors, hardness, durability, flexibility, sharpness,
				resistance, density, tier, rarity, enchantability, craftTier, craftTimeModifier, meltingPoint,
				armour, unbreakable);
	}

	private static CustomMaterial parseWood(JsonObject json) {
		String name = GsonHelper.getAsString(json, "name");
		Ingredient ingredient = resolveIngredient(json.get("materialIngredient"));

		JsonObject properties = GsonHelper.getAsJsonObject(json, "properties");
		float durability = GsonHelper.getAsFloat(properties, "durability");
		float flexibility = GsonHelper.getAsFloat(properties, "flexibility");
		float hardness = GsonHelper.getAsFloat(properties, "hardness");
		float resistance = GsonHelper.getAsFloat(properties, "resistance");
		float density = GsonHelper.getAsFloat(properties, "density");
		int tier = GsonHelper.getAsInt(properties, "tier");
		MFRRarity rarity = MFRRarity.valueOf(GsonHelper.getAsString(properties, "rarity"));
		int craftTier = GsonHelper.getAsInt(properties, "craft_tier");
		float craftTimeModifier = GsonHelper.getAsInt(properties, "craft_time_modifier");

		JsonObject color = GsonHelper.getAsJsonObject(json, "color");
		int[] colors = {
				GsonHelper.getAsInt(color, "red"),
				GsonHelper.getAsInt(color, "green"),
				GsonHelper.getAsInt(color, "blue")};

		return new WoodMaterial(name, ingredient, colors, hardness, durability, flexibility, 0F, resistance,
				density, tier, rarity, 0, craftTier, craftTimeModifier * 4F, false);
	}

	/**
	 * Resolves the legacy {@code materialIngredient} field, which is either
	 * {@code {"type": "forge:ore_dict", "ore": "ingotCopper"}} or
	 * {@code {"item": "minecraft:planks", "data": 0}} (1.12 metadata variant, wood only).
	 */
	private static Ingredient resolveIngredient(@org.jspecify.annotations.Nullable JsonElement element) {
		if (element == null || !element.isJsonObject()) {
			return Ingredient.of(Items.STICK);
		}
		JsonObject obj = element.getAsJsonObject();
		if (obj.has("ore")) {
			String ore = GsonHelper.getAsString(obj, "ore");
			Supplier<Item> mapped = INGREDIENT_LOOKUP.get(ore);
			if (mapped != null) {
				return Ingredient.of(mapped.get());
			}
			MineFantasyReforged.LOG.warn("No item mapping for legacy ore_dict entry '{}', falling back to stick", ore);
			return Ingredient.of(Items.STICK);
		}
		if (obj.has("item")) {
			String item = GsonHelper.getAsString(obj, "item");
			int data = GsonHelper.getAsInt(obj, "data", -1);
			if ("minecraft:planks".equals(item) && data >= 0) {
				return Ingredient.of(legacyPlanksVariant(data));
			}
			Item resolved = net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
					net.minecraft.resources.Identifier.tryParse(item));
			if (resolved != null && resolved != Items.AIR) {
				return Ingredient.of(resolved);
			}
			MineFantasyReforged.LOG.warn("Unknown item id '{}' in material ingredient, falling back to stick", item);
		}
		return Ingredient.of(Items.STICK);
	}

	private static Item legacyPlanksVariant(int data) {
		return switch (data) {
			case 0 -> Items.OAK_PLANKS;
			case 1 -> Items.SPRUCE_PLANKS;
			case 2 -> Items.BIRCH_PLANKS;
			case 3 -> Items.JUNGLE_PLANKS;
			case 4 -> Items.ACACIA_PLANKS;
			case 5 -> Items.DARK_OAK_PLANKS;
			default -> Items.OAK_PLANKS;
		};
	}

	private static Map<String, Supplier<Item>> buildIngredientLookup() {
		Map<String, Supplier<Item>> map = new HashMap<>();
		map.put("ingotTin", () -> ModItems.TIN_INGOT.get());
		map.put("ingotCopper", () -> ModItems.COPPER_INGOT.get());
		map.put("ingotBronze", () -> ModItems.BRONZE_INGOT.get());
		map.put("ingotIron", () -> Items.IRON_INGOT);
		map.put("ingotPigIron", () -> ModItems.PIG_IRON_INGOT.get());
		map.put("ingotSteel", () -> ModItems.STEEL_INGOT.get());
		map.put("ingotDiamond", () -> Items.DIAMOND);
		map.put("ingotObsidian", () -> ModItems.OBSIDIAN_INGOT.get());
		map.put("ingotTungsten", () -> ModItems.TUNGSTEN_INGOT.get());
		map.put("ingotBlackSteel", () -> ModItems.BLACK_STEEL_INGOT.get());
		map.put("ingotBlueSteel", () -> ModItems.BLUE_STEEL_INGOT.get());
		map.put("ingotRedSteel", () -> ModItems.RED_STEEL_INGOT.get());
		map.put("ingotSilver", () -> ModItems.SILVER_INGOT.get());
		map.put("ingotGold", () -> Items.GOLD_INGOT);
		map.put("ingotMithril", () -> ModItems.MITHRIL_INGOT.get());
		map.put("ingotAdamantium", () -> ModItems.ADAMANTIUM_INGOT.get());
		map.put("ingotMithium", () -> ModItems.MITHIUM_INGOT.get());
		map.put("ingotIgnotumite", () -> ModItems.IGNOTUMITE_INGOT.get());
		map.put("ingotEnder", () -> ModItems.ENDER_INGOT.get());
		map.put("ingotCompositeAlloy", () -> ModItems.COMPOSITE_ALLOY_INGOT.get());
		map.put("stickWood", () -> Items.STICK);
		return map;
	}

	private static JsonObject readJson(String fileName) {
		try (InputStream in = CustomMaterialFactory.class.getClassLoader().getResourceAsStream(BASE_PATH + fileName)) {
			if (in == null) {
				MineFantasyReforged.LOG.warn("Material definition file not found on classpath: {}{}", BASE_PATH, fileName);
				return null;
			}
			try (Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
				return JsonParser.parseReader(reader).getAsJsonObject();
			}
		} catch (Exception e) {
			MineFantasyReforged.LOG.error("Failed to read material definition file {}{}", BASE_PATH, fileName, e);
			return null;
		}
	}
}
