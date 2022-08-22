package cafe.navy.sculpts.render;

import cafe.navy.bedrock.paper.entity.ClientEntity;
import cafe.navy.bedrock.paper.entity.Entities;
import cafe.navy.bedrock.paper.exception.ClientEntityException;
import cafe.navy.bedrock.paper.target.EntityTarget;
import cafe.navy.sculpts.Sculpt;
import org.bukkit.Location;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SculptHitbox extends ClientEntity {

    private final @NonNull Sculpt sculpt;
    private final @NonNull List<ClientEntity> hitboxes;

    public SculptHitbox(final @NonNull Sculpt sculpt) {
        super(sculpt.uuid(), sculpt.location());
        this.sculpt = sculpt;
        this.hitboxes = new ArrayList<>();
        this.hitboxes.add(Entities.createHitbox(sculpt.location(), 1));
        this.hitboxes.add(Entities.createHitbox(sculpt.location().add(0, 0.51, 0), 1));
        this.hitboxes.add(Entities.createHitbox(sculpt.location().add(0, 1.02, 0), 1));

        for (final ClientEntity hitbox : this.hitboxes) {
            try {
                this.addChild(hitbox);
                System.out.println("added hitbox child");
            } catch (ClientEntityException e) {
                throw new RuntimeException(e);
            }
        };
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
        double i = 0;
        for (final ClientEntity entity : this.hitboxes) {
            entity.teleport(location.clone().add(0, i * 0.52, 0));
        }
    }

}