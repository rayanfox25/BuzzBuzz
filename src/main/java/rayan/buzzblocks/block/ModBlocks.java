package rayan.buzzblocks.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import rayan.buzzblocks.BuzzBlocks;

public class ModBlocks {
public static final Block  HONEY_SOAKED_PLANKS = registerBlock("honey_soaked_planks",
    new Block(FabricBlockSettings.copyOf(Blocks.SLIME_BLOCK).sounds(BlockSoundGroup.SLIME)));
public static final Block  WOOD_PANELING = registerBlock("wood_paneling",
    new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).sounds(BlockSoundGroup.WOOD)));

private static Block registerBlock(String name, Block block){
    registerBlockItem(name , block);
    return Registry.register(Registries.BLOCK, new Identifier(BuzzBlocks.MOD_ID, name ), block);
}
    private static void registerBlockItem(String name, Block block){
        Registry.register(Registries.ITEM, new Identifier(BuzzBlocks.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));

    }
    public static void registerModBlocks() {
    BuzzBlocks.LOGGER.info("registering mod Blocks for " + BuzzBlocks.MOD_ID);
    }
}
