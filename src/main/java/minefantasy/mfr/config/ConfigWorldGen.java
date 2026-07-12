package minefantasy.mfr.config;

// TODO: wire these fields to NeoForge ModConfigSpec when config porting is complete
public class ConfigWorldGen {
	// Copper
	public static float copperRarity = 1.0F;
	public static int copperFrequencyMin = 8;
	public static int copperFrequencyMax = 8;
	public static int copperLayerMin = 48;
	public static int copperLayerMax = 96;
	public static int copperSize = 8;

	// Tin
	public static float tinRarity = 1.0F;
	public static int tinFrequencyMin = 8;
	public static int tinFrequencyMax = 8;
	public static int tinLayerMin = 48;
	public static int tinLayerMax = 96;
	public static int tinSize = 5;

	// Silver
	public static float silverRarity = 1.0F;
	public static int silverFrequencyMin = 3;
	public static int silverFrequencyMax = 4;
	public static int silverLayerMin = 0;
	public static int silverLayerMax = 32;
	public static int silverSize = 8;

	// Wolframite
	public static float wolframiteRarity = 1.0F;
	public static int wolframiteFrequencyMin = 1;
	public static int wolframiteFrequencyMax = 1;
	public static int wolframiteLayerMin = 0;
	public static int wolframiteLayerMax = 16;
	public static int wolframiteSize = 7;

	// Mythic
	public static float mythicRarity = 0.05F;
	public static int mythicFrequencyMin = 2;
	public static int mythicFrequencyMax = 5;
	public static int mythicLayerMin = 4;
	public static int mythicLayerMax = 6;
	public static int mythicSize = 8;

	// Kaolinite
	public static float kaoliniteRarity = 0.25F;
	public static int kaoliniteFrequencyMin = 1;
	public static int kaoliniteFrequencyMax = 1;
	public static int kaoliniteLayerMin = 48;
	public static int kaoliniteLayerMax = 72;
	public static int kaoliniteSize = 16;

	// Clay
	public static float clayRarity = 0.15F;
	public static int clayFrequencyMin = 1;
	public static int clayFrequencyMax = 1;
	public static int clayLayerMin = 60;
	public static int clayLayerMax = 68;
	public static int claySize = 32;

	// Nitre
	public static float nitreRarity = 1.0F;
	public static int nitreFrequencyMin = 2;
	public static int nitreFrequencyMax = 5;
	public static int nitreLayerMin = 16;
	public static int nitreLayerMax = 64;
	public static int nitreSize = 8;

	// Sulfur
	public static float sulfurRarity = 1.0F;
	public static int sulfurFrequencyMin = 6;
	public static int sulfurFrequencyMax = 12;
	public static int sulfurLayerMin = 0;
	public static int sulfurLayerMax = 16;
	public static int sulfurSize = 4;

	// Borax
	public static float boraxRarity = 0.1F;
	public static int boraxFrequencyMin = 5;
	public static int boraxFrequencyMax = 10;
	public static int boraxLayerMin = 48;
	public static int boraxLayerMax = 96;
	public static int boraxSize = 8;

	// Rich Coal
	public static float coalRarity = 1.0F;
	public static int coalFrequencyMin = 5;
	public static int coalFrequencyMax = 5;
	public static int coalLayerMin = 0;
	public static int coalLayerMax = 64;
	public static int coalSize = 8;

	// Limestone
	public static float limestoneRarity = 0.025F;
	public static int limestoneFrequencyMin = 1;
	public static int limestoneFrequencyMax = 2;
	public static int limestoneLayerMin = 48;
	public static int limestoneLayerMax = 96;
	public static int limestoneSize = 48;

	// Berry Bush
	public static int berryRarity = 20;
	public static int berryGroupSize = 20;
	public static float berryMinTemp = 0.2F;
	public static float berryMaxTemp = 1.0F;
	public static float berryMinRain = 0.3F;
	public static float berryMaxRain = 1.0F;

	// Yew Tree
	public static float yewRarity = 0.001F;
	public static float yewMinTemp = 0.2F;
	public static float yewMaxTemp = 1.0F;
	public static float yewMinRain = 0.3F;
	public static float yewMaxRain = 1.0F;

	// Ironbark Tree
	public static float ironbarkRarity = 0.0015F;
	public static float ironbarkMinTemp = 0.4F;
	public static float ironbarkMaxTemp = 1.2F;
	public static float ironbarkMinRain = 0.0F;
	public static float ironbarkMaxRain = 0.8F;

	// Ebony Tree
	public static float ebonyRarity = 0.0005F;
	public static float ebonyMinTemp = 0.2F;
	public static float ebonyMaxTemp = 1.0F;
	public static float ebonyMinRain = 0.4F;
	public static float ebonyMaxRain = 1.0F;

	// Structures
	public static int structureTickRate = 1;

	public static float ancientForgeSpawnChance = 0.025F;
	public static int ancientForgeGrid = 2;

	public static float ancientAltarSpawnChance = 0.020F;
	public static int ancientAltarGrid = 6;

	public static float dwarvenStrongholdSpawnChance = 0.080F;
	public static int dwarvenStrongholdGrid = 12;
	public static int dwarvenStrongholdLength = 8;
	public static int dwarvenStrongholdDeviations = 1;
	public static boolean dwarvenStrongholdShouldFurnaceSpawn = true;
	public static boolean dwarvenStrongholdShouldCauldronSpawn = false;
}
