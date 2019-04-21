package engine.render.light;

import engine.util.Colour;

public abstract class Light {
	
	private Colour colour = Colour.WHITE;
	
	public Light() {}
	
	public Light(Colour c) { colour = c; }
	
	public Colour getColour() { return colour; }
	
	public void setColour(Colour c) { colour = c; }
	
}