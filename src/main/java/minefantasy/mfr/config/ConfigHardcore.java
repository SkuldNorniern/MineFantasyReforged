package minefantasy.mfr.config;

// TODO: wire these fields to NeoForge ModConfigSpec when config porting is complete
// Note: ResearchLogic.knowledgelyr, InformationBase.unlockAll/easyResearch, and
//       Heatable.HCCquenchRuin were previously set from this config at init — assign
//       them from these fields when those classes are ported.
public class ConfigHardcore {
	public static boolean HCCreduceIngots = true;
	public static boolean HCChotBurn = true;
	public static boolean HCCquenchRuin = true;
	public static boolean HCCWeakItems = true;
	public static boolean HCCallowRocks = true;
	public static boolean HCCRemoveCraftBread = true;
	public static boolean HCCRemoveCraftPumpkinPie = true;
	public static boolean HCCRemoveCraftCake = true;
	public static boolean HCCRemoveCraftFlintAndSteel = true;
	public static boolean HCCRemoveCraftBucket = true;
	public static boolean HCCRemoveBooksCraft = false;
	public static boolean HCCRemoveTalismansCraft = false;
	public static int researchKnowledgeLayer = 0;
	public static boolean researchUnlockAll = false;
	public static boolean researchEasyMode = false;
	public static boolean hunterKnife = false;
	public static boolean lessHunt = false;
	public static boolean dropRawhide = true;
	public static boolean preventCook = false;
	public static boolean preventCeramic = false;
	public static boolean enableOverheat = true;
	public static int foodRepeatPenaltyLimit = 3;
	public static int dirtyProgressSkillModifier = 2;
	public static int dirtyProgressMax = 100;
}
