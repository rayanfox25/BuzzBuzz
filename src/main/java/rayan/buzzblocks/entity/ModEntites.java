package rayan.buzzblocks.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import rayan.buzzblocks.BuzzBlocks;
import rayan.buzzblocks.entity.custom.PollenBallProjectialEntity;

public class ModEntites {

    public static void Pollenball()
    {

    }

    public static final EntityType<PollenBallProjectialEntity> POLLEN_BALL_PROJECTIAL = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(BuzzBlocks.MOD_ID, "polllen_ball"), FabricEntityTypeBuilder.<PollenBallProjectialEntity>create(SpawnGroup.MISC ,PollenBallProjectialEntity::new )
                    .dimensions(EntityDimensions.fixed(0.25f,0.25f)).build());

}



