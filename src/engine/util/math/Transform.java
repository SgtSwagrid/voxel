package engine.util.math;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Object representing any 3D geometric transformation,
 * featuring tools to manipulate said transformation.
 * Relies on Matrix.java for most calculations.
 * @author Alec
 */
public class Transform {
	
	//Matrix back-end storing the transformation
	//represented by this Transformation object.
	private volatile Matrix4f transf;
	
	/**
	 * Default constructor; initializes as identity transformation.
	 */
	public Transform() {
		transf = new Matrix4f();
	}
	
	/**
	 * Copy constructor; initializes as equivalent to the given Transformation.
	 * @param t a Transformation3D instance.
	 */
	public Transform(Transform t) {
		transf = new Matrix4f(t.transf);
	}
	
	/**
	 * Initializes using the given transformation matrix. This Transformation
	 * object will represent an equivalent transformation to the one given.
	 * @param t a transformation matrix.
	 */
	public Transform(Matrix4f t) {
		transf = new Matrix4f(t);
	}
	
	/**
	 * Initializes using the given position (or translation).
	 * No rotation or scaling will be applied.
	 * @param pos a position or translation.
	 */
	public Transform(Vector3f pos) {
		transf = Matrix.genTranslation(pos);
	}
	
	/**
	 * Initializes using the given position/translation and orientation/rotation.
	 * The rotation is applied first (about the origin), and then the translation,
	 * along the absolute (global) axes.
	 * The rotation occurs anti-clockwise about each axis in the order x-y-z.
	 * The described axes are intrinsic such that they move with each passing step.
	 * All angles are in degrees.
	 * @param pos a position or translation, in global coordinates.
	 * @param rot an orientation or rotation, in degrees (see description above).
	 */
	public Transform(Vector3f pos, Vector3f rot) {
		transf = new Matrix4f();
		Matrix4f.mul(Matrix.genTranslation(pos), Matrix.genRotation(rot), transf);
	}
	
	/**
	 * Initializes using the given position/translation and orientation/rotation.
	 * The rotation is applied first (about the origin), and then the translation,
	 * along the absolute (global) axes.
	 * @param pos a position or translation, in global coordinates.
	 * @param rot an orientation or rotation.
	 */
	public Transform(Vector3f pos, Quaternion rot) {
		transf = new Matrix4f();
		Matrix4f.mul(Matrix.genTranslation(pos), Matrix.genRotation(rot), transf);
	}
	
	/**
	 * Initializes using the given orientation (or rotation).
	 * No translation or scaling will be applied.
	 * @param rot an orientation or rotation.
	 */
	public Transform(Quaternion rot) {
		transf = Matrix.genRotation(rot);
	}
	
	/**
	 * Returns a matrix representation of this Transformation object.
	 * This matrix will represent the same geometric transformation as this object.
	 * @return a transformation matrix.
	 */
	public Matrix4f asMatrix() {
		return new Matrix4f(transf);
	}
	
	/**
	 * Changes this Transformation to represent an equivalent
	 * geometric transformation as the given transformation matrix.
	 * @param t a transformation matrix.
	 */
	public void loadMatrix(Matrix4f t) {
		transf = new Matrix4f(t);
	}
	
	/**
	 * Changes this Transformation to be equivalent to the given one.
	 * @param t a transformation.
	 */
	public void loadTransformation(Transform t) {
		transf = new Matrix4f(t.transf);
	}
	
	/**
	 * Applies a matrix transformation to this Transformation object.
	 * This transformation is in local object space (acting with intrinsic axes).
	 * For a global world space transformation, see 'Transform()' instead.
	 * Within this class, a lower and upper case version of the same
	 * method name indicates local and global transformations respectively.
	 * @param t a transformation matrix.
	 */
	public void transform(Matrix4f t) {
		Matrix4f.mul(transf, t, transf);
	}
	
	/**
	 * Applies a transformation to this Transformation object.
	 * This transformation is in local object space (acting with intrinsic axes).
	 * For a global world space transformation, see 'Transform()' instead.
	 * Within this class, a lower and upper case version of the same
	 * method name indicates local and global transformations respectively.
	 * @param t a transformation.
	 */
	public void transform(Transform t) {
		Matrix4f.mul(transf, t.asMatrix(), transf);
	}
	
	/**
	 * Applies a matrix transformation to this Transformation object.
	 * This transformation is in global world space (acting with global axes).
	 * For a local object space transformation, see 'transform()' instead.
	 * Within this class, a lower and upper case version of the same
	 * method name indicates local and global transformations respectively.
	 * @param t a transformation matrix.
	 */
	public void Transform(Matrix4f T) {
		Matrix4f.mul(T, transf, transf);
	}
	
