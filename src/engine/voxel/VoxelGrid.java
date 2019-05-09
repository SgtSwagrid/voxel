package engine.voxel;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class VoxelGrid {
    
    private String name;
    
    private Class[] blocks = new Class[1000];
    
    private int chunkSize, loadRadius;
    
    private int cx, cy, cz;
    
    private Chunk[][][] chunks;
    
    public VoxelGrid(String name, int chunkSize, int loadRadius, int cx, int cy, int cz) {
        
        this.name = name;
        this.chunkSize = chunkSize;
        this.loadRadius = loadRadius;
        this.cx = cx;
        this.cy = cy;
        this.cz = cz;
        chunks = new Chunk[2 * loadRadius + 1][2 * loadRadius + 1][2 * loadRadius + 1];
    }
    
    public String getName() { return name; }
    
    public void moveCentre(int cx, int cy, int cz) {
        
        int dx = cx - this.cx;
        int dy = cy - this.cy;
        int dz = cz - this.cz;
        
        Chunk[][][] newChunks = new Chunk[chunkSize][chunkSize][chunkSize];
        
        for(int x = -loadRadius; x <= loadRadius; x++) {
            for(int y = -loadRadius; y <= loadRadius; y++) {
                for(int z = -loadRadius; z <= loadRadius; z++) {
                    
                    //If this chunk is in the previously loaded region.
                    if(x + dx >= 0 && x + dx < chunks.length && y + dy >= 0
                            && y + dy < chunks.length && z + dz >= 0 && z + dz < chunks.length) {
                        
                        
                    }
                }
            }
        }
        
        this.cx = cx;
        this.cy = cy;
        this.cz = cz;
    }
    
    public Block getBlock(int x, int y, int z) {
        
        int xx = x / chunkSize - cx + loadRadius;
        int yy = y / chunkSize - cy + loadRadius;
        int zz = z / chunkSize - cz + loadRadius;
        
        int id = chunks[xx][yy][zz].getBlockId(x % chunkSize, y % chunkSize, z % chunkSize);
        int data = chunks[xx][yy][zz].getBlockData(x % chunkSize, y % chunkSize, z % chunkSize);
        
        Block block = null;
        try {
            block = (Block) blocks[id].getConstructor(Integer.class).newInstance(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return block;
    }
    
    public <B extends Block> void registerBlock(int index, Class<B> block) {
           blocks[index] = block;
    }
}