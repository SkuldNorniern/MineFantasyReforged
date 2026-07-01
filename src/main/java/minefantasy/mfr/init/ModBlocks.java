package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(MineFantasyReforged.MOD_ID);

    // ---- Ores ----
    public static final DeferredBlock<Block> COPPER_ORE = BLOCKS.registerSimpleBlock(
            "copper_ore", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F));
    public static final DeferredBlock<Block> TIN_ORE = BLOCKS.registerSimpleBlock(
            "tin_ore", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F));
    public static final DeferredBlock<Block> SILVER_ORE = BLOCKS.registerSimpleBlock(
            "silver_ore", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F));
    public static final DeferredBlock<Block> KAOLINITE_ORE = BLOCKS.registerSimpleBlock(
            "kaolinite_ore", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(2.0F, 3.0F));
    public static final DeferredBlock<Block> NITRE_ORE = BLOCKS.registerSimpleBlock(
            "nitre_ore", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(2.0F, 3.0F));
    public static final DeferredBlock<Block> SULFUR_ORE = BLOCKS.registerSimpleBlock(
            "sulfur_ore", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(2.0F, 3.0F));
    public static final DeferredBlock<Block> BORAX_ORE = BLOCKS.registerSimpleBlock(
            "borax_ore", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(2.0F, 3.0F));
    public static final DeferredBlock<Block> TUNGSTEN_ORE = BLOCKS.registerSimpleBlock(
            "tungsten_ore", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(5.0F, 5.0F));
    public static final DeferredBlock<Block> COAL_RICH_ORE = BLOCKS.registerSimpleBlock(
            "coal_rich_ore", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F));

    // ---- Metal Storage Blocks ----
    public static final DeferredBlock<Block> COPPER_BLOCK = BLOCKS.registerSimpleBlock(
            "copper_block", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL));
    public static final DeferredBlock<Block> TIN_BLOCK = BLOCKS.registerSimpleBlock(
            "tin_block", BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL));
    public static final DeferredBlock<Block> SILVER_BLOCK = BLOCKS.registerSimpleBlock(
            "silver_block", BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL));
    public static final DeferredBlock<Block> BRONZE_BLOCK = BLOCKS.registerSimpleBlock(
            "bronze_block", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL));
    public static final DeferredBlock<Block> STEEL_BLOCK = BLOCKS.registerSimpleBlock(
            "steel_block", BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(7.0F, 8.0F).sound(SoundType.METAL));
    public static final DeferredBlock<Block> BLACK_STEEL_BLOCK = BLOCKS.registerSimpleBlock(
            "black_steel_block", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(8.0F, 10.0F).sound(SoundType.METAL));
    public static final DeferredBlock<Block> RED_STEEL_BLOCK = BLOCKS.registerSimpleBlock(
            "red_steel_block", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).requiresCorrectToolForDrops().strength(8.0F, 10.0F).sound(SoundType.METAL));
    public static final DeferredBlock<Block> BLUE_STEEL_BLOCK = BLOCKS.registerSimpleBlock(
            "blue_steel_block", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(8.0F, 10.0F).sound(SoundType.METAL));
    public static final DeferredBlock<Block> ADAMANTIUM_BLOCK = BLOCKS.registerSimpleBlock(
            "adamantium_block", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).requiresCorrectToolForDrops().strength(10.0F, 20.0F).sound(SoundType.METAL));
    public static final DeferredBlock<Block> MITHRIL_BLOCK = BLOCKS.registerSimpleBlock(
            "mithril_block", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).requiresCorrectToolForDrops().strength(10.0F, 20.0F).sound(SoundType.METAL));

    // ---- Construction Blocks ----
    public static final DeferredBlock<Block> MUD_BRICK = BLOCKS.registerSimpleBlock(
            "mud_brick", BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(2.0F, 3.0F).sound(SoundType.MUD_BRICKS));
    public static final DeferredBlock<Block> FIREBRICKS = BLOCKS.registerSimpleBlock(
            "firebricks", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).requiresCorrectToolForDrops().strength(3.0F, 5.0F));
    public static final DeferredBlock<Block> LIMESTONE = BLOCKS.registerSimpleBlock(
            "limestone", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F));
    public static final DeferredBlock<Block> LIMESTONE_BRICK = BLOCKS.registerSimpleBlock(
            "limestone_brick", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(2.0F, 6.0F));
    public static final DeferredBlock<Block> COBBLE_BRICK = BLOCKS.registerSimpleBlock(
            "cobble_brick", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(2.0F, 6.0F));
    public static final DeferredBlock<Block> REINFORCED_STONE = BLOCKS.registerSimpleBlock(
            "reinforced_stone", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(5.0F, 20.0F));
    public static final DeferredBlock<Block> REFINED_PLANKS = BLOCKS.registerSimpleBlock(
            "refined_planks", BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD));
    public static final DeferredBlock<Block> NAILED_PLANKS = BLOCKS.registerSimpleBlock(
            "nailed_planks", BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(2.5F, 4.0F).sound(SoundType.WOOD));

    // ---- Crafting Stations (stubbed - full BlockEntity implementations to be ported) ----
    public static final DeferredBlock<Block> ANVIL_STONE = BLOCKS.registerSimpleBlock(
            "anvil_stone", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.STONE));
    public static final DeferredBlock<Block> ANVIL_BRONZE = BLOCKS.registerSimpleBlock(
            "anvil_bronze", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL));
    public static final DeferredBlock<Block> ANVIL_IRON = BLOCKS.registerSimpleBlock(
            "anvil_iron", BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL));
    public static final DeferredBlock<Block> ANVIL_STEEL = BLOCKS.registerSimpleBlock(
            "anvil_steel", BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(6.0F, 8.0F).sound(SoundType.METAL));
    public static final DeferredBlock<Block> CARPENTER = BLOCKS.registerSimpleBlock(
            "carpenter", BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(2.5F, 3.0F).sound(SoundType.WOOD));
    public static final DeferredBlock<Block> FORGE = BLOCKS.registerSimpleBlock(
            "forge", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.5F, 5.0F));
    public static final DeferredBlock<Block> BLOOMERY = BLOCKS.registerSimpleBlock(
            "bloomery", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.5F, 5.0F));
    public static final DeferredBlock<Block> QUERN = BLOCKS.registerSimpleBlock(
            "quern", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 4.0F));
    public static final DeferredBlock<Block> TANNER = BLOCKS.registerSimpleBlock(
            "tanner", BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD));
    public static final DeferredBlock<Block> BELLOWS = BLOCKS.registerSimpleBlock(
            "bellows", BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(1.5F, 2.0F).sound(SoundType.WOOD));
    public static final DeferredBlock<Block> BLAST_CHAMBER = BLOCKS.registerSimpleBlock(
            "blast_chamber", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(5.0F, 8.0F));
    public static final DeferredBlock<Block> BLAST_HEATER = BLOCKS.registerSimpleBlock(
            "blast_heater", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(5.0F, 8.0F));
    public static final DeferredBlock<Block> KITCHEN_BENCH = BLOCKS.registerSimpleBlock(
            "kitchen_bench", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(2.5F, 3.0F));
    public static final DeferredBlock<Block> RESEARCH_BENCH = BLOCKS.registerSimpleBlock(
            "research_bench", BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD));
    public static final DeferredBlock<Block> SALVAGE_BLOCK = BLOCKS.registerSimpleBlock(
            "salvage_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 4.0F));
    public static final DeferredBlock<Block> TANNING_RACK = BLOCKS.registerSimpleBlock(
            "tanning_rack", BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(1.5F, 2.0F).sound(SoundType.WOOD));
    public static final DeferredBlock<Block> BOMB_BENCH = BLOCKS.registerSimpleBlock(
            "bomb_bench", BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(2.5F, 3.0F).sound(SoundType.WOOD));
    public static final DeferredBlock<Block> CROSSBOW_BENCH = BLOCKS.registerSimpleBlock(
            "crossbow_bench", BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(2.5F, 3.0F).sound(SoundType.WOOD));

    // ---- Wood Types ----
    public static final DeferredBlock<Block> LOG_YEW = BLOCKS.registerSimpleBlock(
            "log_yew", BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(2.0F).sound(SoundType.WOOD));
    public static final DeferredBlock<Block> LOG_IRONBARK = BLOCKS.registerSimpleBlock(
            "log_ironbark", BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(2.5F).sound(SoundType.WOOD));
    public static final DeferredBlock<Block> LOG_EBONY = BLOCKS.registerSimpleBlock(
            "log_ebony", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(2.0F).sound(SoundType.WOOD));
    public static final DeferredBlock<Block> YEW_PLANKS = BLOCKS.registerSimpleBlock(
            "yew_planks", BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD));
    public static final DeferredBlock<Block> IRONBARK_PLANKS = BLOCKS.registerSimpleBlock(
            "ironbark_planks", BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(2.5F, 3.5F).sound(SoundType.WOOD));
    public static final DeferredBlock<Block> EBONY_PLANKS = BLOCKS.registerSimpleBlock(
            "ebony_planks", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(2.0F, 3.0F).sound(SoundType.WOOD));

    // ---- Berry Bush ----
    public static final DeferredBlock<Block> BERRY_BUSH = BLOCKS.registerSimpleBlock(
            "berry_bush", BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().strength(0.2F).sound(SoundType.GRASS));
}