	/**
	 * Applies a transformation to this Transformation object.
	 * This transformation is in global world space (acting with global axes).
	 * For a local object space transformation, see 'transform()' instead.
	 * Within this class, a lower and upper case version of the same
	 * method name indicates local and global transformations respectively.
	 * @param t a transformation.
	 */
	public void Transform(Transform T) {
		Matrix4f.mul(T.asMatrix(), transf, transf);
	}
	
	/**
	 * Returns the 3-dimensional translation associated with this Transformation.
	 * Can be interpreted as the position of an object.
	 * The position is in global world space coordinates.
	 * @return the position/translation.
	 */
	public Vector3f getPosition() {
		return Matrix.extrTranslation(transf);
	}
	
	/**
	 * Sets the position/translation associated with this Transformation.
	 * Any previous position data is discarded.
	 * The position is in global world space coordinates.
	 * @param pos a position/translation.
	 */
	public void setPosition(Vector3f pos) {
		transf = Matrix.remTranslation(transf);
		Transform(Matrix.genTranslation(pos));
	}
	
	public void setPosition(Vector2f pos) {
		setPosition(new Vector3f(pos.x, pos.y, 0.0F));
	}
	
	/**
	 * Translates this Transformation as per the given translation.
	 * This translation is in local object space (acting with intrinsic axes).
	 * For a global world space translation, see 'Translate()' instead.
	 * Within this class, a lower and upper case version of the same
	 * method name indicates local and global transformations respectively.
	 * @param transl a translation vector.
	 */
	public void translate(Vector3f transl) {
		transform(Matrix.genTranslation(transl));
	}
	
	public void translate(Vector2f transl) {
		translate(new Vector3f(transl.x, transl.y, 0.0F));
	}
	
	/**
	 * Translates this Transformation as per the given translation.
	 * This translation is in global world space (acting with global axes).
	 * For a local object space translation, see 'translate()' instead.
	 * Within this class, a lower and upper case version of the same
	 * method name indicates local and global transformations respectively.
	 * @param transl a translation vector.
	 */
	public void Translate(Vector3f Transl) {
		Transform(Matrix.genTranslation(Transl));
	}
	
	public void Translate(Vector2f transl) {
		Translate(new Vector3f(transl.x, transl.y, 0.0F));
	}
	
	/**
	 * Returns the rotation associated with this Transformation.
	 * Can be interpreted as the orientation of an object.
	 * The orientation is with respect to the global axes,
	 * but is about the objects local origin.
	 * @return the orientation/rotation, as a quaternion.
	 */
	public Quaternion getOrientation() {
		return Matrix.extrRotation(transf);
	}
	
	/**
	 * Returns a unit vector in the forward direction of an object
	 * transformed by this Transformation. Assumes the local forward
	 * direction of an object is taken to be (0, 0, -1).
	 * @return unit vector in forward direction.
	 */
	public Vector3f getForwardVector() {
		Vector3f dir = Matrix.transfDirVec(transf, new Vector3f(0.0F, 0.0F, -1.0F));
		dir.normalise();
		return dir;
	}
	
	/**
	 * Sets the orientation/rotation associated with this Transformation.
	 * Any previous orientation data is discarded.
	 * The orientation is with respect to the global axes,
	 * but is about the objects local origin.
	 * The rotation occurs anti-clockwise about each axis in the order x-y-z.
	 * The described axes are intrinsic such that they move with each passing step.
	 * All angles are in degrees.
	 * This is overloaded to accept both Euler angles and quaternions.
	 * @param rot an orientation/rotation, in degrees.
	 */
	public void setOrientation(Vector3f rot) {
		transf = Matrix.remRotation(transf);
		Rotate(rot);
	}
	
	/**
	 * Sets the orientation/rotation associated with this Transformation.
	 * Any previous orientation data is discarded.
	 * The orientation is with respect to the global axes,
	 * but is about the objects local origin.
	 * This is overloaded to accept both Euler angles and quaternions.
	 * @param rot an orientation/rotation.
	 */
	public void setOrientation(Quaternion rot) {
		transf = Matrix.remRotation(transf);
		Rotate(rot);
	}
	
	/**
	 * Rotates this Transformation as per the given rotation.
	 * This rotation is in local object space (acting with intrinsic axes).
	 * The rotation occurs anti-clockwise about each axis in the order x-y-z.
	 * The described axes are intrinsic such that they move with each passing step.
	 * All angles are in degrees.
	 * For a global world space rotation, see 'Rotate()' instead.
	 * Within this class, a lower and upper case version of the same
	 * method name indicates local and global transformations respectively.
	 * This is overloaded to accept both Euler angles and quaternions.
	 * @param rot a rotation, in degrees (see description above).
	 */
	public void rotate(Vector3f rot) {
		transform(Matrix.genRotation(rot));
	}
	
