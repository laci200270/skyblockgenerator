package hu.laci200270.mods.skyblockgenerator;

public class BlockPOS {
	public int x;
	public int y;
	public int z;
	
	
	public BlockPOS(int x, int y, int z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}


	@Override
	public String toString() {
		return "BlockPOS [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
	
}
