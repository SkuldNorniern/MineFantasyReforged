package minefantasy.mfr.constants;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Rarity;

public enum MFRRarity {
    POOR(ChatFormatting.DARK_GRAY, -1, Rarity.COMMON),
    COMMON(ChatFormatting.WHITE, 0, Rarity.COMMON),
    UNCOMMON(ChatFormatting.YELLOW, 1, Rarity.UNCOMMON),
    RARE(ChatFormatting.AQUA, 2, Rarity.RARE),
    EPIC(ChatFormatting.LIGHT_PURPLE, 3, Rarity.EPIC),
    UNIQUE(ChatFormatting.DARK_GREEN, 4, Rarity.EPIC);

    public final ChatFormatting color;
    public final int value;
    private final Rarity mcRarity;

    MFRRarity(ChatFormatting color, int value, Rarity mcRarity) {
        this.color = color;
        this.value = value;
        this.mcRarity = mcRarity;
    }

    public Rarity toMCRarity() {
        return mcRarity;
    }

    public static MFRRarity fromValue(int value) {
        for (MFRRarity r : values()) {
            if (r.value == value) return r;
        }
        return POOR;
    }

    public static MFRRarity fromEnchanted(MFRRarity base, boolean enchanted) {
        if (!enchanted) return base;
        int next = base.value + (base.value == 0 ? 2 : 1);
        if (next >= values().length) next = values().length - 1;
        return fromValue(next);
    }
}
