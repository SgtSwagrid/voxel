package engine.render.light;

import engine.entity.World;
import engine.util.Colour;

public abstract class Light {
	
	private Colour colour = Colour.WHITE;
	
	public Light(World world) {
		world.addLight(this);
	}
	
	public Light(World world, Colour c) {
		world.addLight(this);
		colour = c;
	}
	
	public Colour getColour() { return colour; }
	
	public void setColour(Colour c) { colour = c; }
}