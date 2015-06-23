package hu.laci200270.mods.skyblockgenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class SkyBlockEventHandler {

	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event) {
		if (!event.player.worldObj.isRemote) {
			if (event.player.worldObj.getWorldInfo().getTerrainType() == SkyBlockGeneratorModFile.type) {
				EntityPlayer player = event.player;
				SkyBlockPlayerProporites props = new SkyBlockPlayerProporites();
				player.registerExtendedProperties("skyblockgenerator", props);
				try {
					NBTTagCompound compound = CompressedStreamTools
							.readCompressed(new FileInputStream(new File(
									SkyBlockGeneratorModFile.configFolder,
									"structure.dat")));
					int playerx = (int) Math.floor(player.posX);
					int playery = (int) (player.posY - player.getYOffset()) - 5;
					int playerz = (int) Math.floor(player.posZ);
					DATAFileFormatHelper.loadWorldRegionFromNBT(
							player.worldObj, new BlockPOS(playerx, playery,
									playerz), compound);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
// }

