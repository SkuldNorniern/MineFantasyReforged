package minefantasy.mfr.registry.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.chars.CharArraySet;
import it.unimi.dsi.fastutil.chars.CharSet;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Anvil equivalent of vanilla {@code ShapedRecipePattern} — same ASCII-art {@code pattern} +
 * {@code key} shape (which is also what the legacy MFR anvil recipe JSON already uses, byte for
 * byte, making the recipe-JSON converter far simpler), but sized for the anvil's 6x4 grid instead
 * of vanilla's hardcoded 3x3 cap ({@code ShapedRecipePattern.MAX_SIZE = 3}), so it can't be reused
 * directly.
 * <p>
 * Unlike {@link AlloyShapedRecipe} (which fits within 3x3 and does reuse vanilla's class), this
 * also properly supports gaps *within* the recipe's own bounding box (e.g. a pickaxe's classic
 * "T" shape) via {@code Optional<Ingredient>} per cell — the flat-list version this replaced
 * could only express "smaller than the full grid", not internal gaps (see commit `20c73907`).
 */
public final class AnvilRecipePattern {

	public static final int MAX_WIDTH = AnvilRecipe.MAX_WIDTH;
	public static final int MAX_HEIGHT = AnvilRecipe.MAX_HEIGHT;

	public static final MapCodec<AnvilRecipePattern> MAP_CODEC = Data.MAP_CODEC.flatXmap(
			AnvilRecipePattern::unpack,
			pattern -> pattern.data.map(DataResult::success).orElseGet(() -> DataResult.error(() -> "Cannot encode unpacked recipe")));

