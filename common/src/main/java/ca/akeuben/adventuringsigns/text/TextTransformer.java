package ca.akeuben.adventuringsigns.text;

import ca.akeuben.adventuringsigns.platform.Services;
import net.kyori.adventure.platform.modcommon.MinecraftServerAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;

import java.util.regex.Pattern;

public final class TextTransformer {

    private static final Pattern WHITE_SPECIAL_TAGS =
            Pattern.compile("<(sprite|head)(\\b[^>]*?)(/?)>");

    private static final MiniMessage mm = MiniMessage.builder(MiniMessage.Preset.DEFAULT)
            .preProcessor(input -> WHITE_SPECIAL_TAGS.matcher(input)
                    .replaceAll("<white><$1$2$3></white>"))
            .build();

    public static Component parseMiniMessage(String message) {
        return toNative(mm.deserialize(message));
    }

    public static String encodeMiniMessage(Component component) {
        return mm.serialize(toAdventure(component));
    }

    public static Component toNative(net.kyori.adventure.text.Component component) {
        MinecraftServer server = Services.PLATFORM.getMinecraftServerInstance();
        if(server == null)
            throw new RuntimeException("Tried to convert to native component while not connected to a Minecraft server!");
        return MinecraftServerAudiences.of(server).asNative(component);
    }

    public static net.kyori.adventure.text.Component toAdventure(Component component) {
        MinecraftServer server = Services.PLATFORM.getMinecraftServerInstance();
        if(server == null)
            throw new RuntimeException("Tried to convert to native component while not connected to a Minecraft server!");
        return MinecraftServerAudiences.of(server).asAdventure(component);
    }
}
