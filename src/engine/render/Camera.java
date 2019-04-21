package engine.render;

import org.lwjgl.util.vector.Matrix4f;

import engine.util.math.Transform;

/**
 * Represents a transformation from which to render.
 * @author Alec
 */
public class Camera {
	
	private static volatile Transform transf = new Transform();
	
	/**
	 * Returns a reference to the transform used to represent this camera position.
	 */
	public Transform getTransform() { return transf; }
	
	/**
	 * Returns a view matrix for this camera, for use in transforming rendered objects.
	 */
	public Matrix4f getViewMatrix() {
		Matrix4f view = transf.asMatrix();
		view.invert();
		return view;
	}
}