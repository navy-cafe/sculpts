package cafe.navy.sculpts.skin.stand;

import cafe.navy.bedrock.paper.entity.ClientEntity;
import cafe.navy.bedrock.paper.entity.NMSClientEntity;
import cafe.navy.bedrock.paper.exception.ClientEntityException;
import cafe.navy.bedrock.paper.message.Message;
import cafe.navy.bedrock.paper.target.EntityTarget;
import cafe.navy.bedrock.paper.util.NumberUtil;
import cafe.navy.sculpts.Sculpt;
import cafe.navy.sculpts.skin.Skin;
import com.google.gson.JsonObject;
import net.kyori.adventure.text.Component;
import net.minecraft.core.Rotations;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class StandSkin extends Skin {

    private final @NonNull NMSClientEntity entity;
    private final @NonNull ArmorStand armorStand;
    private @NonNull Rotations leftArm;
    private @NonNull Rotations rightArm;
    private @NonNull Rotations leftLeg;
    private @NonNull Rotations rightLeg;
    private @NonNull Rotations head;
    private @NonNull Rotations body;
    private boolean showArms;
    private boolean small;

    public StandSkin(final @NonNull Sculpt sculpt) {
        super("ARMOR_STAND", sculpt);
        this.leftArm = this.getRandomRotation();
        this.rightArm = this.getRandomRotation();
        this.leftLeg = this.getNoRotation();
        this.rightLeg = this.getNoRotation();
        this.head = this.getRandomRotation();
        this.body = this.getNoRotation();
        this.showArms = true;
        this.small = true;

        this.armorStand = new ArmorStand(
                ((CraftWorld) location().getWorld()).getHandle(),
                location().getX(),
                location().getY(),
                location().getZ()
        );

        this.entity = new NMSClientEntity(this.armorStand);

        try {
            this.addChild(this.entity);
        } catch (ClientEntityException e) {
            throw new RuntimeException(e);
        }

        this.applyEntityData();
    }

    @Override
    public @NonNull Component name() {
        return Message.create("Armor Stand").asComponent();
    }

    @Override
    public @NonNull List<Component> description() {
        return List.of(
                Message.create("An armor stand.").asComponent()
        );
    }

    @Override
    public double getHeight() {
        return 1.975;
    }

    @Override
    public void saveToJson(@NonNull JsonObject object) {

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
    protected boolean isEntityIdPresent(int i) {
        return false;
    }

    @Override
    public void teleport(@NonNull Location location) {
        this.entity.teleport(location);
    }

    public @NonNull Rotations rightArmRotation() {
        return this.rightArm;
    }

    public void rightArmRotation(final @NonNull Rotations rightArm) {
        this.rightArm = rightArm;
        this.applyEntityData();
    }

    public @NonNull Rotations leftArmRotation() {
        return this.rightArm;
    }

    public void leftArmRotation(final @NonNull Rotations leftArm) {
        this.leftArm = leftArm;
        this.applyEntityData();
    }

    public boolean small() {
        return this.small;
    }

    public void small(final boolean small) {
        this.small = small;
        this.applyEntityData();
    }

    private @NonNull Rotations getRandomRotation() {
        return new Rotations(NumberUtil.between(0, 360), NumberUtil.between(0, 360), NumberUtil.between(0, 360));
    }

    private @NonNull Rotations getNoRotation() {
        return new Rotations(0, 0, 0);
    }

    private void applyEntityData() {
        final var stand = (ArmorStand) this.entity.entity();
        stand.setShowArms(this.showArms);
        stand.setRightArmPose(this.rightArm);
        stand.setLeftArmPose(this.leftArm);

        stand.setRightLegPose(this.rightLeg);
        stand.setLeftLegPose(this.leftLeg);

        stand.setHeadPose(this.head);
        stand.setBodyPose(this.body);
        stand.setSmall(this.small);

        this.update();
    }

}
