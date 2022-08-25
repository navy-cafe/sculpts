package cafe.navy.sculpts;

import cafe.navy.bedrock.paper.entity.ClientEntity;
import cafe.navy.bedrock.paper.exception.ClientEntityException;
import cafe.navy.bedrock.paper.hologram.Hologram;
import cafe.navy.bedrock.paper.message.Message;
import cafe.navy.bedrock.paper.target.EntityTarget;
import cafe.navy.sculpts.render.SculptHitbox;
import cafe.navy.sculpts.skin.Skin;
import cafe.navy.sculpts.skin.stand.StandSkin;
import org.bukkit.Location;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;

public class SculptEntity extends ClientEntity {

    private final @NonNull Sculpt sculpt;
    private final @NonNull SculptHitbox hitbox;
    private final @NonNull Hologram hologram;
    private @NonNull Skin skin;

    public SculptEntity(final @NonNull Sculpt sculpt) {
        super(sculpt.uuid(), sculpt.location());
        this.sculpt = sculpt;
        this.skin = this.sculpt.skin().orElse(new StandSkin(this.sculpt));
        this.hologram = new Hologram(this.location(), sculpt.hologramLines().stream().map(Message::create).toList());
        this.hitbox = new SculptHitbox(this.sculpt);

        try {
            this.addChild(this.skin);
            this.addChild(this.hologram);
            this.addChild(this.hitbox);
        } catch (ClientEntityException e) {
            throw new RuntimeException(e);
        }
    }

    public @NonNull Sculpt sculpt() {
        return this.sculpt;
    }

    @Override
    protected void show(@NonNull EntityTarget entityTarget) throws ClientEntityException {

    }

    @Override
    protected void hide(@NonNull EntityTarget entityTarget) throws ClientEntityException {

    }

    @Override
    protected void update(@NonNull EntityTarget entityTarget) throws ClientEntityException {

    }

    @Override
    protected boolean isEntityIdPresent(int i) {
        return false;
    }

    @Override
    public void teleport(@NonNull Location location) {

    }

    public void setSkin(final @NonNull Skin skin) {
        Objects.requireNonNull(skin);
        try {
            this.skin.remove();
            this.removeChild(this.skin);
            this.skin = skin;
            this.addChild(this.skin);
        } catch (ClientEntityException e) {
            throw new RuntimeException(e);
        }
    }

}
