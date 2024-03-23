package rayan.buzzblocks.entity.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import rayan.buzzblocks.BuzzBlocks;
import rayan.buzzblocks.entity.ModEntites;
import rayan.buzzblocks.item.ModItems;

import java.util.Random;



public class PollenBallProjectialEntity extends ThrownItemEntity {

    public PollenBallProjectialEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public PollenBallProjectialEntity(LivingEntity livingEntity, World world) {
        super(ModEntites.POLLEN_BALL_PROJECTIAL, livingEntity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.POLLEN_BAll;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {

    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        BlockPos blockPos = blockHitResult.getBlockPos();
        World world = getEntityWorld();

        // Introduce a probability factor for the explosion
        double lightningStrikeProbability = 0.11;
        double explosionProbability = 0.12; // Adjust this probability as needed
        double hostileMobSpawnProbability = 0.3;// 50% chance of spawning hostile mobs
        double foodDropProbability = 0.3;// 25% chance of dropping random food
        double petSpawnProbability = 0.20; // 10% chance of spawning a pet cat or wolf
        double flowerDropProbability = 0.2; // 20% chance of dropping 10 random flowers
        double diamondBlockSpawnProbability = 0.3;
        double sandstonePyramidSpawnProbability = 0.12;
        double zombieSpawnProbability = 0.11; // 17% chance to summon a zombie in full diamond armor fully enchanted


        // Track whether an event occurred
        boolean eventOccurred = false;

        if (!eventOccurred && Math.random() < sandstonePyramidSpawnProbability) {
            // Place a small sandstone pyramid
            BuzzBlocks.LOGGER.warn("This is the pyramid");
            placeSmallSandstonePyramid(world, blockPos);
            eventOccurred = true;
        } else if (!eventOccurred && Math.random() < lightningStrikeProbability) {
            BuzzBlocks.LOGGER.warn("This is the lightning");
            Entity player = getOwner();
            if (player instanceof ServerPlayerEntity) {
                // Strike the player with lightning
                LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
                lightning.refreshPositionAndAngles(player.getX(), player.getY(), player.getZ(), player.getYaw(), player.getPitch());
                world.spawnEntity(lightning);
                eventOccurred = true;
            }
        } else if (!eventOccurred && Math.random() < diamondBlockSpawnProbability) {
            BuzzBlocks.LOGGER.warn("This is the Diamond_Block");
            world.setBlockState(blockPos, Blocks.DIAMOND_BLOCK.getDefaultState());
            eventOccurred = true;
        } else if (!eventOccurred && Math.random() < explosionProbability) {
            BuzzBlocks.LOGGER.warn("This is the Exploded");
            CustomExplosion explosion = new CustomExplosion(world, blockPos, 15);
            explosion.explode();
            eventOccurred = true;
        } else if (!eventOccurred && Math.random() < hostileMobSpawnProbability) {
            BuzzBlocks.LOGGER.warn("This is the Spawning Mobs");
            spawnHostileMobs(world, blockPos, 20);
            eventOccurred = true;
        } else if (!eventOccurred && Math.random() < foodDropProbability) {
            BuzzBlocks.LOGGER.warn("This is the yay food");
            dropRandomFood(world, blockPos, 10);
            eventOccurred = true;
        } else if (!eventOccurred && Math.random() < petSpawnProbability) {
            BuzzBlocks.LOGGER.warn("This is the Pets");
            spawnRandomPet(world, blockPos);
            eventOccurred = true;
        } else if (!eventOccurred && Math.random() < flowerDropProbability) {
            BuzzBlocks.LOGGER.warn("This is the Flowers");
            dropRandomFlowers(world, blockPos, 10);
            eventOccurred = true;
        } else if (!eventOccurred && Math.random() < zombieSpawnProbability) {
            BuzzBlocks.LOGGER.warn("This is the zombie");
            // Create a new zombie entity
            ZombieEntity zombie = new ZombieEntity(world);

            // Equip the zombie with full diamond armor
            zombie.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
            zombie.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
            zombie.equipStack(EquipmentSlot.LEGS, new ItemStack(Items.DIAMOND_LEGGINGS));
            zombie.equipStack(EquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));

            // Apply enchantments to the zombie's armor
            int maxEnchantmentLevel = 5; // Maximum level of enchantment
            zombie.getEquippedStack(EquipmentSlot.HEAD).addEnchantment(Enchantments.PROTECTION, random.nextInt(maxEnchantmentLevel) + 1);
            zombie.getEquippedStack(EquipmentSlot.CHEST).addEnchantment(Enchantments.PROTECTION, random.nextInt(maxEnchantmentLevel) + 1);
            zombie.getEquippedStack(EquipmentSlot.LEGS).addEnchantment(Enchantments.PROTECTION, random.nextInt(maxEnchantmentLevel) + 1);
            zombie.getEquippedStack(EquipmentSlot.FEET).addEnchantment(Enchantments.PROTECTION, random.nextInt(maxEnchantmentLevel) + 1);

            // Add the zombie to the world
            zombie.refreshPositionAndAngles(blockPos, 0, 0);
            ((ServerWorld) world).spawnEntityAndPassengers(zombie);

            eventOccurred = true;
        }


        BlockState blockState = world.getBlockState(blockPos);
        if (!blockState.isAir() && blockState.isReplaceable()) {
            world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
            this.remove(RemovalReason.DISCARDED);
        }

        this.remove(RemovalReason.DISCARDED);
    }


    private void placeSmallSandstonePyramid(World world, BlockPos pos) {
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

    private void dropRandomFlowers(World world, BlockPos blockPos, int count) {
        // Define an array of flower blocks
        Block[] flowerBlocks = {Blocks.POPPY, Blocks.BLUE_ORCHID, Blocks.ALLIUM, Blocks.AZURE_BLUET, Blocks.RED_TULIP, Blocks.ORANGE_TULIP, Blocks.WHITE_TULIP, Blocks.PINK_TULIP, Blocks.OXEYE_DAISY};

        for (int i = 0; i < count; i++) {
            // Select a random flower block from the array
            Block flowerBlock = flowerBlocks[(int) (Math.random() * flowerBlocks.length)];

            // Drop the random flower block
            world.spawnEntity(new ItemEntity(world, blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, new ItemStack(flowerBlock, 1)));
        }
    }

    private void spawnRandomPet(World world, BlockPos blockPos) {
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
                        blockPos.getX() + xOffset,
                        blockPos.getY() + yOffset,
                        blockPos.getZ() + zOffset,
                        world.random.nextFloat() * 360.0F,
                        0.0F
                );
                world.spawnEntity(entity);
            }
            this.remove(RemovalReason.DISCARDED);
        }
        this.remove(RemovalReason.DISCARDED);
    }

    private void dropRandomFood(World world, BlockPos blockPos, int count) {
        // Define an array of food items
        Item[] foodItems = {Items.APPLE, Items.BREAD, Items.CARROT, Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_PORKCHOP, Items.COOKED_COD, Items.COOKED_SALMON};

        for (int i = 0; i < count; i++) {
            // Select a random food item from the array
            Item foodItem = foodItems[(int) (Math.random() * foodItems.length)];

            // Drop the random food item
            ItemStack foodStack = new ItemStack(foodItem, 1);
            ItemEntity itemEntity = new ItemEntity(world, blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, foodStack);
            world.spawnEntity(itemEntity);
        }
    }

    private void spawnHostileMobs(World world, BlockPos blockPos, int count) {
        // Define an array of hostile mobs excluding Ender Dragon and Wither
        EntityType<?>[] hostileMobs = {EntityType.ZOMBIE, EntityType.SKELETON, EntityType.CREEPER, EntityType.SPIDER, EntityType.WITCH, EntityType.HUSK, EntityType.STRAY, EntityType.EVOKER, EntityType.VINDICATOR, EntityType.DROWNED};

        for (int i = 0; i < count; i++) {
            // Select a random hostile mob from the array
            EntityType<?> mobType = hostileMobs[(int) (Math.random() * hostileMobs.length)];
            // Spawn the hostile mob
            Entity entity = mobType.create(world);
            if (entity != null) {
                entity.refreshPositionAndAngles(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, world.random.nextFloat() * 360.0F, 0.0F);
                world.spawnEntity(entity);
            }
        }
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

    private void spawnWitherSkeleton(World world, BlockPos blockPos) {
        // Get the entity type for Wither Skeleton
        EntityType<? extends WitherSkeletonEntity> entityType = EntityType.WITHER_SKELETON;

        // Spawn the Wither Skeleton entity at the given position
        WitherSkeletonEntity witherSkeletonEntity = entityType.create(world);
        if (witherSkeletonEntity != null) {
            witherSkeletonEntity.refreshPositionAndAngles(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, world.random.nextFloat() * 360.0F, 0.0F);
            world.spawnEntity(witherSkeletonEntity);
        }
    }
}
