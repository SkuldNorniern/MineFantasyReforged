package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.registry.recipe.AlloyRatioRecipe;
import minefantasy.mfr.registry.recipe.AlloyShapedRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeSerializers {

	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
			DeferredRegister.create(Registries.RECIPE_SERIALIZER, MineFantasyReforged.MOD_ID);

	public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AlloyShapedRecipe>> ALLOY_SHAPED =
			RECIPE_SERIALIZERS.register("alloy_shaped", () -> AlloyShapedRecipe.SERIALIZER);

	public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AlloyRatioRecipe>> ALLOY_RATIO =
			RECIPE_SERIALIZERS.register("alloy_ratio", () -> AlloyRatioRecipe.SERIALIZER);
}
