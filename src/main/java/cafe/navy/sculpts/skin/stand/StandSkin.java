package cafe.navy.sculpts.skin.stand;

import cafe.navy.bedrock.paper.entity.ClientEntity;
import cafe.navy.bedrock.paper.entity.NMSClientEntity;
import cafe.navy.bedrock.paper.exception.ClientEntityException;
import cafe.navy.bedrock.paper.target.EntityTarget;
import cafe.navy.sculpts.Sculpt;
import cafe.navy.sculpts.skin.Skin;
import org.bukkit.Location;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class StandSkin extends Skin<StandData> {

    private final @NonNull Sculpt sculpt;
    private final @NonNull NMSClientEntity entity;

    public StandSkin(final @NonNull Sculpt sculpt,
                     final @NonNull StandData data) {
        super(sculpt, data);
        this.sculpt = sculpt;
        this.entity = new NMSClientEntity(data.entity());
    }

    @Override
    public double getHeight() {
        return 1.975;
    }

    @Override
    protected void show(final @NonNull EntityTarget target) throws ClientEntityException {

    }

    @Override
    protected void hide(final @NonNull EntityTarget target) throws ClientEntityException {

    }

    @Override
    protected void update(final @NonNull EntityTarget target) throws ClientEntityException {

    }

    @Override
    public @NonNull List<ClientEntity> children() {
        return List.of(this.entity);
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
