package minefantasy.mfr.registry.recipe;

import minefantasy.mfr.constants.Skill;
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
 * Common base for the crucible's alloy recipes (shaped and ratio-matched).
 * Replaces legacy AlloyRecipeBase; matching context is up to a 3x3 {@link CraftingInput}.
 */
public abstract class AlloyRecipe implements Recipe<CraftingInput>, IRecipeMFR {

	protected final Recipe.CommonInfo commonInfo;
	protected final ItemStackTemplate result;
	protected final int tier;
	protected final String requiredResearch;
	protected final @Nullable Skill skill;
	protected final int skillXp;
	protected final float vanillaXp;

	protected AlloyRecipe(Recipe.CommonInfo commonInfo, ItemStackTemplate result, int tier,
			String requiredResearch, @Nullable Skill skill, int skillXp, float vanillaXp) {
		this.commonInfo = commonInfo;
		this.result = result;
		this.tier = tier;
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
	public abstract RecipeSerializer<? extends AlloyRecipe> getSerializer();

	@Override
	public RecipeType<? extends AlloyRecipe> getType() {
		return ModRecipeTypes.ALLOY.get();
	}

	@Override
	public RecipeBookCategory recipeBookCategory() {
		return RecipeBookCategories.CRAFTING_MISC;
	}

	public int getTier() {
		return tier;
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
		// TODO: ConfigHardcore.HCCreduceIngots not yet ported
		return true;
	}
}
