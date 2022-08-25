package cafe.navy.sculpts.skin.entity;

import cafe.navy.bedrock.paper.entity.NMSClientEntity;
import cafe.navy.bedrock.paper.exception.ClientEntityException;
import cafe.navy.bedrock.paper.message.Message;
import cafe.navy.bedrock.paper.target.EntityTarget;
import cafe.navy.sculpts.Sculpt;
import cafe.navy.sculpts.skin.Skin;
import com.google.gson.JsonObject;
import io.papermc.paper.adventure.PaperAdventure;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class EntitySkin extends Skin {

    private final @NonNull NMSClientEntity entity;

    @Override
    public @NonNull Component name() {
        return Message.create()
                .text("Entity: ")
                .highlight(PaperAdventure.asAdventure(entity.entity().getName()))
                .asComponent();
    }

    @Override
    public @NonNull List<Component> description() {
        return List.of(
                Message.create("blah blah blah fuck you").asComponent()
        );
    }

    public double getHeight() {
        return this.entity.entity().getBbHeight();
    }

    @Override
    public void saveToJson(@NonNull JsonObject object) {

    }

    public EntitySkin(final @NonNull Sculpt sculpt,
                      final @NonNull EntitySkinData data) {
        super("ENTITY", sculpt);
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
