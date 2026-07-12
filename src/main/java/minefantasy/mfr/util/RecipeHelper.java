package minefantasy.mfr.util;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecipeHelper {

	private RecipeHelper() {}

	// TODO: recipe lookup needs RecipeManager access (passed via ServerLevel or RecipeAccess)
	//       when registry and server-lifecycle classes are ported
	public static void writeFile(String filePath, String trimmedName, Object tempRecipe, Gson g) {
		File tmp = new File(filePath + trimmedName + ".json");
		boolean exists = tmp.exists();

		if (exists) {
			int index = 1;
			while (exists) {
				index++;
				File tmpFile = new File(filePath + trimmedName + "-" + index + ".json");
				exists = tmpFile.exists();
			}
			trimmedName += "-" + index;
		}

		try (FileWriter writer = new FileWriter(filePath + trimmedName + ".json")) {
			JsonWriter jsonWriter = g.newJsonWriter(writer);
			jsonWriter.setIndent("\t");
			g.toJson(tempRecipe, tempRecipe.getClass(), jsonWriter);
		} catch (IOException e) {
			System.out.println("Error during writing recipe json file:");
			e.printStackTrace();
		}
	}

	public static Character getRandomChar() {
		Random r = new Random();
		return (char) (r.nextInt(26) + 'a');
	}

	public static String stripNamespace(String string) {
		return string.replaceAll(".+:", "");
	}

	public static NonNullList<Ingredient> duplicateList(NonNullList<Ingredient> list, Integer repeatAmount) {
		if (repeatAmount == null) {
			return NonNullList.create();
		}
		NonNullList<Ingredient> duplicateList = NonNullList.create();
		for (int i = 0; i < repeatAmount; i++) {
			duplicateList.addAll(list);
		}
		return duplicateList;
	}

	public static List<ItemStack> convertNonNullList(List<ItemStack> nonNullList) {
		return new ArrayList<>(nonNullList);
	}

	public static List<ItemStack> expandPattern(List<ItemStack> stacks, int oldWidth, int oldHeight, int targetWidth, int targetHeight) {
		List<ItemStack> expandedPattern = new ArrayList<>();
		int index = 0;

		if (oldWidth < targetWidth) {
			for (int i = 0; i < oldHeight; i++) {
				for (int j = 0; j < oldWidth; j++) {
					expandedPattern.add(stacks.get(index++));
				}
				for (int j = 0; j < targetWidth - oldWidth; j++) {
					expandedPattern.add(ItemStack.EMPTY);
				}
			}
		} else {
			expandedPattern.addAll(stacks);
		}
		if (oldHeight < targetHeight) {
			for (int i = 0; i < (targetHeight - oldHeight) * targetWidth; i++) {
				expandedPattern.add(ItemStack.EMPTY);
			}
		}
		return expandedPattern;
	}

	// TODO: Ingredient.EMPTY does not exist in MC 26.x — callers must use Optional<Ingredient>
	//       or a sentinel value. Stub uses Ingredient.of() (zero-item ingredient) as placeholder.
	public static List<Ingredient> expandPattern(NonNullList<Ingredient> ingredients, int oldWidth, int oldHeight, int targetWidth, int targetHeight) {
		Ingredient empty = Ingredient.of();
		List<Ingredient> expandedPattern = new ArrayList<>();
		int index = 0;

		if (oldWidth < targetWidth) {
			for (int i = 0; i < oldHeight; i++) {
				for (int j = 0; j < oldWidth; j++) {
					expandedPattern.add(ingredients.get(index++));
				}
				for (int j = 0; j < targetWidth - oldWidth; j++) {
					expandedPattern.add(empty);
				}
			}
		} else {
			expandedPattern.addAll(ingredients);
		}
		if (oldHeight < targetHeight) {
			for (int i = 0; i < (targetHeight - oldHeight) * targetWidth; i++) {
				expandedPattern.add(empty);
			}
		}
		return expandedPattern;
	}
}
