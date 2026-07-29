package minefantasy.mfr.registry.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.util.CustomToolHelper;
import minefantasy.mfr.util.Utils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Ratio-matched alloy recipe (crucible): ingredients are unordered, but the *counts* present
 * in the matrix must match the ratio of counts in the recipe (e.g. 3 copper : 1 tin).
 */
public class AlloyRatioRecipe extends AlloyRecipe {

	public static final MapCodec<AlloyRatioRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
					Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
					Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(o -> o.inputs),
					ItemStackTemplate.CODEC.fieldOf("result").forGetter(o -> o.result),
					Codec.INT.optionalFieldOf("tier", 0).forGetter(AlloyRecipe::getTier),
					Codec.STRING.optionalFieldOf("research", "none").forGetter(AlloyRecipe::getRequiredResearch),
					Skill.CODEC.optionalFieldOf("skill", Skill.NONE).forGetter(o -> o.skill),
					Codec.INT.optionalFieldOf("skill_xp", 0).forGetter(AlloyRecipe::getSkillXp),
					Codec.FLOAT.optionalFieldOf("vanilla_xp", 0.0F).forGetter(AlloyRecipe::getVanillaXp),
					Codec.INT.optionalFieldOf("repeat_amount", 1).forGetter(o -> o.repeatAmount))
			.apply(i, AlloyRatioRecipe::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, AlloyRatioRecipe> STREAM_CODEC =
			ByteBufCodecs.fromCodecWithRegistries(MAP_CODEC.codec());

	public static final RecipeSerializer<AlloyRatioRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

	private final List<Ingredient> inputs;
	private final int repeatAmount;

	public AlloyRatioRecipe(Recipe.CommonInfo commonInfo, List<Ingredient> inputs, ItemStackTemplate result,
			int tier, String requiredResearch, @Nullable Skill skill, int skillXp, float vanillaXp, int repeatAmount) {
		super(commonInfo, result, tier, requiredResearch, skill, skillXp, vanillaXp);
		this.inputs = inputs;
		this.repeatAmount = repeatAmount;
	}

	@Override
	public boolean matches(CraftingInput input, Level level) {
		Map<Ingredient, Long> ingredientRatio = inputs.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		Map<Ingredient, Long> matrixRatio = countMatches(input, ingredientRatio.keySet());

		if (!ingredientRatio.keySet().equals(matrixRatio.keySet())) {
			return false;
		}
		return compareRatios(ingredientRatio, matrixRatio);
	}

	@Override
	public ItemStack assemble(CraftingInput input) {
		Map<Ingredient, Long> matrixRatio = countMatches(input, inputs);
		long gcd = Utils.gcd(new ArrayList<>(matrixRatio.values()));

		ItemStack output = result.create();
		output.setCount(output.getCount() * Math.toIntExact(gcd));
		return output;
	}

	private Map<Ingredient, Long> countMatches(CraftingInput input, Iterable<Ingredient> ingredients) {
		Map<Ingredient, Long> counts = new HashMap<>();
		for (Ingredient ingredient : ingredients) {
			for (int i = 0; i < input.size(); i++) {
				ItemStack stack = input.getItem(i);
				if (!stack.isEmpty() && ingredient.test(stack) && CustomToolHelper.doesMatchForRecipe(ingredient, stack)) {
					counts.merge(ingredient, 1L, Math::addExact);
				}
			}
		}
		return counts;
	}

	private boolean compareRatios(Map<Ingredient, Long> ingredientRatio, Map<Ingredient, Long> matrixRatio) {
		List<Long> ingredientCounts = new ArrayList<>(ingredientRatio.values());
		List<Long> matrixCounts = new ArrayList<>(matrixRatio.values());
		long gcd = Utils.gcd(matrixCounts);

		List<Long> reducedMatrixRatio = new ArrayList<>();
		matrixCounts.forEach(count -> reducedMatrixRatio.add(count / gcd));

		return ingredientCounts.equals(reducedMatrixRatio);
	}

	@Override
	public RecipeSerializer<AlloyRatioRecipe> getSerializer() {
		return SERIALIZER;
	}

	@Override
	public PlacementInfo placementInfo() {
		return PlacementInfo.create(inputs);
	}

	public int getRepeatAmount() {
		return repeatAmount;
	}
}
