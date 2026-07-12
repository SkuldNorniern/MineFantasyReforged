package minefantasy.mfr.config;

// TODO: wire these fields to NeoForge ModConfigSpec when config porting is complete
// Note: EntityDragon.interestTimeSeconds and EntityDragon.heartChance were previously
//       set from this config at init — assign them from these fields when EntityDragon is ported.
public class ConfigMobs {
	public static boolean shouldFireCausePanic = false;
	public static boolean upgradeZombieWep = true;
	public static float zombieWepChance = 1.0F;
	public static boolean fastZombies = true;
	public static boolean criticalLimp = true;
	public static boolean swordSkeleton = true;

	// Dragon stats
	public static int youngdragonHP = 60;
	public static int youngdragonMD = 4;
	public static int youngdragonFD = 2;
	public static int youngdragonFT = 10;

	public static int dragonHP = 100;
	public static int dragonMD = 7;
	public static int dragonFD = 5;
	public static int dragonFT = 40;

	public static int diredragonHP = 200;
	public static int diredragonMD = 8;
	public static int diredragonFD = 8;
	public static int diredragonFT = 40;

	public static int elderdragonHP = 500;
	public static int elderdragonMD = 14;
	public static int elderdragonFD = 10;
	public static int elderdragonFT = 50;

	public static int ancientdragonHP = 1000;
	public static int ancientdragonMD = 20;
	public static int ancientdragonFD = 10;
	public static int ancientdragonFT = 100;

	public static int dragonInterval = 12000;
	public static float dragonChance = 5F;
	public static int[] dragonDimensionID = new int[]{0, -1};
	public static boolean dragonKillNPC = true;
	public static boolean dragonGriefFire = true;
	public static boolean dragonGriefGeneral = true;
	public static boolean dragonMSG = true;
	public static int dragonInterestTimeSeconds = 90;
	public static float dragonHeartChance = 1.0F;

	// Minotaur stats
	public static int minotaurSpawnrate = 5;
	public static int minotaurSpawnrateNether = 25;

	public static int minotaurHP = 30, minotaurMD = 5, minotaurGD = 5, minotaurBT = 25,
			minotaurBD = 7, minotaurDC = 10, minotaurGC = 5, minotaurGCB = 10, minotaurTC = 20;

	public static int guardminotaurHP = 30, guardminotaurMD = 6, guardminotaurGD = 7, guardminotaurBT = 35,
			guardminotaurBD = 8, guardminotaurDC = 10, guardminotaurGC = 5, guardminotaurGCB = 10, guardminotaurTC = 20;
	public static int lightminotaurAR = 100;

	public static int eliteminotaurHP = 50, eliteminotaurMD = 6, eliteminotaurGD = 6, eliteminotaurBT = 40,
			eliteminotaurBD = 8, eliteminotaurDC = 20, eliteminotaurGC = 10, eliteminotaurGCB = 20, eliteminotaurTC = 20;
	public static int mediumminotaurAR = 400;

	public static int bossminotaurHP = 60, bossminotaurMD = 8, bossminotaurGD = 10, bossminotaurBT = 40,
			bossminotaurBD = 10, bossminotaurDC = 40, bossminotaurGC = 20, bossminotaurGCB = 40, bossminotaurTC = 20;
	public static int heavyminotaurAR = 600;

	public static int frostminotaurAR = 50;
	public static int dreadminotaurAR = 100;
}
