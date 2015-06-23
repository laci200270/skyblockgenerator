package hu.laci200270.mods.skyblockgenerator;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class DATAFileFormatHelper {

	@SuppressWarnings("deprecation")
	public static NBTTagCompound saveWorldRegionToNBT(World world,
			BlockPOS pos1, BlockPOS pos2) {
		NBTTagCompound compound = new NBTTagCompound();
		if (pos1.x > pos2.x) {
			int temp1 = pos1.x;
			int temp2 = pos2.x;
			pos2.x = temp1;
			pos1.x = temp2;
		}
		if (pos1.y > pos2.y) {
			int temp1 = pos1.y;
			int temp2 = pos2.y;
			pos2.y = temp1;
			pos1.y = temp2;
		}
		if (pos1.z > pos2.z) {
			int temp1 = pos1.z;
			int temp2 = pos2.z;
			pos2.z = temp1;
			pos1.z = temp2;
		}
		int height = Math.abs(pos1.y - pos2.y);
		int width = Math.abs(pos1.x - pos2.x);
		int depth = Math.abs(pos1.z - pos2.z);
		System.out.println("Pos1:" + pos1);
		System.out.println("Pos2:" + pos2);
		System.out.println("height: " + height + "width: " + width + "depth: "
				+ depth);
		for (int y = pos1.y; y < pos2.y; y++) {
			for (int x = pos1.x; x < pos2.x + 1; x++) {
				for (int z = pos1.z; z < pos2.z + 1; z++) {
					int relativeX = Math.abs(pos1.x - x);
					int relativeZ = Math.abs(pos1.z - z);
					int relativeY = Math.abs(pos1.y - y);
					Block currentBlock = world.getBlock(x, y, z);
					NBTTagCompound currentBlockCompound = new NBTTagCompound();
					NBTTagCompound tileEntityCompound = new NBTTagCompound();
					String currentBlockName = (String) Block.blockRegistry
							.getNameForObject(currentBlock);

					Integer metadata = world.getBlockMetadata(x, y, z);
					System.out.println("processing block at:"
							+ new BlockPOS(x, y, z) + "relative condinates:"
							+ new BlockPOS(relativeX, relativeY, relativeZ)
							+ "Block type:" + currentBlockName + "matedata:"
							+ metadata);

					currentBlockCompound.setString("blockid", currentBlockName);
					currentBlockCompound.setInteger("metadata", metadata);
					String name = "Block|" + relativeX + "|" + relativeY + "|"
							+ relativeZ;
					if (currentBlock.hasTileEntity(metadata)
							|| currentBlock.hasTileEntity()) {
						currentBlockCompound.setBoolean("hasTileEntity", true);
						world.getTileEntity(x, y, z).writeToNBT(
								tileEntityCompound);
					}
					currentBlockCompound.setTag("tileEntityTag",
							tileEntityCompound);
					compound.setTag(name, currentBlockCompound);
				}
			}
		}
		compound.setInteger("width", width);
		compound.setInteger("height", height);
		compound.setInteger("depth", depth);
		return compound;
	}

	public static void loadWorldRegionFromNBT(World world, BlockPOS pos,
			NBTTagCompound compound) {

		System.out.println("Starting generating structure at: " + pos);
		int height = compound.getInteger("height");
		int width = compound.getInteger("width");
		int depth = compound.getInteger("depth");
		System.out.println("height: " + height + "width: " + width + "depth: "
				+ depth);
		for (int y = pos.y; y < pos.y + height; y++) {
			for (int x = pos.x; x < pos.x + width + 1; x++) {
				for (int z = pos.z; z < pos.z + depth + 1; z++) {
					int relativeX = 0;
					int relativeZ = 0;
					if (pos.x > x) {
						relativeX = pos.x - x;
					} else {
						relativeX = x - pos.x;
					}

					int relativeY = y - pos.y;
					if (pos.z > z) {
						relativeZ = pos.z - z;
					} else {
						relativeZ = z - pos.z;
					}

					NBTTagCompound currentBlockCompound = (NBTTagCompound) compound
							.getTag("Block|" + relativeX + "|" + relativeY
									+ "|" + relativeZ);
					if (currentBlockCompound == null) {

						throw new NullPointerException("compound is null");
					}
					NBTTagCompound tileEntityCompound = currentBlockCompound
							.getCompoundTag("tileEntityTag");
					int metadata = currentBlockCompound.getInteger("metadata");

					Block currentBlock = Block
							.getBlockFromName(currentBlockCompound
									.getString("blockid"));
					System.out.println("Setting block at: "
							+ new BlockPOS(x, y, z) + "To this: "
							+ currentBlockCompound.getString("blockid"));
					world.setBlock(x, y, z, currentBlock, metadata, 2);
					if (currentBlockCompound.getBoolean("hasTileEntity")) {
						tileEntityCompound.setInteger("x", x);
						tileEntityCompound.setInteger("y", y);
						tileEntityCompound.setInteger("z", z);
						TileEntity.createAndLoadEntity(tileEntityCompound);
						world.getBlock(x, y, z).createTileEntity(world,
								metadata);
						if (world.getTileEntity(x, y, z) == null) {
							throw new RuntimeException("TileEntity is null",
									new NullPointerException());
						}
						world.getTileEntity(x, y, z).readFromNBT(
								tileEntityCompound);

					}

				}
			}
		}

	}
}
