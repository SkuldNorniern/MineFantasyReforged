package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(MineFantasyReforged.MOD_ID);

    // ---- Block Items ----
    public static final DeferredItem<BlockItem> COPPER_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.COPPER_ORE);
    public static final DeferredItem<BlockItem> TIN_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.TIN_ORE);
    public static final DeferredItem<BlockItem> SILVER_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.SILVER_ORE);
    public static final DeferredItem<BlockItem> KAOLINITE_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.KAOLINITE_ORE);
    public static final DeferredItem<BlockItem> NITRE_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.NITRE_ORE);
    public static final DeferredItem<BlockItem> SULFUR_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.SULFUR_ORE);
    public static final DeferredItem<BlockItem> BORAX_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.BORAX_ORE);
    public static final DeferredItem<BlockItem> TUNGSTEN_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.TUNGSTEN_ORE);
    public static final DeferredItem<BlockItem> COAL_RICH_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.COAL_RICH_ORE);

    public static final DeferredItem<BlockItem> COPPER_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.COPPER_BLOCK);
    public static final DeferredItem<BlockItem> TIN_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.TIN_BLOCK);
    public static final DeferredItem<BlockItem> SILVER_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.SILVER_BLOCK);
    public static final DeferredItem<BlockItem> BRONZE_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.BRONZE_BLOCK);
    public static final DeferredItem<BlockItem> STEEL_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.STEEL_BLOCK);
    public static final DeferredItem<BlockItem> BLACK_STEEL_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.BLACK_STEEL_BLOCK);
    public static final DeferredItem<BlockItem> RED_STEEL_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.RED_STEEL_BLOCK);
    public static final DeferredItem<BlockItem> BLUE_STEEL_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.BLUE_STEEL_BLOCK);
    public static final DeferredItem<BlockItem> ADAMANTIUM_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.ADAMANTIUM_BLOCK);
    public static final DeferredItem<BlockItem> MITHRIL_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.MITHRIL_BLOCK);

    public static final DeferredItem<BlockItem> MUD_BRICK = ITEMS.registerSimpleBlockItem(ModBlocks.MUD_BRICK);
    public static final DeferredItem<BlockItem> FIREBRICKS = ITEMS.registerSimpleBlockItem(ModBlocks.FIREBRICKS);
    public static final DeferredItem<BlockItem> LIMESTONE = ITEMS.registerSimpleBlockItem(ModBlocks.LIMESTONE);
    public static final DeferredItem<BlockItem> LIMESTONE_BRICK = ITEMS.registerSimpleBlockItem(ModBlocks.LIMESTONE_BRICK);
    public static final DeferredItem<BlockItem> COBBLE_BRICK = ITEMS.registerSimpleBlockItem(ModBlocks.COBBLE_BRICK);
    public static final DeferredItem<BlockItem> REINFORCED_STONE = ITEMS.registerSimpleBlockItem(ModBlocks.REINFORCED_STONE);
    public static final DeferredItem<BlockItem> REFINED_PLANKS = ITEMS.registerSimpleBlockItem(ModBlocks.REFINED_PLANKS);
    public static final DeferredItem<BlockItem> NAILED_PLANKS = ITEMS.registerSimpleBlockItem(ModBlocks.NAILED_PLANKS);

    public static final DeferredItem<BlockItem> ANVIL_STONE = ITEMS.registerSimpleBlockItem(ModBlocks.ANVIL_STONE);
    public static final DeferredItem<BlockItem> ANVIL_BRONZE = ITEMS.registerSimpleBlockItem(ModBlocks.ANVIL_BRONZE);
    public static final DeferredItem<BlockItem> ANVIL_IRON = ITEMS.registerSimpleBlockItem(ModBlocks.ANVIL_IRON);
    public static final DeferredItem<BlockItem> ANVIL_STEEL = ITEMS.registerSimpleBlockItem(ModBlocks.ANVIL_STEEL);
    public static final DeferredItem<BlockItem> CARPENTER = ITEMS.registerSimpleBlockItem(ModBlocks.CARPENTER);
    public static final DeferredItem<BlockItem> FORGE = ITEMS.registerSimpleBlockItem(ModBlocks.FORGE);
    public static final DeferredItem<BlockItem> BLOOMERY = ITEMS.registerSimpleBlockItem(ModBlocks.BLOOMERY);
    public static final DeferredItem<BlockItem> QUERN = ITEMS.registerSimpleBlockItem(ModBlocks.QUERN);
    public static final DeferredItem<BlockItem> TANNER = ITEMS.registerSimpleBlockItem(ModBlocks.TANNER);
    public static final DeferredItem<BlockItem> BELLOWS = ITEMS.registerSimpleBlockItem(ModBlocks.BELLOWS);
    public static final DeferredItem<BlockItem> BLAST_CHAMBER = ITEMS.registerSimpleBlockItem(ModBlocks.BLAST_CHAMBER);
    public static final DeferredItem<BlockItem> BLAST_HEATER = ITEMS.registerSimpleBlockItem(ModBlocks.BLAST_HEATER);
    public static final DeferredItem<BlockItem> KITCHEN_BENCH = ITEMS.registerSimpleBlockItem(ModBlocks.KITCHEN_BENCH);
    public static final DeferredItem<BlockItem> RESEARCH_BENCH = ITEMS.registerSimpleBlockItem(ModBlocks.RESEARCH_BENCH);
    public static final DeferredItem<BlockItem> SALVAGE_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.SALVAGE_BLOCK);
    public static final DeferredItem<BlockItem> TANNING_RACK = ITEMS.registerSimpleBlockItem(ModBlocks.TANNING_RACK);
    public static final DeferredItem<BlockItem> BOMB_BENCH = ITEMS.registerSimpleBlockItem(ModBlocks.BOMB_BENCH);
    public static final DeferredItem<BlockItem> CROSSBOW_BENCH = ITEMS.registerSimpleBlockItem(ModBlocks.CROSSBOW_BENCH);

    public static final DeferredItem<BlockItem> LOG_YEW = ITEMS.registerSimpleBlockItem(ModBlocks.LOG_YEW);
    public static final DeferredItem<BlockItem> LOG_IRONBARK = ITEMS.registerSimpleBlockItem(ModBlocks.LOG_IRONBARK);
    public static final DeferredItem<BlockItem> LOG_EBONY = ITEMS.registerSimpleBlockItem(ModBlocks.LOG_EBONY);
    public static final DeferredItem<BlockItem> YEW_PLANKS = ITEMS.registerSimpleBlockItem(ModBlocks.YEW_PLANKS);
    public static final DeferredItem<BlockItem> IRONBARK_PLANKS = ITEMS.registerSimpleBlockItem(ModBlocks.IRONBARK_PLANKS);
    public static final DeferredItem<BlockItem> EBONY_PLANKS = ITEMS.registerSimpleBlockItem(ModBlocks.EBONY_PLANKS);
    public static final DeferredItem<BlockItem> BERRY_BUSH = ITEMS.registerSimpleBlockItem(ModBlocks.BERRY_BUSH);

    // ---- Ingots & Materials ----
    public static final DeferredItem<Item> COPPER_INGOT = ITEMS.registerSimpleItem("copper_ingot");
    public static final DeferredItem<Item> TIN_INGOT = ITEMS.registerSimpleItem("tin_ingot");
    public static final DeferredItem<Item> SILVER_INGOT = ITEMS.registerSimpleItem("silver_ingot");
    public static final DeferredItem<Item> BRONZE_INGOT = ITEMS.registerSimpleItem("bronze_ingot");
    public static final DeferredItem<Item> PIG_IRON_INGOT = ITEMS.registerSimpleItem("pig_iron_ingot");
    public static final DeferredItem<Item> STEEL_INGOT = ITEMS.registerSimpleItem("steel_ingot");
    public static final DeferredItem<Item> BLACK_STEEL_INGOT = ITEMS.registerSimpleItem("black_steel_ingot");
    public static final DeferredItem<Item> RED_STEEL_INGOT = ITEMS.registerSimpleItem("red_steel_ingot");
    public static final DeferredItem<Item> BLUE_STEEL_INGOT = ITEMS.registerSimpleItem("blue_steel_ingot");
    public static final DeferredItem<Item> ADAMANTIUM_INGOT = ITEMS.registerSimpleItem("adamantium_ingot");
    public static final DeferredItem<Item> MITHRIL_INGOT = ITEMS.registerSimpleItem("mithril_ingot");
    public static final DeferredItem<Item> TUNGSTEN_INGOT = ITEMS.registerSimpleItem("tungsten_ingot");

    // ---- Dusts & Powders ----
    public static final DeferredItem<Item> COPPER_DUST = ITEMS.registerSimpleItem("copper_dust");
    public static final DeferredItem<Item> TIN_DUST = ITEMS.registerSimpleItem("tin_dust");
    public static final DeferredItem<Item> SILVER_DUST = ITEMS.registerSimpleItem("silver_dust");
    public static final DeferredItem<Item> IRON_DUST = ITEMS.registerSimpleItem("iron_dust");
    public static final DeferredItem<Item> GOLD_DUST = ITEMS.registerSimpleItem("gold_dust");
    public static final DeferredItem<Item> COAL_DUST = ITEMS.registerSimpleItem("coal_dust");
    public static final DeferredItem<Item> KAOLINITE = ITEMS.registerSimpleItem("kaolinite");
    public static final DeferredItem<Item> NITRE = ITEMS.registerSimpleItem("nitre");
    public static final DeferredItem<Item> SULFUR = ITEMS.registerSimpleItem("sulfur");
    public static final DeferredItem<Item> BORAX = ITEMS.registerSimpleItem("borax");

    // ---- Crafting Components ----
    public static final DeferredItem<Item> STEEL_HELMET_PLATE = ITEMS.registerSimpleItem("steel_helmet_plate");
    public static final DeferredItem<Item> STEEL_INGOT_HEATED = ITEMS.registerSimpleItem("steel_ingot_heated");
    public static final DeferredItem<Item> IRON_INGOT_HEATED = ITEMS.registerSimpleItem("iron_ingot_heated");
    public static final DeferredItem<Item> STEEL_WIRE = ITEMS.registerSimpleItem("steel_wire");
    public static final DeferredItem<Item> IRON_WIRE = ITEMS.registerSimpleItem("iron_wire");
    public static final DeferredItem<Item> COPPER_WIRE = ITEMS.registerSimpleItem("copper_wire");
    public static final DeferredItem<Item> NAILS = ITEMS.registerSimpleItem("nails");
    public static final DeferredItem<Item> RIVET = ITEMS.registerSimpleItem("rivet");
    public static final DeferredItem<Item> TALLOW = ITEMS.registerSimpleItem("tallow");
    public static final DeferredItem<Item> SALT = ITEMS.registerSimpleItem("salt");

    // ---- Weapons & Tools (stub items to be ported to custom Item subclasses) ----
    public static final DeferredItem<Item> COPPER_SWORD = ITEMS.registerSimpleItem("copper_sword");
    public static final DeferredItem<Item> BRONZE_SWORD = ITEMS.registerSimpleItem("bronze_sword");
    public static final DeferredItem<Item> STEEL_SWORD = ITEMS.registerSimpleItem("steel_sword");
    public static final DeferredItem<Item> COPPER_PICKAXE = ITEMS.registerSimpleItem("copper_pickaxe");
    public static final DeferredItem<Item> BRONZE_PICKAXE = ITEMS.registerSimpleItem("bronze_pickaxe");
    public static final DeferredItem<Item> STEEL_PICKAXE = ITEMS.registerSimpleItem("steel_pickaxe");

    // ---- Armour (stub items to be ported) ----
    public static final DeferredItem<Item> COPPER_HELM = ITEMS.registerSimpleItem("copper_helm");
    public static final DeferredItem<Item> BRONZE_HELM = ITEMS.registerSimpleItem("bronze_helm");
    public static final DeferredItem<Item> STEEL_HELM = ITEMS.registerSimpleItem("steel_helm");
    public static final DeferredItem<Item> CHAIN_COIF = ITEMS.registerSimpleItem("chain_coif");

    // ---- Food ----
    public static final DeferredItem<Item> JERKY = ITEMS.registerSimpleItem("jerky");
    public static final DeferredItem<Item> BREAD_MFR = ITEMS.registerSimpleItem("bread_mfr");
    public static final DeferredItem<Item> POTTAGE = ITEMS.registerSimpleItem("pottage");
}
