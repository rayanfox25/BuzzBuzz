package rayan.buzzblocks.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import rayan.buzzblocks.block.ModBlocks;
import rayan.buzzblocks.BuzzBlocks;

public class ModItemGroups {
    public static final ItemGroup RUBY_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(BuzzBlocks.MOD_ID, "ruby" ),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.hunny"))

                .icon(() -> new ItemStack(Items.HONEYCOMB)).entries((displayContext, entries) -> {

                    entries.add(Items.HONEY_BOTTLE);
                    entries.add(Items.HONEYCOMB);
                    entries.add(Items.BEE_NEST);
                    entries.add(Items.BEEHIVE);
                    entries.add(Items.BEE_SPAWN_EGG);
                    entries.add(ModBlocks.HONEY_SOAKED_PLANKS);
                    entries.add(ModItems.ARTIFICIAL_HONEYCOMB);
                    entries.add(ModBlocks.WOOD_PANELING);
                    entries.add(ModItems.POLLEN_BALL);

                    //blocks





                }).build());
    public static void registerItemGroups(){

    BuzzBlocks.LOGGER.info("registering Item Groups for " +BuzzBlocks.MOD_ID);
    }
}
