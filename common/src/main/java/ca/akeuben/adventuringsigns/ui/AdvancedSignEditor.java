package ca.akeuben.adventuringsigns.ui;

import ca.akeuben.adventuringsigns.AdventuringSign;
import ca.akeuben.adventuringsigns.Constants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.dialog.*;
import net.minecraft.server.dialog.action.CustomAll;
import net.minecraft.server.dialog.input.TextInput;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Optional;

public class AdvancedSignEditor {
    public static Dialog advancedSignEditor(AdventuringSign sign, Player player) {
        List<String> original = List.of(sign.adventuringsigns$getLine(0), sign.adventuringsigns$getLine(1), sign.adventuringsigns$getLine(2), sign.adventuringsigns$getLine(3));
        List<Input> inputs = List.of(
                new Input(
                        "line0",
                        new TextInput(
                                200,
                                Component.literal("Line 1"),
                                true,
                                original.getFirst(),
                                256,
                                Optional.empty()
                        )
                ),
                new Input(
                        "line1",
                        new TextInput(
                                200,
                                Component.literal("Line 2"),
                                true,
                                original.get(1),
                                256,
                                Optional.empty()
                        )
                ),
                new Input(
                        "line2",
                        new TextInput(
                                200,
                                Component.literal("Line 3"),
                                true,
                                original.get(2),
                                256,
                                Optional.empty()
                        )
                ),
                new Input(
                        "line3",
                        new TextInput(
                                200,
                                Component.literal("Line 4"),
                                true,
                                original.get(3),
                                256,
                                Optional.empty()
                        )
                )
        );

        CommonDialogData common = new CommonDialogData(
                Component.literal("Edit Sign Contents"),
                Optional.empty(),
                true,
                false,
                DialogAction.CLOSE,
                List.of(),
                inputs
        );

        CompoundTag additionalData = new CompoundTag();
        additionalData.putString("player", player.getStringUUID());
        assert sign.getLevel() != null;
        additionalData.putString("dimension", sign.getLevel().dimension().identifier().toString());
        additionalData.putInt("x", sign.getBlockPos().getX());
        additionalData.putInt("y", sign.getBlockPos().getY());
        additionalData.putInt("z", sign.getBlockPos().getZ());

        ActionButton submit = new ActionButton(
                new CommonButtonData(
                        Component.literal("Submit"),
                        150
                ),
                Optional.of(new CustomAll(
                        Identifier.fromNamespaceAndPath(Constants.MOD_ID, Constants.SIGN_EDITOR_PATH + "/" + "submit"),
                        Optional.of(additionalData)
                ))
        );

        return new MultiActionDialog(
                common,
                List.of(submit),
                Optional.empty(),
                1
        );
    }
}
