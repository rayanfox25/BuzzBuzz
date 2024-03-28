package rayan.buzzblocks.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
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
import rayan.buzzblocks.util.CustomMaths;
import rayan.buzzblocks.util.D20.*;
import rayan.buzzblocks.util.PlayerMessageUtility;

import java.util.List;


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
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        BlockPos blockPos = blockHitResult.getBlockPos();
        World world = getEntityWorld();
        Entity owner = this.getOwner();

        if (owner instanceof ServerPlayerEntity) { // Added null check for owner
            // Introduce a probability factor for the explosion
            List<Double> probabilityFactors = List.of(
                    0.09, //lightningStrikeProbability
                    0.09,//explosionProbability
                    0.09, //hostileMobSpawnProbability
                    0.09, //foodDropProbability
                    0.09, //petSpawnProbability
                    0.09, //flowerDropProbability
                    0.09, //diamondBlockSpawnProbability
                    0.09, //sandstonePyramidSpawnProbability
                    0.09, //effectProbability Positive
                    0.09, //effectProbability Negative
                    0.09
            ); // Must add/sum to 1
            probabilityFactors = CustomMaths.preCalculateCumulativeProbabilities(probabilityFactors);
            int switchValue = CustomMaths.weightedRandomSelection(probabilityFactors);

            switch (switchValue) {
                case 0: //lightningStrikeProbability
                    BuzzBlocks.LOGGER.warn("This is the lightning");
                    PlayerMessageUtility.sendMessageToPlayers(world,"this effect is broken");
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
                    PlayerMessageUtility.sendMessageToPlayers(world, "DANG ANIMALS ");
                    break;
                case 5:
                    BuzzBlocks.LOGGER.warn("This is the Flowers");
                    dropRandomFlowers flowers = new dropRandomFlowers(world, blockPos, 10);
                    flowers.flowers();
                    PlayerMessageUtility.sendMessageToPlayers(world, "FLOWERS ALL OF THEM");
                    break;
                case 6:
                    BuzzBlocks.LOGGER.warn("This is the Diamond_Block");
                    world.setBlockState(blockPos, Blocks.DIAMOND_BLOCK.getDefaultState());
                    PlayerMessageUtility.sendMessageToPlayers(world, "OWO A DIAMOND");
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
                    BuzzBlocks.LOGGER.warn("Applying random Positive effect");
                   GiveNegtiveRandomEffect effect1 = new GiveNegtiveRandomEffect(world, (ServerPlayerEntity) owner);
                    effect1.NEffect();
                    break;
                case 10:
                    TNTTrap.triggerTrap(world, (ServerPlayerEntity) owner);
                    PlayerMessageUtility.sendMessageToPlayers(world, "GET EXPLODED");
                default:
                    // nothing
            }

            BlockState blockState = world.getBlockState(blockPos);
            if (!blockState.isAir() && blockState.isReplaceable()) {
                world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
                this.remove(RemovalReason.DISCARDED);
            }
        }
        this.remove(RemovalReason.DISCARDED);
    }
}