package hu.laci200270.mods.skyblockgenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemWandOfSaving extends Item {

	public static BlockPOS pos1 = null;
	public static BlockPOS pos2 = null;

	@Override
	public boolean onItemUse(ItemStack tool, EntityPlayer player, World world,
			int x, int y, int z, int par7, float xFloat, float yFloat,
			float zFloat) {
		if (!player.isSneaking()) {
			pos1 = new BlockPOS(x, y, z);
		} else {
			pos2 = new BlockPOS(x, y, z);
		}
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_,
			EntityPlayer p_77659_3_) {
		System.out.println("right click in air");
		if ((pos1 != null) && (pos2 != null)) {
			System.out.println("not null");
			NBTTagCompound compond = DATAFileFormatHelper.saveWorldRegionToNBT(
					p_77659_2_, pos1, pos2);
			try {
				FileOutputStream fos = new FileOutputStream(new File(
						SkyBlockGeneratorModFile.configFolder, "structure.dat"));
				CompressedStreamTools.writeCompressed(compond, fos);
				fos.flush();
				fos.close();
				System.out.println("Write complette");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return p_77659_1_;
		}
		return p_77659_1_;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_,
			@SuppressWarnings("rawtypes") List p_77624_3_, boolean p_77624_4_) {
		// TODO Auto-generated method stub
		if (pos1 == null) {
			p_77624_3_.add("POS1 not set");
		} else {
			p_77624_3_.add("POS1 x:" + pos1.x + "y:" + pos1.y + "z:" + pos1.z);
		}
		if (pos2 == null) {
			p_77624_3_.add("POS2 not set");
		} else {
			p_77624_3_.add("POS2 x:" + pos2.x + "y:" + pos2.y + "z:" + pos2.z);
		}
	}

}
