package ca.akeuben.adventuringsigns;

import ca.akeuben.adventuringsigns.text.TextTransformer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SignApplicator {

    public static Player decodePlayerData(MinecraftServer server, CompoundTag data) {
        return server.getPlayerList().getPlayer(UUID.fromString(data.getString("player").get()));
    }

    public static Optional<SignBlockEntity> decodeSignData(MinecraftServer server, CompoundTag data) {
        Level level = server.getLevel(ResourceKey.create(Registries.DIMENSION, Identifier.parse(data.getStringOr("dimension", "minecraft:overworld"))));
        if(level == null)
            return Optional.empty();

        BlockEntity blockEntity = level.getBlockEntity(new BlockPos(data.getIntOr("x", 0), data.getIntOr("y", 0), data.getIntOr("z", 0)));
        if(blockEntity == null)
            return Optional.empty();

        if(!blockEntity.is(BlockEntityTypes.SIGN) && !blockEntity.is(BlockEntityTypes.HANGING_SIGN))
            return Optional.empty();

        SignBlockEntity sign = (SignBlockEntity) blockEntity;

        return Optional.of(sign);
    }

    public static String[] decodeLines(CompoundTag data) {
        return new String[] {
                data.getStringOr("line0", ""),
                data.getStringOr("line1", ""),
                data.getStringOr("line2", ""),
                data.getStringOr("line3", ""),
        };
    }

    public static void applySign(Player player, SignBlockEntity sign, String[] rawLines) {
        List<Component> lines = List.of(
                TextTransformer.parseMiniMessage(rawLines[0]),
                TextTransformer.parseMiniMessage(rawLines[1]),
                TextTransformer.parseMiniMessage(rawLines[2]),
                TextTransformer.parseMiniMessage(rawLines[3])
        );
        sign.setText(new SignText(lines, lines, sign.getText(SignTextSlot.FRONT).getColor(), sign.getText(SignTextSlot.FRONT).hasGlowingText()), SignTextSlot.FRONT);
    }

    public static void applySign(MinecraftServer server, CompoundTag data) {
        Player player = decodePlayerData(server, data);
        SignBlockEntity sign = decodeSignData(server, data).get();
        String[] lines = decodeLines(data);

        applySign(player, sign, lines);
    }
}
