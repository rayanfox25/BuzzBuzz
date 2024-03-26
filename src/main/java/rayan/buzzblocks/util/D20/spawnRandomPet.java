package rayan.buzzblocks.util.D20;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class spawnRandomPet {
    private final World world;
    private final BlockPos pos;
    private final int count;

    public spawnRandomPet(World world, BlockPos pos, int count) {
        this.world = world;
        this.pos = pos;
        this.count = count;
    }

    public void Pet() {
        // Define an array of pet entities (cat and wolf)
        EntityType<?>[] petEntities = {EntityType.CAT, EntityType.WOLF};

        for (int i = 0; i < 10; i++) {
            // Select a random pet entity from the array
            EntityType<?> petType = petEntities[(int) (Math.random() * petEntities.length)];

            // Spawn the pet
            Entity entity = petType.create(world);
            if (entity != null) {
                // Randomize position around the block
                double xOffset = Math.random() * 1.5 - 1.75; // Spawn within 0.75 blocks of center
                double yOffset = Math.random() + 1; // Spawn anywhere above the block
                double zOffset = Math.random() * 1.5 - 1.75;

                entity.refreshPositionAndAngles(
                        pos.getX() + xOffset,
                        pos.getY() + yOffset,
                        pos.getZ() + zOffset,
                        world.random.nextFloat() * 360.0F,
                        0.0F
                );
                world.spawnEntity(entity);
            }
        }
    }
}


