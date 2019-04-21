package engine.render.light;

import org.lwjgl.util.vector.Vector3f;

import engine.entity.World;
import engine.util.Colour;

public class DirectionalLight extends Light {
	
	private Vector3f direction = new Vector3f(0.0F, 0.0F, -1.0F);
	
	public DirectionalLight(World world) { super(world); }
	
	public DirectionalLight(World world, Colour c) { super(world, c); }
	
	public DirectionalLight(World world, Vector3f dir) {
		super(world);
		direction = dir;
	}
	
	public DirectionalLight(World world, Colour c, Vector3f dir) {
		super(world, c);
		direction = dir;
	}
	
	public Vector3f getDirection() { return direction; }
	
	public void setDirection(Vector3f dir) { direction = dir; }
	
}