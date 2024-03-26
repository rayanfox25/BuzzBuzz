package rayan.buzzblocks.util.D20;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import rayan.buzzblocks.BuzzBlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GivePositiveRandomEffect {
    private final World world;
    private final ServerPlayerEntity Splayer;

    public GivePositiveRandomEffect(World world, ServerPlayerEntity splayer) {
        this.world = world;
        this.Splayer = splayer;
    }

    public void GEffect() {
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
        sendMessageToPlayers("rolled a 5");
    }

    // Helper method to get all the status effects you want to consider
    private List<StatusEffect> getAllStatusEffects() {
        List<StatusEffect> effects = new ArrayList<>();
        effects.add(StatusEffects.SPEED);
        effects.add(StatusEffects.STRENGTH);
        effects.add(StatusEffects.JUMP_BOOST);
        effects.add(StatusEffects.REGENERATION);
        effects.add(StatusEffects.RESISTANCE);
        effects.add(StatusEffects.FIRE_RESISTANCE);
        effects.add(StatusEffects.ABSORPTION);
        effects.add(StatusEffects.HASTE);
        effects.add(StatusEffects.HEALTH_BOOST);
        effects.add(StatusEffects.INSTANT_HEALTH);
        effects.add(StatusEffects.HERO_OF_THE_VILLAGE);
        effects.add(StatusEffects.LUCK);
        return effects;
    }
    private void sendMessageToPlayers(String message) {
        // Send message to all players in the world
        for (PlayerEntity player : world.getPlayers()) {
            player.sendMessage(Text.of(message), false);
        }
    }
}