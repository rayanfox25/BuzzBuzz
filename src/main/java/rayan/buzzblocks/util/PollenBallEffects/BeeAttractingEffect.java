package rayan.buzzblocks.util.PollenBallEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class BeeAttractingEffect extends StatusEffect {
    public BeeAttractingEffect() {
        super(StatusEffectCategory.NEUTRAL, 0xFFA7A7); // Set category and color
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof ServerPlayerEntity) {
            World world = entity.getEntityWorld();
            // Iterate over all nearby entities
            world.getEntitiesByClass(BeeEntity.class, entity.getBoundingBox().expand(8.0, 8.0, 8.0), bee -> true)
                    .forEach(bee -> {
                        // Apply a force to attract the bee towards the player
                        // Adjust the force as needed
                        bee.getNavigation().startMovingTo(entity, 2.0);
                    });
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // This method determines whether the effect should be applied in the next tick
        // Return true if the effect should continue applying, false otherwise
        return true;
    }
}