package rayan.buzzblocks.util.D20;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import rayan.buzzblocks.BuzzBlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GiveNegtiveRandomEffect {
    private final World world;
    private final ServerPlayerEntity Splayer;

    public GiveNegtiveRandomEffect(World world, ServerPlayerEntity splayer) {
        this.world = world;
        this.Splayer = splayer;
    }

    public void NEffect() {
        List<StatusEffect> effects = getAllStatusEffects();

        // Select a random effect from the list
        Random random = new Random();
        StatusEffect effect = effects.get(random.nextInt(effects.size()));

        // Modify duration and amplifier as needed
        int duration = 20 * (10 + random.nextInt(30)); // Random duration between 10 to 40 seconds
        int amplifier = random.nextInt(2); // Random amplifier between 0 and 1

        // Apply the random effect to the player
        if (Splayer != null) {
            Splayer.addStatusEffect(new StatusEffectInstance(effect, duration, amplifier));
        } else {
            BuzzBlocks.LOGGER.error("Player is null. Cannot apply status effect.");
        }
    }

    // Helper method to get all the status effects you want to consider
    private List<StatusEffect> getAllStatusEffects() {
        List<StatusEffect> effects = new ArrayList<>();
        effects.add(StatusEffects.WEAKNESS);
        effects.add(StatusEffects.MINING_FATIGUE);
        effects.add(StatusEffects.SLOWNESS);
        effects.add(StatusEffects.BLINDNESS);
        effects.add(StatusEffects.HUNGER);
        effects.add(StatusEffects.POISON);
        effects.add(StatusEffects.WITHER);
        effects.add(StatusEffects.UNLUCK);
        effects.add(StatusEffects.BAD_OMEN);
        effects.add(StatusEffects.DARKNESS);
        effects.add(StatusEffects.LEVITATION);
        // Add more negative effects as needed
        return effects;
    }
}