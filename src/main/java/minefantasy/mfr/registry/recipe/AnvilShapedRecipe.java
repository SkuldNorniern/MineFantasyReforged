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

import java.util.Optional;

/**
 * Shaped anvil recipe, matched over the 6x4 grid via {@link AnvilRecipePattern} (pattern+key,
 * same shape as the legacy MFR anvil recipe JSON and as vanilla's own {@code ShapedRecipePattern}
 * — just sized for a bigger grid). MFR-specific matching extras (heat requirements, hot-item
 * substitution) live here rather than in the pattern class itself.
 */
public class AnvilShapedRecipe extends AnvilRecipe {

	public static final MapCodec<AnvilShapedRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
					Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
					AnvilRecipePattern.MAP_CODEC.forGetter(o -> o.pattern),
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
			.apply(i, AnvilShapedRecipe::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, AnvilShapedRecipe> STREAM_CODEC =
			ByteBufCodecs.fromCodecWithRegistries(MAP_CODEC.codec());

	public static final RecipeSerializer<AnvilShapedRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

	private final AnvilRecipePattern pattern;

	public AnvilShapedRecipe(Recipe.CommonInfo commonInfo, AnvilRecipePattern pattern, ItemStackTemplate result,
			Tool toolType, int craftTime, int toolTier, int anvilTier, boolean hotOutput,
			String requiredResearch, @Nullable Skill skill, int skillXp, float vanillaXp) {
		super(commonInfo, result, toolType, craftTime, toolTier, anvilTier, hotOutput, requiredResearch, skill, skillXp, vanillaXp);
		this.pattern = pattern;
	}

	@Override
	public boolean matches(CraftingInput input, Level level) {
		for (int x = 0; x <= MAX_WIDTH - pattern.width(); ++x) {
			for (int y = 0; y <= MAX_HEIGHT - pattern.height(); ++y) {
				if (checkMatch(input, x, y, true) || checkMatch(input, x, y, false)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkMatch(CraftingInput input, int x, int y, boolean mirror) {
		for (int matrixX = 0; matrixX < MAX_WIDTH; ++matrixX) {
			for (int matrixY = 0; matrixY < MAX_HEIGHT; ++matrixY) {
				Optional<Ingredient> ingredient = pattern.ingredientAt(matrixX - x, matrixY - y, mirror);

				ItemStack inputItem = matrixX < input.width() && matrixY < input.height()
						? input.getItem(matrixX, matrixY) : ItemStack.EMPTY;

				if (ingredient.isEmpty()) {
					// Pattern has no ingredient in this cell — the grid must be empty here too.
					if (!inputItem.isEmpty()) {
						return false;
					}
					continue;
				}

				if (Heatable.requiresHeating && Heatable.canHeatItem(inputItem)) {
					return false;
				}
				if (!Heatable.isWorkable(inputItem)) {
					return false;
				}
				inputItem = getHotItem(inputItem);

				if (inputItem.isEmpty()) {
					return false;
				}
				if (!ingredient.get().test(inputItem)) {
					return false;
				}
				if (!CustomToolHelper.doesMatchForRecipe(ingredient.get(), inputItem)) {
					return false;
				}
			}
		}
		return true;
	}

	private ItemStack getHotItem(ItemStack item) {
		if (item.isEmpty()) {
			return ItemStack.EMPTY;
		}
		ItemStack hotItem = Heatable.getItemStack(item);
		return hotItem.isEmpty() ? item : hotItem;
	}

	@Override
	public RecipeSerializer<AnvilShapedRecipe> getSerializer() {
		return SERIALIZER;
	}

	@Override
	public PlacementInfo placementInfo() {
		return PlacementInfo.createFromOptionals(pattern.ingredients());
	}

	public int getWidth() {
		return pattern.width();
	}

	public int getHeight() {
		return pattern.height();
	}
}
