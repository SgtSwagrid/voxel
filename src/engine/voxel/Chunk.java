package engine.voxel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Chunk {
    
    private VoxelGrid grid;
    
    private int size;
	
	private int cx, cy, cz;
	
	private int[][][] blocks;
	private int[][][] data;
	
	public Chunk(VoxelGrid grid, int size, int cx, int cy, int cz) {
	    
	    this.grid = grid;
        this.size = size;
	    this.cx = cx;
        this.cy = cy;
        this.cz = cz;
        
        blocks = new int[size][size][size];
        data = new int[size][size][size];
	}
	
	public VoxelGrid getGrid() { return grid; }
	
	public int getBlockId(int x, int y, int z) {
	    return blocks[x][y][z];
	}
	
	public int getBlockData(int x, int y, int z) {
	    return data[x][y][z];
	}
	
	public int getX() { return cx; }
	
	public int getY() { return cy; }
	
	public int getZ() { return cz; }
	
	public void save() {
	    
	    try(FileOutputStream fos = new FileOutputStream(getFileName())) {
	        DataOutputStream dos = new DataOutputStream(fos);
	        
	        for(int x = 0; x < size; x++) {
	            for(int y = 0; y < size; y++) {
	                for(int z = 0; z < size; z++) {
	                    
	                    dos.writeInt(blocks[x][y][z]);
	                    dos.writeInt(data[x][y][z]);
	                }
	            }
	        }
	    } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void load() {
	    
	    try(FileInputStream fis = new FileInputStream(getFileName())) {
	        DataInputStream dis = new DataInputStream(fis);
	        
	        for(int x = 0; x < size; x++) {
                for(int y = 0; y < size; y++) {
                    for(int z = 0; z < size; z++) {
                        
                        blocks[x][y][z] = dis.readInt();
                        data[x][y][z] = dis.readInt();
                    }
                }
	        }
	    } catch(IOException e) {
	        e.printStackTrace();
	    }
	}
	
	private String getFileName() {
	    return "saves/" + grid.getName() + "/c" + cx + "," + cy + "," + cz;
	}
}