	/**
	 * Rotates this Transformation as per the given rotation.
	 * This rotation is in local object space (acting with intrinsic axes).
	 * For a global world space rotation, see 'Rotate()' instead.
	 * Within this class, a lower and upper case version of the same
	 * method name indicates local and global transformations respectively.
	 * This is overloaded to accept both Euler angles and quaternions.
	 * @param rot a rotation.
	 */
	public void rotate(Quaternion rot) {
		transform(Matrix.genRotation(rot));
	}
	
	/**
	 * Rotates this Transformation as per the given rotation.
	 * The rotation axis is in local object space (defined by intrinsic axes).
	 * Rotation is anti-clockwise about the given axis.
	 * All angles are in degrees.
	 * For a global world space rotation, see 'Rotate()' instead.
	 * Within this class, a lower and upper case version of the same
	 * method name indicates local and global transformations respectively.
	 * @param rot a rotation (the angle), in degrees.
	 * @param axis the axis to rotate about.
	 */
	public void rotate(float rot, Vector3f axis) {
		transform(Matrix.genRotation(rot, axis));
	}
	
	/**
	 * Rotates this Transformation as per the given rotation.
	 * This rotation is in global world space (acting with global axes),
	 * however it acts about the objects local origin.
	 * The rotation occurs anti-clockwise about each axis in the order x-y-z.
	 * The described axes are intrinsic such that they move with each passing step.
	 * All angles are in degrees.
	 * For a local object space rotation, see 'rotate()' instead.
	 * Within this class, a lower and upper case version of the same
	 * method name indicates local and global transformations respectively.
	 * This is overloaded to accept both Euler angles and quaternions.
	 * @param rot a rotation, in degrees (see description above).
	 */
	public void Rotate(Vector3f rot) {
		Vector3f pos = Matrix.extrTranslation(transf);
		transf = Matrix.remTranslation(transf);
		Transform(Matrix.genRotation(rot));
		Translate(pos);
	}
	
	/**
	 * Rotates this Transformation as per the given rotation.
	 * This rotation is in global world space (acting with global axes),
	 * however it acts about the objects local origin.
	 * For a local object space rotation, see 'rotate()' instead.
	 * Within this class, a lower and upper case version of the same
	 * method name indicates local and global transformations respectively.
	 * This is overloaded to accept both Euler angles and quaternions.
	 * @param rot a rotation.
	 */
	public void Rotate(Quaternion rot) {
		Vector3f pos = Matrix.extrTranslation(transf);
		transf = Matrix.remTranslation(transf);
		Transform(Matrix.genRotation(rot));
		Translate(pos);
	}
	
	/**
	 * Rotates this Transformation as per the given rotation.
	 * The rotation axis is in global world space (defined by global axes).
	 * Rotation is anti-clockwise about the given axis.
	 * All angles are in degrees.
	 * For a local object space rotation, see 'rotate()' instead.
	 * Within this class, a lower and upper case version of the same
	 * method name indicates local and global transformations respectively.
	 * @param rot a rotation (the angle), in degrees.
	 * @param axis the axis to rotate about.
	 */
	public void Rotate(float rot, Vector3f axis) {
		Vector3f pos = Matrix.extrTranslation(transf);
		transf = Matrix.remTranslation(transf);
		Transform(Matrix.genRotation(rot, axis));
		Translate(pos);
	}
	
	/**
	 * Rotates this Transformation a particular angle about a given line.
	 * The line is defined in global world space (defined by global axes).
	 * Rotation is anti-clockwise about the given line (in degrees).
	 * The line is represented by a direction vector and a reference point.
	 * @param rot a rotation, in degrees.
	 * @param dir the direction of the line.
	 * @param point any point on the line.
	 */
	public void rotateAboutLine(float rot, Vector3f dir, Vector3f point) {
		Vector3f pos = Matrix.extrTranslation(transf);
		transf = Matrix.remTranslation(transf);
		Transform(Matrix.genRotationAboutLine(rot, dir, point));
		Translate(pos);
	}
	
	/**
	 * Returns the scale associated with this Transformation.
	 * The default scale is (1, 1, 1) (no scaling).
	 * The scale is in local object space (acting with intrinsic axes).
	 * @return the scale vector.
	 */
	public Vector3f getScale() {
		return Matrix.extrScale(transf);
	}
	
	/**
	 * Returns the scale associated with this Transformation.
	 * The default scale is (1, 1, 1) (no scaling).
	 * The scale is in local object space (acting with intrinsic axes).
	 * @param scale a scale vector.
	 */
	public void setScale(Vector3f scale) {
		transf = Matrix.remScale(transf);
		Matrix4f.mul(transf, Matrix.genScale(scale), transf);
	}
	
	public void setScale(Vector2f scale) {
		setScale(new Vector3f(scale.x, scale.y, 1.0F));
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof Transform &&
				Matrix.equal(((Transform) o).asMatrix(), asMatrix());
	}
}
