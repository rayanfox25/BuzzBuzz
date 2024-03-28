package rayan.buzzblocks.util.D20;

import net.minecraft.block.Blocks;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class TNTTrap {
    private static final int RADIUS = 2; // Radius of the cylinder

    public static void triggerTrap(World world, @NotNull PlayerEntity player) {
        BlockPos playerPos = player.getBlockPos();

        // Place iron bars around the player in a cylinder shape with a solid stone brick base
        placeIronBars(world, playerPos);
        // Apply Slowness effect to the player to prevent movement
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 100, 100));

        // Spawn primed TNT near the player with a fuse of 5 seconds
        BlockPos trapCenter = new BlockPos((int) player.getX(), (int) player.getY(), (int) player.getZ());
        spawnPrimedTNT(world, trapCenter);
        spawnPrimedTNT(world, trapCenter);
        spawnPrimedTNT(world, trapCenter);
        spawnPrimedTNT(world, trapCenter);
        spawnPrimedTNT(world, trapCenter);
        spawnPrimedTNT(world, trapCenter);
        spawnPrimedTNT(world, trapCenter);
        spawnPrimedTNT(world, trapCenter);
        spawnPrimedTNT(world, trapCenter);
        spawnPrimedTNT(world, trapCenter);
        spawnPrimedTNT(world, trapCenter);
        spawnPrimedTNT(world, trapCenter);
        spawnPrimedTNT(world, trapCenter);
        spawnPrimedTNT(world, trapCenter);
        spawnPrimedTNT(world, trapCenter);
        spawnPrimedTNT(world, trapCenter);
        spawnPrimedTNT(world, trapCenter);
        spawnPrimedTNT(world, trapCenter);
        spawnPrimedTNT(world, trapCenter);


    }
    private static void placeIronBars(World world, BlockPos playerPos) {
        // Define the center position for the cage
        int centerX = playerPos.getX();
        int centerY = playerPos.getY() + 1; // Adjust to be at the player's feet level
        int centerZ = playerPos.getZ();

        int startY = centerY; // Y position of the bottom layer of the cage
        int endY = startY + 4; // Extend the cage upward by 4 blocks

        // Place iron bars around the player's feet
        for (int yOffset = startY; yOffset <= endY; yOffset++) {
            for (int xOffset = -RADIUS; xOffset <= RADIUS; xOffset++) {
                for (int zOffset = -RADIUS; zOffset <= RADIUS; zOffset++) {
                    BlockPos pos = new BlockPos(centerX + xOffset, yOffset, centerZ + zOffset);
                    // Place iron bars if at the edge or at the same level as the player's feet
                    if (Math.abs(xOffset) == RADIUS || Math.abs(zOffset) == RADIUS || yOffset == startY) {
                        world.setBlockState(pos, Blocks.IRON_BARS.getDefaultState());
                    }
                }
            }
        }

        // Place stone bricks at the top and bottom layers of the cage
        BlockPos bottomBase = new BlockPos(centerX - RADIUS, startY - 1, centerZ - RADIUS);
        BlockPos topBase = new BlockPos(centerX - RADIUS, endY, centerZ - RADIUS);

        // Fill in the base with stone bricks
        for (int x = 0; x <= 2 * RADIUS; x++) {
            for (int z = 0; z <= 2 * RADIUS; z++) {
                world.setBlockState(bottomBase.add(x, 0, z), Blocks.STONE_BRICKS.getDefaultState());
                world.setBlockState(topBase.add(x, 0, z), Blocks.STONE_BRICKS.getDefaultState());
            }
        }

        // Teleport the player into the cage
        PlayerEntity player = world.getClosestPlayer(centerX, centerY, centerZ, 2, false);
        if (player != null) {
            player.teleport(centerX, centerY + 1, centerZ);
        }
    }
        private static void spawnPrimedTNT (World world, @NotNull BlockPos pos){
            TntEntity tnt = new TntEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, null);
            tnt.setFuse(100); // 20 ticks per second, so 100 ticks = 5 seconds
            world.spawnEntity(tnt);

        }

    }
