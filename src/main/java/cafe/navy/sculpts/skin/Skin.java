package cafe.navy.sculpts.skin;

import cafe.navy.bedrock.paper.entity.ClientEntity;
import cafe.navy.sculpts.Sculpt;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Optional;

public abstract class Skin<T> extends ClientEntity {

    private final @NonNull Sculpt sculpt;
    private final @NonNull T data;

    public Skin(final @NonNull Sculpt sculpt,
                final @NonNull T data) {
        super(sculpt.uuid(), sculpt.location());
        this.sculpt = sculpt;
        this.data = data;
    }

    @Override
    public @NonNull Optional<@NonNull ClientEntity> parent() {
        return Optional.of(this.sculpt);
    }

    public abstract double getHeight();

}
