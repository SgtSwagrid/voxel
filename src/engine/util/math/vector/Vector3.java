package engine.util.math.vector;

public class Vector3 {
	
	private static final float TOLERANCE = 0.00001F;
	
	public float x = 0, y = 0, z = 0;
	
	public Vector3() {}
	
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3(Vector3 v) {
		this(v.x, v.y, v.z);
	}
	
	public Vector3(Vector2 xy, float z) {
		this(xy.x, xy.y, z);
	}
	
	public Vector3(float x, Vector2 yz) {
		this(x, yz.x, yz.y);
	}
	
	public Vector3(float[] a) {
		this(a[0], a[1], a[2]);
	}
	
	public float getX() { return x; }
	
	public Vector3 setX(float x) {
		this.x = x;
		return this;
	}
	
	public float getY() { return y; }
	
	public Vector3 setY(float y) {
		this.y = y;
		return this;
	}
	
	public float getZ() { return z; }
	
	public Vector3 setZ(float z) {
		this.z = z;
		return this;
	}
	
	public float getValue(int index) {
		switch(index) {
			case 0: return x;
			case 1: return y;
			case 2: return z;
			default: return 0;
		}
	}
	
	public Vector3 setValue(float value, int index) {
		switch(index) {
			case 0: x = value;
			case 1: y = value;
			case 2: z = value;
		}
		return this;
	}
	
	public Vector3 add(Vector3 v) {
		x += v.x;
		y += v.y;
		z += v.z;
		return this;
	}
	
	public Vector3 sub(Vector3 v) {
		x -= v.x;
		y -= v.y;
		z -= v.z;
		return this;
	}
	
	public Vector3 mul(Vector3 v) {
		x *= v.x;
		y *= v.y;
		z *= v.z;
		return this;
	}
	
	public Vector3 mul(float s) {
		x *= s;
		y *= s;
		z *= s;
		return this;
	}
	
	public Vector3 div(Vector3 v) {
		x /= v.x;
		y /= v.y;
		z /= v.z;
		return this;
	}
	
	public Vector3 div(float s) {
		x /= s;
		y /= s;
		z /= s;
		return this;
	}
	
	public Vector3 normalize() {
		float len = len();
		x /= len;
		y /= len;
		z /= len;
		return this;
	}
	
	public Vector3 proj(Vector3 v) {
		load(v.clone().normalize().mul(dot(this, v)));
		return this;
	}
	
	public Vector3 load(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	public Vector3 load(Vector3 v) {
		return load(v.x, v.y, v.z);
	}
	
	public float len() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	public float[] asArray() {
		return new float[] {x, y, z};
	}
	
	@Override
	public Vector3 clone() {
		return new Vector3(this);
	}
	
	public Vector2 xy() {
		return new Vector2(x, y);
	}
	
	public Vector2 yz() {
		return new Vector2(y, z);
	}
	
	public static Vector2 add(Vector2 v1, Vector2 v2) {
		return v1.clone().add(v2);
	}
	
	public static Vector2 sub(Vector2 v1, Vector2 v2) {
		return v1.clone().sub(v2);
	}
	
	public  static Vector2 mul(Vector2 v1, Vector2 v2) {
		return v1.clone().mul(v2);
	}
	
	public  static Vector2 mul(Vector2 v, float s) {
		return v.clone().mul(s);
	}
	
	public  static Vector2 div(Vector2 v1, Vector2 v2) {
		return v1.clone().div(v2);
	}
	
	public  static Vector2 div(Vector2 v, float s) {
		return v.clone().div(s);
	}
	
	public static Vector2 normalize(Vector2 v) {
		return v.clone().normalize();
	}
	
	public static boolean equal(Vector3 v1, Vector3 v2) {
		return Math.abs(v2.x - v1.x) < TOLERANCE &&
			   Math.abs(v2.y - v1.y) < TOLERANCE &&
			   Math.abs(v2.z - v1.z) < TOLERANCE;
	}
	
	public static Vector2 proj(Vector2 v1, Vector2 v2) {
		return v1.clone().proj(v2);
	}
	
	public static float dist(Vector3 v1, Vector3 v2) {
		return v1.clone().sub(v2).len();
	}
	
	public static float dot(Vector3 v1, Vector3 v2) {
		return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
	}
	
	public static float angle(Vector3 v1, Vector3 v2) {
		return (float) Math.acos(dot(v1, v2) / (v1.len() * v2.len()));
	}
	
	public static Vector3 cross(Vector3 v1, Vector3 v2) {
		return new Vector3(
				v1.y * v2.z - v1.z * v2.y,
				v1.z * v2.x - v1.x * v2.z,
				v1.x * v2.y - v1.y * v2.x);
	}
}