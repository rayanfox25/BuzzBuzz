package rayan.buzzblocks;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

import rayan.buzzblocks.entity.ModEntites;

public class BuzzBlocksClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntites.POLLEN_BALL_PROJECTIAL, FlyingItemEntityRenderer::new);


    }
}
