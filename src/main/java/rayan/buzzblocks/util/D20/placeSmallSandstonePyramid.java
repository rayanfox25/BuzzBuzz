package rayan.buzzblocks.util.D20;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import rayan.buzzblocks.item.ModItems;

import java.util.Random;

public class placeSmallSandstonePyramid {
    private final World world;
    private final BlockPos pos;


    public placeSmallSandstonePyramid(World world, BlockPos pos) {
        this.world = world;
        this.pos = pos;
    }

    public void Pyramid() {
        // Define the structure of the small sandstone pyramid
        // For simplicity, let's create a 5x5 square pyramid with a height of 5 blocks

        // Build the pyramid
        for (int y = 0; y < 5; y++) {
            for (int x = -y; x <= y; x++) {
                for (int z = -y; z <= y; z++) {
                    world.setBlockState(pos.add(x, y, z), Blocks.SANDSTONE.getDefaultState(), 3);
                }
            }
        }

        // Place a dungeon chest on top of the pyramid
        BlockPos chestPos = pos.add(0, 5, 0);
        world.setBlockState(chestPos, Blocks.CHEST.getDefaultState(), 3);

        // Populate the chest with dungeon loot
        populateChestWithDungeonLoot(world, chestPos);
    }


    private void populateChestWithDungeonLoot(World world, BlockPos chestPos) {
        BlockEntity blockEntity = world.getBlockEntity(chestPos);
        if (blockEntity instanceof ChestBlockEntity) {
            ChestBlockEntity chest = (ChestBlockEntity) blockEntity;

            // Clear the existing chest contents
            chest.clear();

            // Add items simulating dungeon loot
            Random random = new Random();
            for (int i = 0; i < 5 + random.nextInt(3); i++) {
                // Add random items to the chest
                ItemStack itemStack = getRandomDungeonLootItem(random);
                chest.setStack(random.nextInt(chest.size()), itemStack);
            }
        }
    }

    private ItemStack getRandomDungeonLootItem(Random random) {
        // This is just an example, you can adjust it to fit your needs
        // For simplicity, this method returns a random item
        // You can customize this method to simulate dungeon loot more accurately
        ItemStack[] possibleItems = {
                new ItemStack(Blocks.COBBLESTONE),
                new ItemStack(Items.IRON_INGOT),
                new ItemStack(Items.RAIL),
                new ItemStack(Items.REDSTONE),
                new ItemStack(ModItems.ARTIFICIAL_HONEYCOMB),
                new ItemStack(Items.SHORT_GRASS),
                new ItemStack(Items.STICK)

                // Add more items as needed
        };
        return possibleItems[random.nextInt(possibleItems.length)].copy();
    }
}