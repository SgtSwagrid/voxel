package engine.render.light;

import org.lwjgl.util.vector.Vector3f;

import engine.util.Colour;

public class DirectionalLight extends Light {
	
	private Vector3f direction = new Vector3f(0.0F, 0.0F, -1.0F);
	
	public DirectionalLight() { super(); }
	
	public DirectionalLight(Colour c) { super(c); }
	
	public DirectionalLight(Vector3f dir) { direction = dir; }
	
	public DirectionalLight(Colour c, Vector3f dir) {
		super(c);
		direction = dir;
	}
	
	public Vector3f getDirection() { return direction; }
	
	public void setDirection(Vector3f dir) { direction = dir; }
	
}