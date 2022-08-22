package cafe.navy.sculpts.skin.stand;

import net.minecraft.core.Rotations;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.checkerframework.checker.nullness.qual.NonNull;

public class StandData {

    private final @NonNull ArmorStand stand;

    public StandData(final @NonNull ArmorStand stand) {
        this.stand = stand;
        this.stand.setShowArms(true);
        this.stand.setLeftArmPose(Rotations.createWithoutValidityChecks(
                (float) Math.random() * 360,
                (float) Math.random() * 360,
                (float) Math.random() * 360
        ));

        this.stand.setRightArmPose(Rotations.createWithoutValidityChecks(
                (float) Math.random() * 360,
                (float) Math.random() * 360,
                (float) Math.random() * 360
        ));


        this.stand.setLeftLegPose(Rotations.createWithoutValidityChecks(
                (float) Math.random() * 360,
                (float) Math.random() * 360,
                (float) Math.random() * 360
        ));

        this.stand.setRightLegPose(Rotations.createWithoutValidityChecks(
                (float) Math.random() * 360,
                (float) Math.random() * 360,
                (float) Math.random() * 360
        ));


        this.stand.setHeadPose(Rotations.createWithoutValidityChecks(
                (float) Math.random() * 360,
                (float) Math.random() * 360,
                (float) Math.random() * 360
        ));


        this.stand.setHeadPose(Rotations.createWithoutValidityChecks(
                (float) Math.random() * 360,
                (float) Math.random() * 360,
                (float) Math.random() * 360
        ));
    }

    public @NonNull ArmorStand entity() {
        return this.stand;
    }

}
