package rayan.buzzblocks.entity.custom;

import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EndCombatS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import rayan.buzzblocks.block.ModBlocks;
import rayan.buzzblocks.entity.ModEntites;
import rayan.buzzblocks.item.ModItems;

public class PollenBallProjectialEntity extends ThrownItemEntity {
    public PollenBallProjectialEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public PollenBallProjectialEntity(LivingEntity livingEntity, World world) {
        super(ModEntites.POLLEN_BALL_PROJECTIAL, livingEntity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.POLLEN_BALL;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if(!this.getWorld().isClient()) {
            this.getWorld().sendEntityStatus(this, (byte)3);
        }

        this.discard();
    }
}