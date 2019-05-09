package engine.voxel;

import engine.model.Texture;

public abstract class Block {
	
	public enum Side { TOP, BOTTOM, NORTH, EAST, SOUTH, WEST }
	
	public void update() {}
	
	public Texture getTexture(Side side) { return Texture.BLANK; }
	
	public boolean isOpaque() { return true; }
}