package hu.laci200270.mods.skyblockgenerator;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SkyBlockWorldType extends WorldType {

	public SkyBlockWorldType() {
		super("skyblock");
		DimensionManager.unregisterProviderType(0);
		DimensionManager.registerProviderType(0, WorldProviderSkyBlock.class,
				true);

	}

	@Override
	public IChunkProvider getChunkGenerator(World world, String generatorOptions) {
		// TODO Auto-generated method stub
		return new SkyBlockChunkGenerator(world, world.getSeed(), true, "");

	}

	/*
	 * @Override public WorldChunkManager getChunkManager(World world) { // TODO
	 * Auto-generated method stub return new SkyBlockChunkGenerator(world,
	 * world.getSeed(), true, ""); }
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public boolean showWorldInfoNotice() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCustomizable() {
		// TODO Auto-generated method stub
		return true;
	}

}
