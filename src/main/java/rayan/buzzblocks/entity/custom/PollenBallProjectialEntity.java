package rayan.buzzblocks.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import rayan.buzzblocks.BuzzBlocks;
import rayan.buzzblocks.entity.ModEntites;
import rayan.buzzblocks.item.ModItems;
import rayan.buzzblocks.util.D20.*;

import java.util.List;

import static rayan.buzzblocks.util.CustomMaths.preCalculateCumulativeProbabilities;
import static rayan.buzzblocks.util.CustomMaths.weightedRandomSelection;


public class PollenBallProjectialEntity extends ThrownItemEntity {
    World world = getEntityWorld();
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
    protected void onBlockHit(BlockHitResult blockHitResult ) {
        super.onBlockHit(blockHitResult);
        BlockPos blockPos = blockHitResult.getBlockPos();
        World world = getEntityWorld();
        Entity owner = this.getOwner();


        // Introduce a probability factor for the explosion
        List<Double> probabilityFactors = List.of(
                0.1, //lightningStrikeProbability
                0.1, //explosionProbability
                0.1, //hostileMobSpawnProbability
                0.1, //foodDropProbability
                0.1, //petSpawnProbability
                0.1, //flowerDropProbability
                0.1, //diamondBlockSpawnProbability
                0.1, //sandstonePyramidSpawnProbability
                0.1 , //effectProbability
                0.1
        ); // Must add/sum to 1
        probabilityFactors = preCalculateCumulativeProbabilities(probabilityFactors);
        int switchValue = weightedRandomSelection(probabilityFactors);

        switch (switchValue) {
            case 0: //lightningStrikeProbability
                BuzzBlocks.LOGGER.warn("This is the lightning");
                Entity player = getOwner();
                if (player instanceof ServerPlayerEntity) {
                    // Strike the player with lightning
                    LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
                    lightning.refreshPositionAndAngles(player.getX(), player.getY(), player.getZ(), player.getYaw(), player.getPitch());
                    world.spawnEntity(lightning);
                }
                break;
            case 1:
                BuzzBlocks.LOGGER.warn("This is the Exploded");
                CustomExplosion explosion = new CustomExplosion(world, blockPos, 15);
                explosion.explode();
                break;
            case 2:
                BuzzBlocks.LOGGER.warn("This is the Spawning Mobs");
                spawnHostileMobs mob = new spawnHostileMobs(world, blockPos, 10);
                mob.Mobs();
                break;
            case 3:
                BuzzBlocks.LOGGER.warn("This is the yay food");
                dropRandomFood food = new dropRandomFood(world, blockPos, 10);
                food.Food();
                break;
            case 4:
                BuzzBlocks.LOGGER.warn("This is the Pets");
                spawnRandomPet pet = new spawnRandomPet(world, blockPos, 10);
                pet.Pet();
                break;
            case 5:
                BuzzBlocks.LOGGER.warn("This is the Flowers");
                dropRandomFlowers flowers = new dropRandomFlowers(world, blockPos, 10);
                flowers.flowers();
                break;
            case 6:
                BuzzBlocks.LOGGER.warn("This is the Diamond_Block");
                world.setBlockState(blockPos, Blocks.DIAMOND_BLOCK.getDefaultState());
                break;
            case 7:
                // Place a small sandstone pyramid
                BuzzBlocks.LOGGER.warn("This is the pyramid");
                placeSmallSandstonePyramid pyramid = new placeSmallSandstonePyramid(world, blockPos);
                pyramid.Pyramid();
                break;
            case 8:
                BuzzBlocks.LOGGER.warn("Applying random Positive effect");
                GivePositiveRandomEffect effect = new GivePositiveRandomEffect(world, (ServerPlayerEntity) owner);
                effect.GEffect();
                break;
            case 9:
                BuzzBlocks.LOGGER.warn("Applying random Negative effect");
                GiveNegtiveRandomEffect effect1 = new GiveNegtiveRandomEffect(world, (ServerPlayerEntity) owner);
                effect1.NEffect();
                break;
            default:
                // nothing
        }


        BlockState blockState = world.getBlockState(blockPos);
        if (!blockState.isAir() && blockState.isReplaceable()) {
            world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
            this.remove(RemovalReason.DISCARDED);
        }

        this.remove(RemovalReason.DISCARDED);

    }
}

