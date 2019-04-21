package engine.util.math;

import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Helper class for various matrix operations,
 * including the generation of common transformations.
 * @author Alec
 */
public class Matrix {
	
	//2D Matrix Transformations.
	
	/**
	 * Generates a 3x3 transformation matrix for the given translation.
	 * Intended for use in 2D space.
	 * @param transl a translation.
	 * @return the transformation matrix.
	 */
	public static Matrix3f genTranslation(Vector2f transl) {
		Matrix3f transf = new Matrix3f();
		transf.m20 = transl.x;
		transf.m21 = transl.y;
		return transf;
	}
	
	/**
	 * Extracts and returns the translation from within a transformation matrix.
	 * Other factors such as rotation and scale are ignored.
	 * Intended for use in 2D space.
	 * @param transf a transformation matrix.
	 * @return the translation from the matrix.
	 */
	public static Vector2f extrTranslation(Matrix3f transf) {
		return new Vector2f(transf.m20, transf.m21);
	}
	
	/**
	 * Remove any translation from this matrix, returning the result.
	 * Intended for use in 2D space.
	 * @param transf a transformation matrix.
	 * @return the same matrix, but without translation.
	 */
	public static Matrix3f remTranslation(Matrix3f transf) {
		Matrix3f transf2 = copy(transf);
		transf2.m20 = 0.0F;
		transf2.m21 = 0.0F;
		return transf2;
	}
	
	/**
	 * Set the position of a matrix, disregarding any existing translation.
	 * @param transf a transformation matrix.
	 * @param transl the desired position.
	 * @return the translated matrix.
	 */
	public static Matrix3f setTranslation(Matrix3f transf, Vector2f transl) {
		transf = remTranslation(transf);
		Matrix3f transf2 = new Matrix3f();
		Matrix3f.mul(genTranslation(transl), transf, transf2);
		return transf2;
	}
	
	/**
	 * Generates a 3x3 transformation matrix for the given rotation.
	 * The rotation is assumed anti-clockwise about the origin (in degrees).
	 * Intended for use in 2D space.
	 * @param rot a rotation, in degrees.
	 * @return the transformation matrix.
	 */
	public static Matrix3f genRotation(float rot) {
		Matrix3f transf = new Matrix3f();
		float cos = (float) Math.cos(Math.toRadians(rot));
		float sin = (float) Math.sin(Math.toRadians(rot));
		transf.m00 = cos; transf.m10 = -sin;
		transf.m01 = sin; transf.m11 = cos;
		return transf;
	}
	
	/**
	 * Generates a 3x3 rotation matrix for the given rotation.
	 * The rotation is anti-clockwise about the given point (in degrees).
	 * @param rot a rotation (in degrees).
	 * @param point the point to rotate about.
	 * @return the transformation matrix.
	 */
	public static Matrix3f genRotation(float rot, Vector2f point) {
		Matrix3f transf = new Matrix3f();
		Vector2f negPoint = new Vector2f();
		point.negate(negPoint);
		Matrix3f.mul(genRotation(rot), genTranslation(negPoint), transf);
		Matrix3f.mul(genTranslation(point), transf, transf);
		return transf;
	}
	
	/**
	 * Generates a 3x3 transformation matrix for the given scale.
	 * Intended for use in 2D space.
	 * @param scale a scale.
	 * @return the transformation.
	 */
	public static Matrix3f genScale(Vector2f scale) {
		Matrix3f transf = new Matrix3f();
		transf.m00 = scale.x;
		transf.m11 = scale.y;
		return transf;
	}
	
	/**
	 * Extracts and returns the scale from within a transformation matrix.
	 * Other factors such as translation and rotation are ignored.
	 * @param transf a transformation matrix.
	 * @return the scale from the matrix.
	 */
	public static Vector2f extrScale(Matrix3f transf) {
		return new Vector2f(
				new Vector2f(transf.m00, transf.m01).length(),
				new Vector2f(transf.m10, transf.m11).length());
	}
	
