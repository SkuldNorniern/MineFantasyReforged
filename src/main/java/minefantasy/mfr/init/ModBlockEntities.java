package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.tile.AnvilBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(net.minecraft.core.registries.Registries.BLOCK_ENTITY_TYPE, MineFantasyReforged.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AnvilBlockEntity>> ANVIL =
            BLOCK_ENTITIES.register("anvil", () -> new BlockEntityType<>(AnvilBlockEntity.factory(),
                    Set.of(ModBlocks.ANVIL_STONE.get(), ModBlocks.ANVIL_BRONZE.get(),
                            ModBlocks.ANVIL_IRON.get(), ModBlocks.ANVIL_STEEL.get())));

    // Further block entity registrations are added here as BlockEntity classes are ported.
    // See src/legacy/java/.../tile/ for the 1.12.2 TileEntity implementations to port.
}
