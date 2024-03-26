package rayan.buzzblocks.util.D20;

import net.minecraft.block.Blocks;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TNTTrap {
    private static final int RADIUS = 2; // Radius of the cylinder

    public static void triggerTrap(World world, PlayerEntity player) {
        BlockPos playerPos = player.getBlockPos();

        // Place iron bars around the player in a cylinder shape with a solid stone brick base
        placeIronBars(world, playerPos);

        // Apply Slowness effect to the player to prevent movement
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 40, 100));

        // Spawn primed TNT near the player with a fuse of 5 seconds
        BlockPos trapCenter = new BlockPos((int) player.getX(), (int) player.getY(), (int) player.getZ());
        spawnPrimedTNT(world, trapCenter);
        spawnPrimedTNT(world, trapCenter);
        spawnPrimedTNT(world, trapCenter);
        spawnPrimedTNT(world, trapCenter);
        spawnPrimedTNT(world, trapCenter);
        spawnPrimedTNT(world, trapCenter);

        // Trap the player within the cylinder
        trapPlayer(world, player, trapCenter);
    }

    private static void placeIronBars(World world, BlockPos center) {
        int startY = center.getY() - 1; // Start placing bars from one block below the player
        int endY = startY + 3; // Extend bars upward by 3 blocks
        BlockPos basePos = center.down(); // Position for the base

        // Place stone bricks as the base
        for (int xOffset = -RADIUS; xOffset <= RADIUS; xOffset++) {
            for (int zOffset = -RADIUS; zOffset <= RADIUS; zOffset++) {
                BlockPos pos = basePos.add(xOffset, 0, zOffset);
                world.setBlockState(pos, Blocks.STONE_BRICKS.getDefaultState());
            }
        }

        // Place iron bars around the player
        for (int yOffset = startY; yOffset < endY; yOffset++) {
            for (int xOffset = -RADIUS; xOffset <= RADIUS; xOffset++) {
                for (int zOffset = -RADIUS; zOffset <= RADIUS; zOffset++) {
                    BlockPos pos = center.add(xOffset, yOffset, zOffset);
                    // Check if the block is at the same Y-level as the center
                    if (world.getBlockState(pos).isAir() || world.getBlockState(pos).getBlock() == Blocks.WATER) {
                        if (yOffset == startY || yOffset == endY - 1 || xOffset == -RADIUS || xOffset == RADIUS || zOffset == -RADIUS || zOffset == RADIUS) {
                            world.setBlockState(pos, Blocks.IRON_BARS.getDefaultState());
                        }
                    }
                }
            }
        }

        // Place stone bricks at the top and bottom layers
        for (int xOffset = -RADIUS; xOffset <= RADIUS; xOffset++) {
            for (int zOffset = -RADIUS; zOffset <= RADIUS; zOffset++) {
                BlockPos topPos = center.add(xOffset, endY - 1, zOffset);
                BlockPos bottomPos = center.add(xOffset, startY, zOffset);
                world.setBlockState(topPos, Blocks.STONE_BRICKS.getDefaultState());
                world.setBlockState(bottomPos, Blocks.STONE_BRICKS.getDefaultState());
            }
        }
    }

    private static void trapPlayer(World world, PlayerEntity player, BlockPos trapCenter) {
        // No further action needed since the Slowness effect is applied to prevent movement
    }

    private static void spawnPrimedTNT(World world, BlockPos pos) {
        TntEntity tnt = new TntEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, null);
        tnt.setFuse(20); // 20 ticks per second, so 100 ticks = 5 seconds
        world.spawnEntity(tnt);
    }
}