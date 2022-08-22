package cafe.navy.sculpts.skin.stand;

import cafe.navy.bedrock.paper.message.Colours;
import cafe.navy.bedrock.paper.message.Message;
import cafe.navy.sculpts.Sculpt;
import cafe.navy.sculpts.skin.SkinType;
import net.kyori.adventure.text.Component;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class StandSkinType extends SkinType<StandData> {
    @Override
    public @NonNull Component name() {
        return Component.text("Armor Stand", Colours.Light.PINK);
    }

    @Override
    public @NonNull String id() {
        return "stand";
    }

    @Override
    public @NonNull List<Component> description() {
        return List.of(
                Message.create("A configurable armour stand").asComponent()
        );
    }


    @Override
    public void apply(@NonNull Sculpt sculpt, @NonNull StandData data) {
        sculpt.setSkin(new StandSkin(sculpt, data));
    }

    @Override
    public @NonNull StandData createDefaultData(final @NonNull Location location) {
        final ArmorStand stand = new ArmorStand(
                ((CraftWorld) location.getWorld()).getHandle(),
                location.getX(),
                location.getY(),
                location.getZ()
        );

        final StandData standData = new StandData(stand);
        return standData;
    }
}
