package rayan.buzzblocks.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class PlayerMessageUtility {
    public static void sendMessageToPlayers(World world, String message) {
        // Send message to all players in the world
        for (PlayerEntity player : world.getPlayers()) {
            player.sendMessage(Text.of(message), false);
        }
    }
}