package rayan.buzzblocks.entity.custom;

import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.explosion.Explosion;

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
            explosion.affectWorld(false);
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS ,1.0f, 1.0f);
        }
    }
}
