package rayan.buzzblocks;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rayan.buzzblocks.util.ModLootTableModifiers;

import static rayan.buzzblocks.block.ModBlocks.*;
import static rayan.buzzblocks.entity.ModEntites.Pollenball;
import static rayan.buzzblocks.item.ModItemGroups.*;
import static rayan.buzzblocks.item.ModItems.*;


public class BuzzBlocks implements ModInitializer {

	public static final String MOD_ID = "buzzblocks";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {
		registerItemGroups();
        registerModItems();
		registerModBlocks();
		ModLootTableModifiers.modifyLootTables();
		Pollenball();
		LOGGER.info("Hello Fabric world!");
	}
}