package cafe.navy.sculpts;

import cafe.navy.bedrock.paper.entity.ClientEntity;
import cafe.navy.bedrock.paper.exception.ClientEntityException;
import cafe.navy.bedrock.paper.hologram.Hologram;
import cafe.navy.bedrock.paper.target.EntityTarget;
import org.bukkit.Location;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

public class Sculpt implements ClientEntity {

    private final @NonNull UUID uuid;
    private final @NonNull Location location;
    private final @NonNull Hologram hologram;

    public Sculpt(final @NonNull UUID uuid,
                  final @NonNull Location location,
                  final @NonNull Hologram hologram) {
        this.uuid = uuid;
        this.location = location;
        this.hologram = hologram;
    }

    public Sculpt(final @NonNull Location location,
                  final @NonNull Hologram hologram) {
        this.uuid = UUID.randomUUID();
        this.location = location;
        this.hologram = hologram;
    }

    @Override
    public @NonNull UUID uuid() {
        return this.uuid;
    }

    @Override
    public @NonNull Location location() {
        return this.location;
    }

    @Override
    public void add(final @NonNull EntityTarget target) throws ClientEntityException {
        this.hologram.add(target);
    }

    @Override
    public void remove(final @NonNull EntityTarget target) throws ClientEntityException {
        this.hologram.remove(target);
    }

    @Override
    public void update(@NonNull EntityTarget entityTarget) throws ClientEntityException {

    }
}
