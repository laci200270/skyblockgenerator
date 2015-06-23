package hu.laci200270.mods.skyblockgenerator;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class SkyBlockPlayerProporites implements IExtendedEntityProperties {

	public boolean hasPlatform = false;

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		compound.setBoolean("getPlaform", hasPlatform);

	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		hasPlatform = compound.getBoolean("getPlaform");

	}

	@Override
	public void init(Entity entity, World world) {

	}

}
