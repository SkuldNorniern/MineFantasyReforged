package minefantasy.mfr.registry.recipe;

import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.constants.Tool;
import minefantasy.mfr.init.ModRecipeTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.jspecify.annotations.Nullable;

/**
 * Common base for anvil recipes (shaped/shapeless), matched over a 6x4 {@link CraftingInput}
 * (larger than vanilla's 3x3 crafting grid, so {@code ShapedRecipePattern} can't be reused).
 * Replaces legacy AnvilRecipeBase; the tier-modifying "dynamic"/custom-material variants are
 * deferred until the Anvil tile entity (and the tier/research side effects they trigger on it)
 * is ported.
 */
public abstract class AnvilRecipe implements Recipe<CraftingInput>, IRecipeMFR {

	public static final int MAX_WIDTH = 6;
	public static final int MAX_HEIGHT = 4;

	protected final Recipe.CommonInfo commonInfo;
	protected final ItemStackTemplate result;
	protected final Tool toolType;
	protected final int craftTime;
	protected final int toolTier;
	protected final int anvilTier;
	protected final boolean hotOutput;
	protected final String requiredResearch;
	protected final @Nullable Skill skill;
	protected final int skillXp;
	protected final float vanillaXp;

	protected AnvilRecipe(Recipe.CommonInfo commonInfo, ItemStackTemplate result, Tool toolType, int craftTime,
			int toolTier, int anvilTier, boolean hotOutput, String requiredResearch,
			@Nullable Skill skill, int skillXp, float vanillaXp) {
		this.commonInfo = commonInfo;
		this.result = result;
		this.toolType = toolType;
		this.craftTime = craftTime;
		this.toolTier = toolTier;
		this.anvilTier = anvilTier;
		this.hotOutput = hotOutput;
		this.requiredResearch = requiredResearch;
		this.skill = skill;
		this.skillXp = skillXp;
		this.vanillaXp = vanillaXp;
	}

	@Override
	public ItemStack assemble(CraftingInput input) {
		return result.create();
	}

	public ItemStack getResultItem() {
		return result.create();
	}

	@Override
	public boolean showNotification() {
		return commonInfo.showNotification();
	}

	@Override
	public String group() {
		return "";
	}

	@Override
	public abstract RecipeSerializer<? extends AnvilRecipe> getSerializer();

	@Override
	public RecipeType<? extends AnvilRecipe> getType() {
		return ModRecipeTypes.ANVIL.get();
	}

	@Override
	public RecipeBookCategory recipeBookCategory() {
		return RecipeBookCategories.CRAFTING_MISC;
	}

	public Tool getToolType() {
		return toolType;
	}

	public int getCraftTime() {
		return craftTime;
	}

	public int getToolTier() {
		return toolTier;
	}

	public int getAnvilTier() {
		return anvilTier;
	}

	public boolean isHotOutput() {
		return hotOutput;
	}

	@Override
	public String getRequiredResearch() {
		return requiredResearch;
	}

	@Override
	public @Nullable Skill getSkill() {
		return skill;
	}

	@Override
	public int getSkillXp() {
		return skillXp;
	}

	@Override
	public float getVanillaXp() {
		return vanillaXp;
	}

	@Override
	public boolean shouldSlotGiveSkillXp() {
		return false;
	}
}