	/**
	 * Remove any scale from this matrix, returning the result.
	 * Intended for use in 2D space.
	 * @param transf a transformation matrix.
	 * @return the same matrix, but without scale.
	 */
	public static Matrix3f remScale(Matrix3f transf) {
		Matrix3f transf2 = copy(transf);
		Vector2f scale = extrScale(transf2);
		transf2.m00 /= scale.x; transf.m10 /= scale.y;
		transf2.m01 /= scale.x; transf.m11 /= scale.y;
		return transf2;
	}
	
	/**
	 * Set the scale of a matrix, disregarding any existing scale.
	 * @param transf a transformation matrix.
	 * @param scale the desired scale.
	 * @return the scaled matrix.
	 */
	public static Matrix3f setScale(Matrix3f transf, Vector2f scale) {
		transf = remScale(transf);
		Matrix3f transf2 = new Matrix3f();
		Matrix3f.mul(transf, genScale(scale), transf2);
		return transf2;
	}
	
	/**
	 * Generates a 3x3 transformation matrix for the given translation and rotation.
	 * The rotation is assumed anti-clockwise about the origin (in degrees).
	 * Intended for use in 2D space.
	 * @param transl a translation.
	 * @param rot a rotation, in degrees.
	 * @return the transformation matrix.
	 */
	public static Matrix3f genTransformation(Vector2f transl, float rot) {
		Matrix3f transf = new Matrix3f();
		Matrix3f.mul(genTranslation(transl), genRotation(rot), transf);
		return transf;
	}
	
	/**
	 * Generates a 3x3 transformation matrix for the given translation, rotation and scale.
	 * The rotation is assumed anti-clockwise about the origin (in degrees).
	 * Intended for use in 2D space.
	 * @param transl a translation.
	 * @param rot a rotation, in degrees.
	 * @param scale a scale.
	 * @return the transformation matrix.
	 */
	public static Matrix3f genTransformation(Vector2f transl, float rot, Vector2f scale) {
		Matrix3f transf = new Matrix3f();
		Matrix3f.mul(genTransformation(transl, rot), genScale(scale), transf);
		return transf;
	}
	
	/**
	 * Applies the given transformation matrix to a position vector.
	 * In other words, changes v(x, y) to v(x, y, 1.0) and multiplies it by the matrix.
	 * The result is that the full transformation is applied to the vector.
	 * For transforming direction vectors, see 'transfDirVec()' instead.
	 * Intended for use in 2D space.
	 * @param mat3 a transformation matrix.
	 * @param vec2 a position vector.
	 * @return the resulting vector.
	 */
	public static Vector2f transfPosVec(Matrix3f mat3, Vector2f vec2) {
		return new Vector2f(
				mat3.m00 * vec2.x + mat3.m10 * vec2.y + mat3.m20,
				mat3.m01 * vec2.x + mat3.m11 * vec2.y + mat3.m21);
	}
	
	/**
	 * Applies the given transformation matrix to a direction vector.
	 * In other words, changes v(x, y) to v(x, y, 0.0) and multiplies it by the matrix.
	 * The result is that any translation in the matrix is ignored.
	 * For transforming position vectors, see 'transfPosVec()' instead.
	 * Intended for use in 2D space.
	 * @param mat3 a transformation matrix.
	 * @param vec2 a direction vector.
	 * @return the resulting vector.
	 */
	public static Vector2f transfDirVec(Matrix3f mat3, Vector2f vec2) {
		return new Vector2f(
				mat3.m00 * vec2.x + mat3.m10 * vec2.y,
				mat3.m01 * vec2.x + mat3.m11 * vec2.y);
	}
	
	/**
	 * Makes a copy of a 3x3 matrix.
	 * @param mat3 a matrix.
	 * @return the copy.
	 */
	public static Matrix3f copy(Matrix3f mat3) {
		Matrix3f copy = new Matrix3f();
		copy.m00 = mat3.m00; copy.m10 = mat3.m10; copy.m20 = mat3.m20;
		copy.m01 = mat3.m01; copy.m11 = mat3.m11; copy.m21 = mat3.m21;
		copy.m02 = mat3.m02; copy.m12 = mat3.m12; copy.m22 = mat3.m22;
		return copy;
	}
	
	//3D Matrix Transformations.
	
