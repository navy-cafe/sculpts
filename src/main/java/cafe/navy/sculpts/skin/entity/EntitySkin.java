package cafe.navy.sculpts.skin.entity;

import cafe.navy.bedrock.paper.entity.NMSClientEntity;
import cafe.navy.bedrock.paper.exception.ClientEntityException;
import cafe.navy.bedrock.paper.target.EntityTarget;
import cafe.navy.sculpts.Sculpt;
import cafe.navy.sculpts.skin.Skin;
import org.bukkit.Location;
import org.checkerframework.checker.nullness.qual.NonNull;

public class EntitySkin extends Skin<EntitySkinData> {

    private final @NonNull NMSClientEntity entity;

    public double getHeight() {
        return this.entity.entity().getBbHeight();
    }

    public EntitySkin(final @NonNull Sculpt sculpt,
                      final @NonNull EntitySkinData data) {
        super(sculpt, data);
        this.entity = new NMSClientEntity(data.entity());
        try {
            this.addChild(this.entity);
        } catch (ClientEntityException e) {
            throw new RuntimeException(e);
        }
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
        this.entity.teleport(location);
    }
}
