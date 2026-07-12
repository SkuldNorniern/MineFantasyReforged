package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {

	public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
			DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MineFantasyReforged.MOD_ID);

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_TOOLS =
			CREATIVE_TABS.register("forgedtool", () -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.mfr.forgedtool"))
					.icon(() -> new ItemStack(ModItems.COPPER_ORE.get()))
					.displayItems((params, output) -> {})
					.build());

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_WEAPONS =
			CREATIVE_TABS.register("forgedweapon", () -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.mfr.forgedweapon"))
					.icon(() -> new ItemStack(Items.IRON_SWORD))
					.displayItems((params, output) -> {})
					.build());

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_ARMOUR =
			CREATIVE_TABS.register("forgedarmour", () -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.mfr.forgedarmour"))
					.icon(() -> new ItemStack(Items.IRON_CHESTPLATE))
					.displayItems((params, output) -> {})
					.build());

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_ARCHERY =
			CREATIVE_TABS.register("archery", () -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.mfr.archery"))
					.icon(() -> new ItemStack(Items.BOW))
					.displayItems((params, output) -> {})
					.build());

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_UTIL =
			CREATIVE_TABS.register("util", () -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.mfr.util"))
					.icon(() -> new ItemStack(ModItems.ANVIL_IRON.get()))
					.displayItems((params, output) -> {})
					.build());

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_GADGETS =
			CREATIVE_TABS.register("gadgets", () -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.mfr.gadgets"))
					.icon(() -> new ItemStack(Items.TNT))
					.displayItems((params, output) -> {})
					.build());

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_MATERIALS =
			CREATIVE_TABS.register("materials", () -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.mfr.materials"))
					.icon(() -> new ItemStack(ModItems.COPPER_ORE.get()))
					.displayItems((params, output) -> {})
					.build());

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_ADVANCED_TOOLS =
			CREATIVE_TABS.register("advanced_tools", () -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.mfr.advanced_tools"))
					.icon(() -> new ItemStack(Items.DIAMOND_PICKAXE))
					.displayItems((params, output) -> {})
					.build());

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_CRAFT_TOOLS =
			CREATIVE_TABS.register("craft_tools", () -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.mfr.craft_tools"))
					.icon(() -> new ItemStack(Items.ANVIL))
					.displayItems((params, output) -> {})
					.build());

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_ORES =
			CREATIVE_TABS.register("ores", () -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.mfr.ores"))
					.icon(() -> new ItemStack(ModItems.COPPER_ORE.get()))
					.displayItems((params, output) -> {})
					.build());

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_FOOD =
			CREATIVE_TABS.register("food", () -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.mfr.food"))
					.icon(() -> new ItemStack(Items.COOKED_BEEF))
					.displayItems((params, output) -> {})
					.build());

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_DRAGONFORGED =
			CREATIVE_TABS.register("dragonforged", () -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.mfr.dragonforged"))
					.icon(() -> new ItemStack(Items.DRAGON_EGG))
					.displayItems((params, output) -> {})
					.build());

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_ORNATE =
			CREATIVE_TABS.register("ornate", () -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.mfr.ornate"))
					.icon(() -> new ItemStack(Items.GOLD_INGOT))
					.displayItems((params, output) -> {})
					.build());
}
