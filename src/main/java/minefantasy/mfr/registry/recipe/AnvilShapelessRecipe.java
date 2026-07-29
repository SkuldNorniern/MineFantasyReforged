package minefantasy.mfr.registry.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.constants.Tool;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Shapeless anvil recipe: ingredients can appear in any slot of the 6x4 grid, order-independent. */
public class AnvilShapelessRecipe extends AnvilRecipe {

	public static final MapCodec<AnvilShapelessRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
					Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
					Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(o -> o.ingredients),
					ItemStackTemplate.CODEC.fieldOf("result").forGetter(o -> o.result),
					Tool.CODEC.optionalFieldOf("tool_type", Tool.HAMMER).forGetter(AnvilRecipe::getToolType),
					Codec.INT.optionalFieldOf("craft_time", 0).forGetter(AnvilRecipe::getCraftTime),
					Codec.INT.optionalFieldOf("tool_tier", 0).forGetter(AnvilRecipe::getToolTier),
					Codec.INT.optionalFieldOf("anvil_tier", 0).forGetter(AnvilRecipe::getAnvilTier),
					Codec.BOOL.optionalFieldOf("output_hot", false).forGetter(AnvilRecipe::isHotOutput),
					Codec.STRING.optionalFieldOf("research", "none").forGetter(AnvilRecipe::getRequiredResearch),
					Skill.CODEC.optionalFieldOf("skill", Skill.NONE).forGetter(o -> o.skill),
					Codec.INT.optionalFieldOf("skill_xp", 0).forGetter(AnvilRecipe::getSkillXp),
					Codec.FLOAT.optionalFieldOf("vanilla_xp", 0.0F).forGetter(AnvilRecipe::getVanillaXp))
			.apply(i, AnvilShapelessRecipe::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, AnvilShapelessRecipe> STREAM_CODEC =
			ByteBufCodecs.fromCodecWithRegistries(MAP_CODEC.codec());

	public static final RecipeSerializer<AnvilShapelessRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

	private final List<Ingredient> ingredients;

	public AnvilShapelessRecipe(Recipe.CommonInfo commonInfo, List<Ingredient> ingredients, ItemStackTemplate result,
			Tool toolType, int craftTime, int toolTier, int anvilTier, boolean hotOutput,
			String requiredResearch, @Nullable Skill skill, int skillXp, float vanillaXp) {
		super(commonInfo, result, toolType, craftTime, toolTier, anvilTier, hotOutput, requiredResearch, skill, skillXp, vanillaXp);
		this.ingredients = ingredients;
	}

	@Override
	public boolean matches(CraftingInput input, Level level) {
		List<Boolean> matched = new ArrayList<>(Collections.nCopies(ingredients.size(), false));

		for (int i = 0; i < input.size(); ++i) {
			ItemStack inputItem = input.getItem(i);
			if (inputItem.isEmpty()) {
				continue;
			}

			boolean foundMatch = false;
			for (int j = 0; j < ingredients.size(); j++) {
				if (matchesIngredient(ingredients.get(j), inputItem)) {
					matched.set(j, true);
					foundMatch = true;
					break;
				}
			}
			if (!foundMatch) {
				return false;
			}
		}

		return !matched.contains(false);
	}

	private boolean matchesIngredient(Ingredient ingredient, ItemStack inputItem) {
		if (Heatable.requiresHeating && Heatable.canHeatItem(inputItem)) {
			return false;
		}
		if (!Heatable.isWorkable(inputItem)) {
			return false;
		}
		ItemStack hotItem = Heatable.getItemStack(inputItem);
		ItemStack resolved = hotItem.isEmpty() ? inputItem : hotItem;

		return !resolved.isEmpty() && ingredient.test(resolved) && CustomToolHelper.doesMatchForRecipe(ingredient, resolved);
	}

	@Override
	public RecipeSerializer<AnvilShapelessRecipe> getSerializer() {
		return SERIALIZER;
	}

	@Override
	public PlacementInfo placementInfo() {
		return PlacementInfo.create(ingredients);
	}
}
