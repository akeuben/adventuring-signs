package ca.akeuben.adventuringsigns.text;

import ca.akeuben.adventuringsigns.platform.Services;
import net.kyori.adventure.platform.modcommon.MinecraftServerAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;

public final class TextTransformer {

    private static final MiniMessage mm = MiniMessage.miniMessage();

    public static Component parseMiniMessage(String message) {
        return toNative(mm.deserialize(message));
    }

    public static Component toNative(net.kyori.adventure.text.Component component) {
        MinecraftServer server = Services.PLATFORM.getMinecraftServerInstance();
        if(server == null)
            throw new RuntimeException("Tried to convert to native component while not connected to a Minecraft server!");
        return MinecraftServerAudiences.of(server).asNative(component);
    }
}
