package cafe.navy.sculpts.skin;

import cafe.navy.bedrock.paper.entity.ClientEntity;
import cafe.navy.sculpts.Sculpt;
import com.google.gson.JsonObject;
import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public abstract class Skin extends ClientEntity {

    protected final @NonNull String id;
    protected final @NonNull Sculpt sculpt;

    public Skin(final @NonNull String id,
                final @NonNull Sculpt sculpt) {
        super(sculpt.uuid(), sculpt.location());
        this.id = id;
        this.sculpt = sculpt;
    }

    public @NonNull String id() {
        return this.id;
    }

    public abstract @NonNull Component name();

    public abstract @NonNull List<Component> description();

    public @NonNull Sculpt sculpt() {
        return this.sculpt;
    }

    public abstract double getHeight();

    public abstract void saveToJson(final @NonNull JsonObject object);

}
