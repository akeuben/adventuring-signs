package ca.akeuben.adventuringsigns;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.EventBus;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.CustomClickActionEvent;

@Mod(Constants.MOD_ID)
public class AdventuringSignsNeoforge {

    @SubscribeEvent()
    public static void onCustomClick(CustomClickActionEvent event) {
        if(event.getIdentifier().equals(Identifier.fromNamespaceAndPath(Constants.MOD_ID, Constants.SIGN_EDITOR_PATH + "/submit"))) {
            assert event.getPlayer() != null;
            MinecraftServer server = event.getPlayer().level().getServer();
            assert event.getPayload() != null;
            CompoundTag data = event.getPayload().asCompound().get();

            SignApplicator.applySign(server, data);

            event.setCanceled(true);
        }
    }

    public AdventuringSignsNeoforge(IEventBus eventBus) {
        NeoForge.EVENT_BUS.addListener(AdventuringSignsNeoforge::onCustomClick);
        Constants.LOG.info("Initialized Adventuring Signs for Neoforge!");
    }
}