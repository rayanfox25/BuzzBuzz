package rayan.buzzblocks.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EndCombatS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import rayan.buzzblocks.entity.ModEntites;
import rayan.buzzblocks.item.ModItems;

public class PollenBallProjectialEntity extends ThrownItemEntity {
    public PollenBallProjectialEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }
    public PollenBallProjectialEntity(LivingEntity livingEntity, World world) {
        super(ModEntites.POLLEN_PROJECTAIL, livingEntity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.POLLEN_BALL;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