	public static final StreamCodec<RegistryFriendlyByteBuf, AnvilRecipePattern> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, p -> p.width,
			ByteBufCodecs.VAR_INT, p -> p.height,
			Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), p -> p.ingredients,
			AnvilRecipePattern::createFromNetwork);

	private final int width;
	private final int height;
	private final List<Optional<Ingredient>> ingredients;
	private final Optional<Data> data;

	private AnvilRecipePattern(int width, int height, List<Optional<Ingredient>> ingredients, Optional<Data> data) {
		this.width = width;
		this.height = height;
		this.ingredients = ingredients;
		this.data = data;
	}

	private static AnvilRecipePattern createFromNetwork(Integer width, Integer height, List<Optional<Ingredient>> ingredients) {
		return new AnvilRecipePattern(width, height, ingredients, Optional.empty());
	}

	public static AnvilRecipePattern of(Map<Character, Ingredient> key, String... pattern) {
		return of(key, List.of(pattern));
	}

	public static AnvilRecipePattern of(Map<Character, Ingredient> key, List<String> pattern) {
		return unpack(new Data(key, pattern)).getOrThrow();
	}

	private static DataResult<AnvilRecipePattern> unpack(Data data) {
		String[] shrunk = shrink(data.pattern);
		int width = shrunk[0].length();
		int height = shrunk.length;
		List<Optional<Ingredient>> ingredients = new ArrayList<>(width * height);
		CharSet unusedSymbols = new CharArraySet(data.key.keySet());

		for (String line : shrunk) {
			for (int x = 0; x < line.length(); x++) {
				char symbol = line.charAt(x);
				Optional<Ingredient> ingredient;
				if (symbol == ' ') {
					ingredient = Optional.empty();
				} else {
					Ingredient forSymbol = data.key.get(symbol);
					if (forSymbol == null) {
						return DataResult.error(() -> "Pattern references symbol '" + symbol + "' but it's not defined in the key");
					}
					ingredient = Optional.of(forSymbol);
				}
				unusedSymbols.remove(symbol);
				ingredients.add(ingredient);
			}
		}

		if (width > MAX_WIDTH || height > MAX_HEIGHT) {
			return DataResult.error(() -> "Invalid pattern: max size is " + MAX_WIDTH + "x" + MAX_HEIGHT);
		}
		return !unusedSymbols.isEmpty()
				? DataResult.error(() -> "Key defines symbols that aren't used in pattern: " + unusedSymbols)
				: DataResult.success(new AnvilRecipePattern(width, height, ingredients, Optional.of(data)));
	}

	private static String[] shrink(List<String> pattern) {
		int left = Integer.MAX_VALUE;
		int right = 0;
		int top = 0;
		int bottom = 0;

		for (int i = 0; i < pattern.size(); i++) {
			String line = pattern.get(i);
			left = Math.min(left, firstNonEmpty(line));
			int lastNonSpace = lastNonEmpty(line);
			right = Math.max(right, lastNonSpace);
			if (lastNonSpace < 0) {
				if (top == i) {
					top++;
				}
				bottom++;
			} else {
				bottom = 0;
			}
		}

		if (pattern.size() == bottom) {
			return new String[0];
		}
		String[] result = new String[pattern.size() - bottom - top];
		for (int line = 0; line < result.length; line++) {
			result[line] = pattern.get(line + top).substring(left, right + 1);
		}
		return result;
	}

	private static int firstNonEmpty(String line) {
		int index = 0;
		while (index < line.length() && line.charAt(index) == ' ') {
			index++;
		}
		return index;
	}

	private static int lastNonEmpty(String line) {
		int index = line.length() - 1;
		while (index >= 0 && line.charAt(index) == ' ') {
			index--;
		}
		return index;
	}

	/**
	 * Looks up the ingredient at ({@code recipeX},{@code recipeY}) within this pattern's own
	 * bounding box (mirror-aware), or {@link Optional#empty()} if that cell is out of bounds or
	 * intentionally blank. Matching itself lives in {@link AnvilShapedRecipe} — MFR's anvil needs
	 * extra checks (heating/hot-item) per cell that don't belong in a generic pattern class.
	 */
	public Optional<Ingredient> ingredientAt(int recipeX, int recipeY, boolean mirror) {
		if (recipeX < 0 || recipeY < 0 || recipeX >= width || recipeY >= height) {
			return Optional.empty();
		}
		return mirror
				? ingredients.get(width - recipeX - 1 + recipeY * width)
				: ingredients.get(recipeX + recipeY * width);
	}

	public int width() {
		return width;
	}

	public int height() {
		return height;
	}

	public List<Optional<Ingredient>> ingredients() {
		return ingredients;
	}

	private record Data(Map<Character, Ingredient> key, List<String> pattern) {
		private static final Codec<List<String>> PATTERN_CODEC = Codec.STRING.listOf().comapFlatMap(strings -> {
			if (strings.size() > MAX_HEIGHT) {
				return DataResult.error(() -> "Invalid pattern: too many rows, " + MAX_HEIGHT + " is maximum");
			} else if (strings.isEmpty()) {
				return DataResult.error(() -> "Invalid pattern: empty pattern not allowed");
			} else {
				int firstLength = strings.getFirst().length();
				for (String line : strings) {
					if (line.length() > MAX_WIDTH) {
						return DataResult.error(() -> "Invalid pattern: too many columns, " + MAX_WIDTH + " is maximum");
					}
					if (firstLength != line.length()) {
						return DataResult.error(() -> "Invalid pattern: each row must be the same width");
					}
				}
				return DataResult.success(strings);
			}
		}, java.util.function.Function.identity());

		private static final Codec<Character> SYMBOL_CODEC = Codec.STRING.comapFlatMap(symbol -> {
			if (symbol.length() != 1) {
				return DataResult.error(() -> "Invalid key entry: '" + symbol + "' is an invalid symbol (must be 1 character only).");
			}
			return " ".equals(symbol) ? DataResult.error(() -> "Invalid key entry: ' ' is a reserved symbol.") : DataResult.success(symbol.charAt(0));
		}, String::valueOf);

		static final MapCodec<Data> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
						ExtraCodecs.strictUnboundedMap(SYMBOL_CODEC, Ingredient.CODEC).fieldOf("key").forGetter(d -> d.key),
						PATTERN_CODEC.fieldOf("pattern").forGetter(d -> d.pattern))
				.apply(i, Data::new));
	}
}
