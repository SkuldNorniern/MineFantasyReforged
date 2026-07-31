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
					.displayItems((params, output) -> {
						output.accept(ModItems.COPPER_PICKAXE.get());
						output.accept(ModItems.BRONZE_PICKAXE.get());
						output.accept(ModItems.STEEL_PICKAXE.get());
					})
					.build());

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_WEAPONS =
			CREATIVE_TABS.register("forgedweapon", () -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.mfr.forgedweapon"))
					.icon(() -> new ItemStack(Items.IRON_SWORD))
					.displayItems((params, output) -> {
						output.accept(ModItems.COPPER_SWORD.get());
						output.accept(ModItems.BRONZE_SWORD.get());
						output.accept(ModItems.STEEL_SWORD.get());
					})
					.build());

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_ARMOUR =
			CREATIVE_TABS.register("forgedarmour", () -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.mfr.forgedarmour"))
					.icon(() -> new ItemStack(Items.IRON_CHESTPLATE))
					.displayItems((params, output) -> {
						output.accept(ModItems.COPPER_HELM.get());
						output.accept(ModItems.BRONZE_HELM.get());
						output.accept(ModItems.STEEL_HELM.get());
						output.accept(ModItems.CHAIN_COIF.get());
					})
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
					.displayItems((params, output) -> {
						output.accept(ModItems.ANVIL_STONE.get());
						output.accept(ModItems.ANVIL_BRONZE.get());
						output.accept(ModItems.ANVIL_IRON.get());
						output.accept(ModItems.ANVIL_STEEL.get());
						output.accept(ModItems.CARPENTER.get());
						output.accept(ModItems.FORGE.get());
						output.accept(ModItems.BLOOMERY.get());
						output.accept(ModItems.QUERN.get());
						output.accept(ModItems.TANNER.get());
						output.accept(ModItems.BELLOWS.get());
						output.accept(ModItems.BLAST_CHAMBER.get());
						output.accept(ModItems.BLAST_HEATER.get());
						output.accept(ModItems.KITCHEN_BENCH.get());
						output.accept(ModItems.RESEARCH_BENCH.get());
						output.accept(ModItems.SALVAGE_BLOCK.get());
						output.accept(ModItems.TANNING_RACK.get());
						output.accept(ModItems.BOMB_BENCH.get());
						output.accept(ModItems.CROSSBOW_BENCH.get());
						output.accept(ModItems.MUD_BRICK.get());
						output.accept(ModItems.FIREBRICKS.get());
						output.accept(ModItems.LIMESTONE.get());
						output.accept(ModItems.LIMESTONE_BRICK.get());
						output.accept(ModItems.COBBLE_BRICK.get());
						output.accept(ModItems.REINFORCED_STONE.get());
						output.accept(ModItems.REFINED_PLANKS.get());
						output.accept(ModItems.NAILED_PLANKS.get());
						output.accept(ModItems.LOG_YEW.get());
						output.accept(ModItems.LOG_IRONBARK.get());
						output.accept(ModItems.LOG_EBONY.get());
						output.accept(ModItems.YEW_PLANKS.get());
						output.accept(ModItems.IRONBARK_PLANKS.get());
						output.accept(ModItems.EBONY_PLANKS.get());
						output.accept(ModItems.BERRY_BUSH.get());
					})
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
					.displayItems((params, output) -> {
						output.accept(ModItems.COPPER_INGOT.get());
						output.accept(ModItems.TIN_INGOT.get());
						output.accept(ModItems.SILVER_INGOT.get());
						output.accept(ModItems.BRONZE_INGOT.get());
						output.accept(ModItems.PIG_IRON_INGOT.get());
						output.accept(ModItems.STEEL_INGOT.get());
						output.accept(ModItems.BLACK_STEEL_INGOT.get());
						output.accept(ModItems.RED_STEEL_INGOT.get());
						output.accept(ModItems.BLUE_STEEL_INGOT.get());
						output.accept(ModItems.ADAMANTIUM_INGOT.get());
						output.accept(ModItems.MITHRIL_INGOT.get());
						output.accept(ModItems.TUNGSTEN_INGOT.get());
						output.accept(ModItems.OBSIDIAN_INGOT.get());
						output.accept(ModItems.ENCRUSTED_INGOT.get());
						output.accept(ModItems.IGNOTUMITE_INGOT.get());
						output.accept(ModItems.MITHIUM_INGOT.get());
						output.accept(ModItems.ENDER_INGOT.get());
						output.accept(ModItems.COMPOSITE_ALLOY_INGOT.get());
						output.accept(ModItems.COPPER_DUST.get());
						output.accept(ModItems.TIN_DUST.get());
						output.accept(ModItems.SILVER_DUST.get());
						output.accept(ModItems.IRON_DUST.get());
						output.accept(ModItems.GOLD_DUST.get());
						output.accept(ModItems.COAL_DUST.get());
						output.accept(ModItems.KAOLINITE.get());
						output.accept(ModItems.NITRE.get());
						output.accept(ModItems.SULFUR.get());
						output.accept(ModItems.BORAX.get());
						output.accept(ModItems.STEEL_HELMET_PLATE.get());
						output.accept(ModItems.STEEL_INGOT_HEATED.get());
						output.accept(ModItems.IRON_INGOT_HEATED.get());
						output.accept(ModItems.STEEL_WIRE.get());
						output.accept(ModItems.IRON_WIRE.get());
						output.accept(ModItems.COPPER_WIRE.get());
						output.accept(ModItems.NAILS.get());
						output.accept(ModItems.RIVET.get());
						output.accept(ModItems.TALLOW.get());
						output.accept(ModItems.SALT.get());
					})
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
					.displayItems((params, output) -> {
						output.accept(ModItems.COPPER_ORE.get());
						output.accept(ModItems.TIN_ORE.get());
						output.accept(ModItems.SILVER_ORE.get());
						output.accept(ModItems.KAOLINITE_ORE.get());
						output.accept(ModItems.NITRE_ORE.get());
						output.accept(ModItems.SULFUR_ORE.get());
						output.accept(ModItems.BORAX_ORE.get());
						output.accept(ModItems.TUNGSTEN_ORE.get());
						output.accept(ModItems.COAL_RICH_ORE.get());
						output.accept(ModItems.COPPER_BLOCK.get());
						output.accept(ModItems.TIN_BLOCK.get());
						output.accept(ModItems.SILVER_BLOCK.get());
						output.accept(ModItems.BRONZE_BLOCK.get());
						output.accept(ModItems.STEEL_BLOCK.get());
						output.accept(ModItems.BLACK_STEEL_BLOCK.get());
						output.accept(ModItems.RED_STEEL_BLOCK.get());
						output.accept(ModItems.BLUE_STEEL_BLOCK.get());
						output.accept(ModItems.ADAMANTIUM_BLOCK.get());
						output.accept(ModItems.MITHRIL_BLOCK.get());
					})
					.build());

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_FOOD =
			CREATIVE_TABS.register("food", () -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.mfr.food"))
					.icon(() -> new ItemStack(Items.COOKED_BEEF))
					.displayItems((params, output) -> {
						output.accept(ModItems.JERKY.get());
						output.accept(ModItems.BREAD_MFR.get());
						output.accept(ModItems.POTTAGE.get());
					})
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
