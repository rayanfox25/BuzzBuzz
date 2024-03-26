package rayan.buzzblocks.util.D20;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class spawnHostileMobs {
    private final World world;
    private final BlockPos pos;
    private final int count;

    public spawnHostileMobs(World world, BlockPos pos, int count) {
        this.world = world;
        this.pos = pos;
        this.count = count;
    }

    public void Mobs() {
        // Define an array of hostile mobs excluding Ender Dragon and Wither
        EntityType<?>[] hostileMobs = {EntityType.ZOMBIE, EntityType.SKELETON, EntityType.CREEPER, EntityType.SPIDER, EntityType.WITCH, EntityType.HUSK, EntityType.STRAY, EntityType.EVOKER, EntityType.VINDICATOR, EntityType.DROWNED};

        for (int i = 0; i < count; i++) {
            // Select a random hostile mob from the array
            EntityType<?> mobType = hostileMobs[(int) (Math.random() * hostileMobs.length)];
            // Spawn the hostile mob
            Entity entity = mobType.create(world);
            if (entity != null) {
                entity.refreshPositionAndAngles(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, world.random.nextFloat() * 360.0F, 0.0F);
                world.spawnEntity(entity);

            }
        }
    }
}
