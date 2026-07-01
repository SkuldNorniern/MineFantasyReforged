package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(net.minecraft.core.registries.Registries.BLOCK_ENTITY_TYPE, MineFantasyReforged.MOD_ID);

    // Block entity registrations are added here as BlockEntity classes are ported.
    // See src/legacy/java/.../tile/ for the 1.12.2 TileEntity implementations to port.

    // Example (uncomment and implement when TileEntityAnvil is ported):
    // public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AnvilBlockEntity>> ANVIL =
    //     BLOCK_ENTITIES.register("anvil", () -> BlockEntityType.Builder
    //         .of(AnvilBlockEntity::new, ModBlocks.ANVIL_IRON.get(), ModBlocks.ANVIL_STEEL.get())
    //         .build(null));
}
