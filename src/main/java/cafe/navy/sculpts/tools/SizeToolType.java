package cafe.navy.sculpts.tools;

import broccolai.corn.paper.item.PaperItemBuilder;
import cafe.navy.bedrock.paper.message.Colours;
import cafe.navy.sculpts.Sculpt;
import cafe.navy.sculpts.skin.Skin;
import cafe.navy.sculpts.skin.stand.StandSkin;
import net.kyori.adventure.text.Component;
import net.minecraft.core.Rotations;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Optional;

public class SizeToolType implements ToolType {
    @Override
    public @NonNull String id() {
        return "size";
    }

    @Override
    public @NonNull PaperItemBuilder newItemBuilder() {
        return PaperItemBuilder
                .ofType(Material.STICK)
                .name(Component.text("Size", Colours.Light.BLUE));
    }

    @Override
    public void handleInteract(@NonNull Player user, @NonNull Sculpt sculpt) {
        final Optional<Skin> skinOpt = sculpt.skin();
        if (skinOpt.isEmpty()) {
            return;
        }

        final Skin skin = skinOpt.get();

        if (!(skin instanceof StandSkin stand)) {
            return;
        }

        stand.small(!stand.small());
    }
}
