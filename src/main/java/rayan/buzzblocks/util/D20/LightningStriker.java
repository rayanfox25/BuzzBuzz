package rayan.buzzblocks.util.D20;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class LightningStriker {
    public static void strikePlayer(PlayerEntity player, World world) {
        // Strike the player with lightning
        LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
        lightning.refreshPositionAndAngles(player.getX(), player.getY(), player.getZ(), player.getYaw(), player.getPitch());
        world.spawnEntity(lightning);
    }
}