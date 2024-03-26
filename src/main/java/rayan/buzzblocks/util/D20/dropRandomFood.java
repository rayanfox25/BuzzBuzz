package rayan.buzzblocks.util.D20;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class dropRandomFood {
    private final World world;
    private final BlockPos pos;
    private final int count;

    public dropRandomFood(World world, BlockPos pos, int count) {
        this.world = world;
        this.pos = pos;
        this.count = count;
    }

    public void Food() {
        // Define an array of food items
        Item[] foodItems = {Items.APPLE, Items.BREAD, Items.CARROT, Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_PORKCHOP, Items.COOKED_COD, Items.COOKED_SALMON};

        for (int i = 0; i < count; i++) {
            // Select a random food item from the array
            Item foodItem = foodItems[(int) (Math.random() * foodItems.length)];

            // Drop the random food item
            ItemStack foodStack = new ItemStack(foodItem, 1);
            ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, foodStack);
            world.spawnEntity(itemEntity);
        }
    }
}

