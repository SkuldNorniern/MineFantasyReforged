package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.block.AnvilBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(MineFantasyReforged.MOD_ID);

    private static DeferredBlock<Block> stone(String name, float hardness, float resistance) {
        return BLOCKS.register(name, id -> new Block(BlockBehaviour.Properties.of()
                .setId(ResourceKey.create(Registries.BLOCK, id))
                .mapColor(MapColor.STONE)
                .requiresCorrectToolForDrops()
                .strength(hardness, resistance)));
    }

    private static DeferredBlock<Block> metal(String name, MapColor color, float hardness, float resistance) {
        return BLOCKS.register(name, id -> new Block(BlockBehaviour.Properties.of()
                .setId(ResourceKey.create(Registries.BLOCK, id))
                .mapColor(color)
                .requiresCorrectToolForDrops()
                .strength(hardness, resistance)
                .sound(SoundType.METAL)));
    }

    private static DeferredBlock<Block> wood(String name, MapColor color, float hardness, float resistance) {
        return BLOCKS.register(name, id -> new Block(BlockBehaviour.Properties.of()
                .setId(ResourceKey.create(Registries.BLOCK, id))
                .mapColor(color)
                .strength(hardness, resistance)
                .sound(SoundType.WOOD)));
    }

    // ---- Ores ----
    public static final DeferredBlock<Block> COPPER_ORE = stone("copper_ore", 3.0F, 3.0F);
    public static final DeferredBlock<Block> TIN_ORE = stone("tin_ore", 3.0F, 3.0F);
    public static final DeferredBlock<Block> SILVER_ORE = stone("silver_ore", 3.0F, 3.0F);
    public static final DeferredBlock<Block> KAOLINITE_ORE = stone("kaolinite_ore", 2.0F, 3.0F);
    public static final DeferredBlock<Block> NITRE_ORE = stone("nitre_ore", 2.0F, 3.0F);
    public static final DeferredBlock<Block> SULFUR_ORE = stone("sulfur_ore", 2.0F, 3.0F);
    public static final DeferredBlock<Block> BORAX_ORE = stone("borax_ore", 2.0F, 3.0F);
    public static final DeferredBlock<Block> TUNGSTEN_ORE = stone("tungsten_ore", 5.0F, 5.0F);
    public static final DeferredBlock<Block> COAL_RICH_ORE = stone("coal_rich_ore", 3.0F, 3.0F);

    // ---- Metal Storage Blocks ----
    public static final DeferredBlock<Block> COPPER_BLOCK = metal("copper_block", MapColor.COLOR_ORANGE, 5.0F, 6.0F);
    public static final DeferredBlock<Block> TIN_BLOCK = metal("tin_block", MapColor.METAL, 5.0F, 6.0F);
    public static final DeferredBlock<Block> SILVER_BLOCK = metal("silver_block", MapColor.METAL, 5.0F, 6.0F);
    public static final DeferredBlock<Block> BRONZE_BLOCK = metal("bronze_block", MapColor.COLOR_ORANGE, 5.0F, 6.0F);
    public static final DeferredBlock<Block> STEEL_BLOCK = metal("steel_block", MapColor.METAL, 7.0F, 8.0F);
    public static final DeferredBlock<Block> BLACK_STEEL_BLOCK = metal("black_steel_block", MapColor.COLOR_BLACK, 8.0F, 10.0F);
    public static final DeferredBlock<Block> RED_STEEL_BLOCK = metal("red_steel_block", MapColor.COLOR_RED, 8.0F, 10.0F);
    public static final DeferredBlock<Block> BLUE_STEEL_BLOCK = metal("blue_steel_block", MapColor.COLOR_BLUE, 8.0F, 10.0F);
    public static final DeferredBlock<Block> ADAMANTIUM_BLOCK = metal("adamantium_block", MapColor.COLOR_GREEN, 10.0F, 20.0F);
    public static final DeferredBlock<Block> MITHRIL_BLOCK = metal("mithril_block", MapColor.COLOR_LIGHT_BLUE, 10.0F, 20.0F);

    // ---- Construction ----
    public static final DeferredBlock<Block> MUD_BRICK = BLOCKS.register("mud_brick", id -> new Block(
            BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, id))
                    .mapColor(MapColor.DIRT).strength(2.0F, 3.0F).sound(SoundType.MUD_BRICKS)));
    public static final DeferredBlock<Block> FIREBRICKS = stone("firebricks", 3.0F, 5.0F);
    public static final DeferredBlock<Block> LIMESTONE = stone("limestone", 1.5F, 6.0F);
    public static final DeferredBlock<Block> LIMESTONE_BRICK = stone("limestone_brick", 2.0F, 6.0F);
    public static final DeferredBlock<Block> COBBLE_BRICK = stone("cobble_brick", 2.0F, 6.0F);
    public static final DeferredBlock<Block> REINFORCED_STONE = stone("reinforced_stone", 5.0F, 20.0F);
    public static final DeferredBlock<Block> REFINED_PLANKS = wood("refined_planks", MapColor.WOOD, 2.0F, 3.0F);
    public static final DeferredBlock<Block> NAILED_PLANKS = wood("nailed_planks", MapColor.WOOD, 2.5F, 4.0F);

    // ---- Crafting Stations ----
    public static final DeferredBlock<AnvilBlock> ANVIL_STONE = BLOCKS.register("anvil_stone", id -> new AnvilBlock(
            BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, id))
                    .mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final DeferredBlock<AnvilBlock> ANVIL_BRONZE = BLOCKS.register("anvil_bronze", id -> new AnvilBlock(
            BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, id))
                    .mapColor(MapColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));
    public static final DeferredBlock<AnvilBlock> ANVIL_IRON = BLOCKS.register("anvil_iron", id -> new AnvilBlock(
            BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, id))
                    .mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));
    public static final DeferredBlock<AnvilBlock> ANVIL_STEEL = BLOCKS.register("anvil_steel", id -> new AnvilBlock(
            BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, id))
                    .mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(6.0F, 8.0F).sound(SoundType.METAL)));
    public static final DeferredBlock<Block> CARPENTER = wood("carpenter", MapColor.WOOD, 2.5F, 3.0F);
    public static final DeferredBlock<Block> FORGE = BLOCKS.register("forge", id -> new Block(
            BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, id))
                    .mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.5F, 5.0F)
                    .lightLevel(state -> 8)));
    public static final DeferredBlock<Block> BLOOMERY = stone("bloomery", 3.5F, 5.0F);
    public static final DeferredBlock<Block> QUERN = stone("quern", 3.0F, 4.0F);
    public static final DeferredBlock<Block> TANNER = wood("tanner", MapColor.WOOD, 2.0F, 3.0F);
    public static final DeferredBlock<Block> BELLOWS = wood("bellows", MapColor.WOOD, 1.5F, 2.0F);
    public static final DeferredBlock<Block> BLAST_CHAMBER = stone("blast_chamber", 5.0F, 8.0F);
    public static final DeferredBlock<Block> BLAST_HEATER = stone("blast_heater", 5.0F, 8.0F);
    public static final DeferredBlock<Block> KITCHEN_BENCH = stone("kitchen_bench", 2.5F, 3.0F);
    public static final DeferredBlock<Block> RESEARCH_BENCH = wood("research_bench", MapColor.WOOD, 2.0F, 3.0F);
    public static final DeferredBlock<Block> SALVAGE_BLOCK = stone("salvage_block", 3.0F, 4.0F);
    public static final DeferredBlock<Block> TANNING_RACK = wood("tanning_rack", MapColor.WOOD, 1.5F, 2.0F);
    public static final DeferredBlock<Block> BOMB_BENCH = wood("bomb_bench", MapColor.WOOD, 2.5F, 3.0F);
    public static final DeferredBlock<Block> CROSSBOW_BENCH = wood("crossbow_bench", MapColor.WOOD, 2.5F, 3.0F);

    // ---- Wood Types ----
    public static final DeferredBlock<Block> LOG_YEW = wood("log_yew", MapColor.WOOD, 2.0F, 2.0F);
    public static final DeferredBlock<Block> LOG_IRONBARK = wood("log_ironbark", MapColor.WOOD, 2.5F, 2.5F);
    public static final DeferredBlock<Block> LOG_EBONY = wood("log_ebony", MapColor.COLOR_BLACK, 2.0F, 2.0F);
    public static final DeferredBlock<Block> YEW_PLANKS = wood("yew_planks", MapColor.WOOD, 2.0F, 3.0F);
    public static final DeferredBlock<Block> IRONBARK_PLANKS = wood("ironbark_planks", MapColor.WOOD, 2.5F, 3.5F);
    public static final DeferredBlock<Block> EBONY_PLANKS = wood("ebony_planks", MapColor.COLOR_BLACK, 2.0F, 3.0F);

    // ---- Plants ----
    public static final DeferredBlock<Block> BERRY_BUSH = BLOCKS.register("berry_bush", id -> new Block(
            BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, id))
                    .mapColor(MapColor.GRASS).noCollision().noOcclusion().strength(0.2F).sound(SoundType.GRASS)));
}
