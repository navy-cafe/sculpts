package cafe.navy.sculpts.tools;

import broccolai.corn.paper.item.PaperItemBuilder;
import cafe.navy.sculpts.Sculpt;
import cafe.navy.sculpts.SculptEntity;
import cafe.navy.sculpts.user.SculptUser;
import org.bukkit.Material;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface ToolType {

    @NonNull String id();

    @NonNull PaperItemBuilder newItemBuilder();

    void handleInteract(final @NonNull Player user,
                        final @NonNull Sculpt sculpt);

}
