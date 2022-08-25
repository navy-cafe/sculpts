package cafe.navy.sculpts.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Optional;
import java.util.UUID;

public class SculptUser {

    private final @NonNull UUID uuid;

    public SculptUser(final @NonNull UUID uuid) {
        this.uuid = uuid;
    }

    public @NonNull Optional<@NonNull Player> getPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(this.uuid));
    }

}
