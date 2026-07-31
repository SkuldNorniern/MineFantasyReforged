package minefantasy.mfr.registry.recipe.ingredient;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import minefantasy.mfr.init.ModIngredientTypes;
import minefantasy.mfr.registry.material.CustomMaterial;
import minefantasy.mfr.registry.material.CustomMaterialRegistry;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.HolderSetCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * An {@link Ingredient} that additionally requires a specific (or any non-{@code NONE})
 * {@link CustomMaterial} to be set on the matched {@link ItemStack} — e.g. "the {@code bar} item,
 * but specifically the copper one". Vanilla {@code Ingredient} can only match item identity/tags;
 * this is what legacy MFR recipe JSON's constants like {@code #BAR_COPPER} (a specific material)
 * and {@code #ALL_BARS} ({@code material} omitted — any material) need, and almost every real
 * anvil/alloy/bloomery recipe uses one of these. See {@code docs/neoforge/api_changes_26.1.2.md}
 * § "Custom (non-item/tag) ingredients" for why {@code DataComponentIngredient} doesn't cover
 * this (it compares whole component values, not sub-keys within the nested
 * {@code mf_custom_materials} compound tag that {@link CustomMaterialRegistry} uses).
 * <p>
 * JSON usage (wherever a vanilla {@code Ingredient} field already accepts JSON — the dispatch is
 * automatic via NeoForge's {@code neoforge:ingredient_type} key, no changes needed to any recipe
 * codec that already uses {@code Ingredient.CODEC}):
 * <pre>{@code
 * {
 *   "neoforge:ingredient_type": "minefantasyreforged:material",
 *   "items": "minefantasyreforged:bar",
 *   "material": "copper"
 * }
 * }</pre>
 * {@code "slot"} defaults to {@link CustomToolHelper#slot_main}; {@code "material"} may be
 * omitted to match any material of that slot (i.e. not {@link CustomMaterialRegistry#NONE}).
 */
public record MaterialIngredient(HolderSet<Item> itemSet, String slot, Optional<String> material) implements ICustomIngredient {

	public static final MapCodec<MaterialIngredient> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
					HolderSetCodec.create(Registries.ITEM, BuiltInRegistries.ITEM.holderByNameCodec(), false)
							.fieldOf("items").forGetter(MaterialIngredient::itemSet),
					Codec.STRING.optionalFieldOf("slot", CustomToolHelper.slot_main).forGetter(MaterialIngredient::slot),
					Codec.STRING.optionalFieldOf("material").forGetter(MaterialIngredient::material))
			.apply(i, MaterialIngredient::new));

	@Override
	public boolean test(ItemStack stack) {
		if (!itemSet.contains(stack.typeHolder())) {
			return false;
		}
		CustomMaterial found = CustomMaterialRegistry.getMaterialFor(stack, slot);
		if (found == CustomMaterialRegistry.NONE) {
			return false;
		}
		return material.isEmpty() || material.get().equalsIgnoreCase(found.getName());
	}

	@Override
	public Stream<Holder<Item>> items() {
		return itemSet.stream();
	}

	@Override
	public boolean isSimple() {
		return false;
	}

	@Override
	public IngredientType<?> getType() {
		return ModIngredientTypes.MATERIAL.get();
	}

	// equals()/hashCode() required by ICustomIngredient's contract are satisfied by this being a
	// record — all components (items, slot, material) are compared automatically.
}
