package rayan.buzzblocks.util.D20;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class dropRandomFlowers {
    private final World world;
    private final BlockPos pos;
    private final int count;

    public dropRandomFlowers(World world, BlockPos pos, int count) {
        this.world = world;
        this.pos = pos;
        this.count = count;
    }

    public void flowers() {
        // Define an array of flower blocks
        Block[] flowerBlocks = {Blocks.POPPY, Blocks.BLUE_ORCHID, Blocks.ALLIUM, Blocks.AZURE_BLUET, Blocks.RED_TULIP, Blocks.ORANGE_TULIP, Blocks.WHITE_TULIP, Blocks.PINK_TULIP, Blocks.OXEYE_DAISY};

        for (int i = 0; i < count; i++) {
            // Select a random flower block from the array
            Block flowerBlock = flowerBlocks[(int) (Math.random() * flowerBlocks.length)];

            // Drop the random flower block
            world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(flowerBlock, 1)));
        }
    }
}
