package rayan.buzzblocks.util.D20;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.explosion.Explosion;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class CustomExplosion {
    private final World world;
    private final BlockPos pos;
    private final float explosionPower;

    public CustomExplosion(World world, BlockPos pos, float explosionPower) {
        this.world = world;
        this.pos = pos;
        this.explosionPower = explosionPower;
    }

    public void explode() {
        if (!world.isClient) {
            Explosion explosion = new Explosion(world, null, pos.getX(), pos.getY(), pos.getZ(), explosionPower, false, Explosion.DestructionType.DESTROY);
            explosion.collectBlocksAndDamageEntities();
            explosion.affectWorld(true);
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }
        spawnExplosionParticles();
    }

    @Environment(EnvType.CLIENT)
    public void spawnExplosionParticles() {
        // Limit the number of particles spawned per explosion
        int maxParticles = 100;
        for (int i = 0; i < maxParticles; i++) {
            double offsetX = world.random.nextGaussian();
            double offsetY = world.random.nextGaussian();
            double offsetZ = world.random.nextGaussian();
            double speedFactor = 0.5;
            world.addParticle(ParticleTypes.EXPLOSION, pos.getX() + offsetX, pos.getY() + offsetY, pos.getZ() + offsetZ, speedFactor * offsetX, speedFactor * offsetY, speedFactor * offsetZ);
            world.addParticle(ParticleTypes.WHITE_SMOKE, pos.getX() + offsetX, pos.getY() + offsetY, pos.getZ() + offsetZ, speedFactor * offsetX, speedFactor * offsetY, speedFactor * offsetZ);
        }
    }
}