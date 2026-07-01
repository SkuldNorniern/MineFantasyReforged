package minefantasy.mfr.constants;

import net.minecraft.network.chat.Component;

public enum Tool {
    HANDS("hands", false),
    OTHER("other", false),
    BRUSH("brush", false),
    HAMMER("hammer", true),
    HEAVY_HAMMER("heavy_hammer", true),
    NEEDLE("needle", true),
    KNIFE("knife", true),
    MALLET("mallet", true),
    SHEARS("shears", true),
    SAW("saw", true),
    SPANNER("spanner", true),
    SPOON("spoon", true),
    WASH("wash", false);

    private final String unlocalizedName;
    private final boolean hasTiers;

    Tool(String name, boolean hasTiers) {
        this.unlocalizedName = name;
        this.hasTiers = hasTiers;
    }

    public String getName() {
        return unlocalizedName;
    }

    public boolean hasTiers() {
        return hasTiers;
    }

    public static Tool fromName(String name) {
        for (Tool tool : values()) {
            if (tool.unlocalizedName.equals(name)) return tool;
        }
        throw new IllegalArgumentException("No such tool: " + name);
    }

    public Component getDisplayName() {
        return Component.translatable("tooltype." + unlocalizedName);
    }
}