	/**
	 * Generates a 4x4 transformation matrix for the given translation.
	 * Intended for use in 3D space.
	 * @param transl a translation.
	 * @return the transformation matrix.
	 */
	public static Matrix4f genTranslation(Vector3f transl) {
		Matrix4f transf = new Matrix4f();
		transf.m30 = transl.x;
		transf.m31 = transl.y;
		transf.m32 = transl.z;
		return transf;
	}
	
	/**
	 * Generates a 4x4 transformation matrix for the given rotation.
	 * The rotation occurs anti-clockwise about each axis in the order x-y-z.
	 * The described axes are intrinsic such that they move with each passing step.
	 * All angles are in degrees. Intended for use in 3D space.
	 * @param rot a rotation vector, in degrees (see description above).
	 * @return the transformation matrix.
	 */
	public static Matrix4f genRotation(Vector3f rot) {
		Matrix4f transf = new Matrix4f();
		transf = transf.rotate((float) Math.toRadians(rot.x), new Vector3f(1.0F, 0.0F, 0.0F));
		transf = transf.rotate((float) Math.toRadians(rot.y), new Vector3f(0.0F, 1.0F, 0.0F));
		transf = transf.rotate((float) Math.toRadians(rot.z), new Vector3f(0.0F, 0.0F, 1.0F));
		return transf;
	}
	
	/**
	 * Generates a 4x4 transformation matrix for the given rotation.
	 * The quaternion must be normalized or an exception will be thrown.
	 * Intended for use in 3D space.
	 * @param rot a normalized quaternion.
	 * @return the transformation matrix.
	 */
	public static Matrix4f genRotation(Quaternion rot) {
		if(Math.abs(rot.lengthSquared() - 1.0F) > 0.00001F)
			throw new IllegalArgumentException("Quaternion is degenerate.");
		Matrix4f m1 = new Matrix4f();
		m1.m00 = rot.w; m1.m10 = rot.z; m1.m20 = -rot.y; m1.m30 = rot.x;
		m1.m00 = -rot.z; m1.m10 = rot.w; m1.m20 = rot.x; m1.m30 = rot.y;
		m1.m00 = rot.y; m1.m10 = -rot.x; m1.m20 = rot.w; m1.m30 = rot.z;
		m1.m00 = -rot.x; m1.m10 = -rot.y; m1.m20 = -rot.z; m1.m30 = rot.w;
		Matrix4f m2 = new Matrix4f(m1);
		m2.m30 *= -1; m2.m31 *= -1; m2.m32 *= -1;
		m2.m03 *= -1; m2.m13 *= -1; m2.m23 *= -1;
		Matrix4f.mul(m1, m2, m1);
		return m1;
	}
	
	/**
	 * Generates a 4x4 transformation matrix for the given rotation.
	 * Rotation is anti-clockwise about the given axis.
	 * The axis must not be zero or an exception will be thrown.
	 * All angles are in degrees. Intended for use in 3D space.
	 * @param rot a rotation (the angle), in degrees.
	 * @param axis the axis to rotate about.
	 * @return the transformation matrix.
	 */
	public static Matrix4f genRotation(float rot, Vector3f axis) {
		if(axis.lengthSquared() < 0.00001F)
			throw new IllegalArgumentException("Axis is a zero vector.");
		Matrix4f transf = new Matrix4f();
		return transf.rotate((float) Math.toRadians(rot), axis);
	}
	
	/**
	 * Generates a 4x4 transformation matrix for the given rotation.
	 * Rotation is anti-clockwise about the given line (in degrees).
	 * The line is represented by a direction vector and a reference point.
	 * The direction must not be zero or an exception will be thrown.
	 * Intended for use in 3D space.
	 * @param rot a rotation, in degrees.
	 * @param dir the direction of the line.
	 * @param point any point on the line.
	 * @return the transformation matrix.
	 */
	public static Matrix4f genRotationAboutLine(float rot, Vector3f dir, Vector3f point) {
		if(dir.lengthSquared() < 0.00001F)
			throw new IllegalArgumentException("Direction is a zero vector.");
		Matrix4f transf = new Matrix4f();
		Vector3f negPoint = new Vector3f();
		point.negate(negPoint);
		Matrix4f.mul(genRotation(rot, dir), genTranslation(negPoint), transf);
		Matrix4f.mul(genTranslation(point), transf, transf);
		return transf;
	}
	
