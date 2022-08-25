package cafe.navy.sculpts.tools;

import broccolai.corn.paper.item.PaperItemBuilder;
import cafe.navy.bedrock.paper.message.Colours;
import cafe.navy.sculpts.Sculpt;
import cafe.navy.sculpts.skin.Skin;
import cafe.navy.sculpts.skin.stand.StandSkin;
import cafe.navy.sculpts.user.SculptUser;
import net.kyori.adventure.text.Component;
import net.minecraft.core.Rotations;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Optional;

public class RotateToolType implements ToolType {
    @Override
    public @NonNull String id() {
        return "rotate";
    }

    @Override
    public @NonNull PaperItemBuilder newItemBuilder() {
        return PaperItemBuilder
                .ofType(Material.STICK)
                .name(Component.text("Rotate", Colours.Light.BLUE));
    }

    @Override
    public void handleInteract(@NonNull Player user, @NonNull Sculpt sculpt) {
        System.out.println("handling rotate interact");
        final Optional<Skin> skinOpt = sculpt.skin();
        if (skinOpt.isEmpty()) {
            System.out.println("not present");
            return;
        }

        final Skin skin = skinOpt.get();

        if (!(skin instanceof StandSkin stand)) {
            System.out.println("not stand");
            return;
        }

        final Rotations rightArm = stand.rightArmRotation();
        final Rotations newRightArm = new Rotations(
                rightArm.getWrappedX(),
                rightArm.getWrappedY() + 10,
                rightArm.getWrappedZ()
        );
        System.out.println("Set stand rotation to "+newRightArm);
        stand.rightArmRotation(newRightArm);
    }
}
