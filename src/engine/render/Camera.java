package engine.render;

import org.lwjgl.util.vector.Matrix4f;

import engine.World;
import engine.util.math.Transform;

/**
 * Represents a transformation from which to render.
 * @author Alec
 */
public class Camera {
	
	private World world;
	private Transform transf;
	
	public Camera(World world) {
		this.world = world;
		transf = new Transform();
	}
	
	public Camera(World world, Transform transf) {
		this.world = world;
		this.transf = new Transform(transf);
	}
	
	/**
	 * Returns a reference to the transform used to represent this camera position.
	 */
	public Transform getTransform() { return transf; }
	
	public World getWorld() { return world; }
	
	/**
	 * Returns a view matrix for this camera, for use in transforming rendered objects.
	 */
	public Matrix4f getViewMatrix() {
		Matrix4f view = transf.asMatrix();
		view.invert();
		return view;
	}
}