	/**
	 * Generates a 4x4 transformation matrix for the given scale.
	 * Intended for use in 3D space.
	 * @param scale a scale.
	 * @return the transformation.
	 */
	public static Matrix4f genScale(Vector3f scale) {
		Matrix4f transf = new Matrix4f();
		transf.m00 = scale.x;
		transf.m11 = scale.y;
		transf.m22 = scale.z;
		return transf;
	}
	
	/**
	 * Applies the given transformation matrix to a position vector.
	 * In other words, changes v(x, y, z) to v(x, y, z, 1.0) and multiplies it by the matrix.
	 * The result is that the full transformation is applied to the vector.
	 * For transforming direction vectors, see 'transfDirVec()' instead.
	 * Intended for use in 3D space.
	 * @param mat4 a transformation matrix.
	 * @param vec3 a position vector.
	 * @return the resulting vector.
	 */
	public static Vector3f transfPosVec(Matrix4f mat4, Vector3f vec3) {
		return new Vector3f(
				mat4.m00 * vec3.x + mat4.m10 * vec3.y + mat4.m20 * vec3.z + mat4.m30,
				mat4.m01 * vec3.x + mat4.m11 * vec3.y + mat4.m21 * vec3.z + mat4.m31,
				mat4.m02 * vec3.x + mat4.m12 * vec3.y + mat4.m22 * vec3.z + mat4.m32);
	}
	
	/**
	 * Applies the given transformation matrix to a direction vector.
	 * In other words, changes v(x, y, z) to v(x, y, z, 0.0) and multiplies it by the matrix.
	 * The result is that any translation in the matrix is ignored.
	 * For transforming position vectors, see 'transfPosVec()' instead.
	 * Intended for use in 3D space.
	 * @param mat4 a transformation matrix.
	 * @param vec3 a direction vector.
	 * @return the resulting vector.
	 */
	public static Vector3f transfDirVec(Matrix4f mat4, Vector3f vec3) {
		return new Vector3f(
				mat4.m00 * vec3.x + mat4.m10 * vec3.y + mat4.m20 * vec3.z,
				mat4.m01 * vec3.x + mat4.m11 * vec3.y + mat4.m21 * vec3.z,
				mat4.m02 * vec3.x + mat4.m12 * vec3.y + mat4.m22 * vec3.z);
	}
	
	/**
	 * Extracts and returns the translation from within a transformation matrix.
	 * Other factors such as rotation and scale are ignored.
	 * Intended for use in 3D space.
	 * @param transf a transformation matrix.
	 * @return the translation from the matrix.
	 */
	public static Vector3f extrTranslation(Matrix4f transf) {
		return new Vector3f(transf.m30, transf.m31, transf.m32);
	}
	
	/**
	 * Remove any translation from this matrix, returning the result.
	 * Intended for use in 3D space.
	 * @param transf a transformation matrix.
	 * @return the same matrix, but without translation.
	 */
	public static Matrix4f remTranslation(Matrix4f transf) {
		Matrix4f transf2 = new Matrix4f(transf);
		transf2.m30 = 0.0F;
		transf2.m31 = 0.0F;
		transf2.m32 = 0.0F;
		return transf2;
	}
	
	/**
	 * Extracts and returns the rotation from within a transformation matrix.
	 * Other factors such as translation and scale are ignored.
	 * Intended for use in 3D space.
	 * @param transf a transformation matrix.
	 * @return the rotation from the matrix.
	 */
	public static Quaternion extrRotation(Matrix4f transf) {
		transf = remScale(transf);
		float qw = (float) Math.sqrt(1 + transf.m00 + transf.m11 + transf.m22) / 2.0F;
		return new Quaternion(
				(transf.m21 - transf.m12) / (4.0F * qw),
				(transf.m02 - transf.m20) / (4.0F * qw),
				(transf.m10 - transf.m01) / (4.0F * qw), qw);
	}
	
