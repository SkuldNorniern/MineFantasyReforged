package minefantasy.mfr.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

public class MFREventHandler {
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        // Port stamina, knowledge, and player data logic from src/legacy/java/.../data/PlayerData
    }

    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.Load event) {
        // Port world load logic
    }
}
