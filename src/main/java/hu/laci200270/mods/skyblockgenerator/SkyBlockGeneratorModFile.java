package hu.laci200270.mods.skyblockgenerator;

import java.io.File;

import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "skyblockgenerator")
public class SkyBlockGeneratorModFile {
	public static SkyBlockWorldType type = null;
	public static File configFolder = null;
	
	@Instance
	public static SkyBlockGeneratorModFile instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		configFolder = event.getModConfigurationDirectory();
		ForgeChunkManager.setForcedChunkLoadingCallback(instance, new SkyBlockGeneratorCallbackHelper());
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		
		type = new SkyBlockWorldType();
		SkyBlockEventHandler handler = new SkyBlockEventHandler();
		FMLCommonHandler.instance().bus().register(handler);
		MinecraftForge.EVENT_BUS.register(handler);
		GameRegistry.registerItem(new ItemWandOfSaving(), "wandofsave");
	}

}