	/**
	 * Remove any rotation from this matrix, returning the result.
	 * Intended for use in 3D space.
	 * Note that this will also remove skew if there is any.
	 * @param transf a transformation matrix.
	 * @return the same matrix, but without rotation.
	 */
	public static Matrix4f remRotation(Matrix4f transf) {
		Matrix4f transf2 = remScale(remTranslation(transf));
		Matrix4f.invert(transf2, transf2);
		Matrix4f.mul(transf, transf2, transf2);
		return transf2;
	}
	
	/**
	 * Converts an Euler rotation to quaternion form.
	 * The rotation occurs anti-clockwise about each axis in the order x-y-z.
	 * The described axes are intrinsic such that they move with each passing step.
	 * All angles are in degrees. Intended for use in 3D space.
	 * @param rot a rotation vector, in degrees (see description above).
	 * @return the rotation in quaternion form.
	 */
	public static Quaternion toQuaternion(Vector3f rot) {
		return extrRotation(genRotation(rot));
	}
	
	/**
	 * Extracts and returns the scale from within a transformation matrix.
	 * Other factors such as translation and rotation are ignored.
	 * @param transf a transformation matrix.
	 * @return the scale from the matrix.
	 */
	public static Vector3f extrScale(Matrix4f transf) {
		return new Vector3f(
				new Vector3f(transf.m00, transf.m01, transf.m02).length(),
				new Vector3f(transf.m10, transf.m11, transf.m12).length(),
				new Vector3f(transf.m20, transf.m21, transf.m22).length());
	}
	
	/**
	 * Remove any scale from this matrix, returning the result.
	 * Intended for use in 3D space.
	 * @param transf a transformation matrix.
	 * @return the same matrix, but without scale.
	 */
	public static Matrix4f remScale(Matrix4f transf) {
		transf = new Matrix4f(transf);
		Vector3f scale = extrScale(transf);
		transf.m00 /= scale.x; transf.m10 /= scale.y; transf.m20 /= scale.z;
		transf.m01 /= scale.x; transf.m11 /= scale.y; transf.m21 /= scale.z;
		transf.m02 /= scale.x; transf.m12 /= scale.y; transf.m22 /= scale.z;
		return transf;
	}
	
	/**
	 * Calculates a projection matrix with the given properties.
	 * @param fov field of view.
	 * @param aspect ratio (width / height).
	 * @param near clipping plane.
	 * @param far clipping plane (render distance).
	 * @return a 4x4 (3D) projection matrix.
	 */
	public static Matrix4f projection(float fov, float aspect, float near, float far) {
		
		Matrix4f proj = new Matrix4f();
		
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(fov / 2f))) * aspect);
        float x_scale = y_scale / aspect;
        float frustum_length = far - near;
 
        proj.m00 = x_scale;
        proj.m11 = y_scale;
        proj.m22 = -((far + near) / frustum_length);
        proj.m23 = -1;
        proj.m32 = -((2 * near * far) / frustum_length);
        proj.m33 = 0;
        
        return proj;
	}
	
	private static final float TOLERANCE = 0.00001F;
	
	public static boolean equal(Matrix4f mat1, Matrix4f mat2) {
		return equal(mat1.m00, mat2.m00) && equal(mat1.m01, mat2.m01) && equal(mat1.m02, mat2.m02) && equal(mat1.m03, mat2.m03) &&
			   equal(mat1.m10, mat2.m10) && equal(mat1.m11, mat2.m11) && equal(mat1.m12, mat2.m12) && equal(mat1.m13, mat2.m13) &&
			   equal(mat1.m20, mat2.m20) && equal(mat1.m21, mat2.m21) && equal(mat1.m22, mat2.m22) && equal(mat1.m23, mat2.m23) &&
			   equal(mat1.m30, mat2.m30) && equal(mat1.m31, mat2.m31) && equal(mat1.m32, mat2.m32) && equal(mat1.m33, mat2.m33);
	}
	
	private static boolean equal(float val1, float val2) {
		return Math.abs(val1 - val2) < TOLERANCE;
	}
}