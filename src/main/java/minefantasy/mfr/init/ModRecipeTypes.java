package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.registry.recipe.AlloyRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeTypes {

	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
			DeferredRegister.create(Registries.RECIPE_TYPE, MineFantasyReforged.MOD_ID);

	public static final DeferredHolder<RecipeType<?>, RecipeType<AlloyRecipe>> ALLOY =
			RECIPE_TYPES.register("alloy", () -> new RecipeType<AlloyRecipe>() {
				@Override
				public String toString() {
					return "alloy";
				}
			});
}
