package minefantasy.mfr.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class MFRConfig {
    public static final ModConfigSpec SERVER_SPEC;
    public static final ModConfigSpec CLIENT_SPEC;

    public static final ModConfigSpec.BooleanValue ENABLE_STAMINA;
    public static final ModConfigSpec.BooleanValue ENABLE_CUSTOM_MATERIALS;
    public static final ModConfigSpec.BooleanValue ENABLE_KNOWLEDGE_SYSTEM;
    public static final ModConfigSpec.IntValue ANVIL_HAMMER_COUNT;
    public static final ModConfigSpec.DoubleValue FORGE_HEAT_RATE;

    public static final ModConfigSpec.BooleanValue SHOW_TOOLTIPS;
    public static final ModConfigSpec.BooleanValue SHOW_QUALITY_INDICATORS;

    static {
        ModConfigSpec.Builder serverBuilder = new ModConfigSpec.Builder();

        serverBuilder.comment("Gameplay settings").push("gameplay");
        ENABLE_STAMINA = serverBuilder.comment("Enable the stamina system").define("enableStamina", true);
        ENABLE_CUSTOM_MATERIALS = serverBuilder.comment("Enable loading of custom material JSON files").define("enableCustomMaterials", true);
        ENABLE_KNOWLEDGE_SYSTEM = serverBuilder.comment("Enable the knowledge/research system").define("enableKnowledgeSystem", true);
        serverBuilder.pop();

        serverBuilder.comment("Crafting settings").push("crafting");
        ANVIL_HAMMER_COUNT = serverBuilder.comment("Number of hammer strikes required for anvil crafting").defineInRange("anvilHammerCount", 3, 1, 20);
        FORGE_HEAT_RATE = serverBuilder.comment("Rate at which forges heat items").defineInRange("forgeHeatRate", 1.0, 0.1, 10.0);
        serverBuilder.pop();

        SERVER_SPEC = serverBuilder.build();

        ModConfigSpec.Builder clientBuilder = new ModConfigSpec.Builder();

        clientBuilder.comment("Client-side display settings").push("client");
        SHOW_TOOLTIPS = clientBuilder.comment("Show MFR item tooltips").define("showTooltips", true);
        SHOW_QUALITY_INDICATORS = clientBuilder.comment("Show quality indicators on items").define("showQualityIndicators", true);
        clientBuilder.pop();

        CLIENT_SPEC = clientBuilder.build();
    }
}
