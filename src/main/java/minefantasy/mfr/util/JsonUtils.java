package minefantasy.mfr.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import javax.annotation.Nullable;
import java.util.MissingResourceException;

public class JsonUtils {

	private JsonUtils() { }

	public static ItemStack getItemStack(JsonElement json) {
		return getItemStack(json, (i, c, t, n) -> {
			ItemStack stack = new ItemStack(i, c);
			if (t != null) {
				stack.set(DataComponents.CUSTOM_DATA, CustomData.of(t));
			}
			return stack;
		});
	}

	private static <T> T getItemStack(JsonElement element, ItemStackCreator<T> creator) {
		if (element.isJsonPrimitive()) {
			return creator.instantiate(getItem(element.getAsString()), 1, null, false);
		}

		JsonObject obj = element.getAsJsonObject();
		String registryName = GsonHelper.getAsString(obj, "item");
		Item item = getItem(registryName);

		int count = GsonHelper.isValidNode(obj, "count") ? GsonHelper.getAsInt(obj, "count") : 1;

		CompoundTag tagCompound = null;
		if (GsonHelper.isValidNode(obj, "nbt")) {
			try {
				tagCompound = TagParser.parseTag(GsonHelper.getAsString(obj, "nbt"));
			} catch (CommandSyntaxException e) {
				MineFantasyReforged.LOG.error("Error reading item stack nbt: {}", e.getMessage());
			}
		}

		boolean ignoreNbt = GsonHelper.isValidNode(obj, "ignore_nbt") && GsonHelper.getAsBoolean(obj, "ignore_nbt");

		return creator.instantiate(item, count, tagCompound, ignoreNbt);
	}

	private interface ItemStackCreator<R> {
		R instantiate(Item item, int count, @Nullable CompoundTag tagCompound, boolean ignoreNbt);
	}

	public static Item getItem(String registryName) {
		ResourceLocation key = new ResourceLocation(registryName);
		Item item = BuiltInRegistries.ITEM.get(key);
		if (item == null || !BuiltInRegistries.ITEM.containsKey(key)) {
			throw new MissingResourceException("Unable to find item with registry name \"" + registryName + "\"",
					Item.class.getName(), registryName);
		}
		return item;
	}
}
