package rayan.buzzblocks;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.Entity;
import rayan.buzzblocks.entity.ModEntites;

public class BuzzBlocksClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntites.POLLEN_PROJECTAIL, FlyingItemEntityRenderer::new);
    }
}
