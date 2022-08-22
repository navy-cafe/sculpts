package cafe.navy.sculpts.skin;

import cafe.navy.sculpts.Sculpt;
import com.sun.jna.platform.win32.WinDef;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.LightningStrike;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public abstract class SkinType<T> {

    public abstract @NonNull Component name();

    public abstract @NonNull String id();

    public abstract @NonNull List<Component> description();

    public abstract void apply(final @NonNull Sculpt sculpt,
                               final @NonNull T data);

    public void apply(final @NonNull Sculpt sculpt) {
        this.apply(sculpt, this.createDefaultData(sculpt.location()));
    }


    public abstract @NonNull T createDefaultData(final @NonNull Location location);
}
