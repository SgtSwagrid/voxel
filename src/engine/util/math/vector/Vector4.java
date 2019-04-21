package engine.util.math.vector;

public class Vector4 {
	
	private static final float TOLERANCE = 0.00001F;
	
	public float x = 0, y = 0, z = 0, w = 0;
	
	public Vector4() {}
	
	public Vector4(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4(Vector4 v) {
		this(v.x, v.y, v.z, v.w);
	}
	
	public Vector4(Vector3 xyz, float w) {
		this(xyz.x, xyz.y, xyz.z, w);
	}
	
	public Vector4(float x, Vector3 yzw) {
		this(x, yzw.x, yzw.y, yzw.z);
	}
	
	public Vector4(Vector2 xy, Vector2 zw) {
		this(xy.x, xy.y, zw.x, zw.y);
	}
	
	public Vector4(Vector2 xy, float z, float w) {
		this(xy.x, xy.y, z, w);
	}
	
	public Vector4(float x, Vector2 yz, float w) {
		this(x, yz.x, yz.y, w);
	}
	
	public Vector4(float x, float y, Vector2 zw) {
		this(x, y, zw.x, zw.y);
	}
	
	public Vector4(float[] a) {
		this(a[0], a[1], a[2], a[3]);
	}
	
	public float getX() { return x; }
	
	public Vector4 setX(float x) {
		this.x = x;
		return this;
	}
	
	public float getY() { return y; }
	
	public Vector4 setY(float y) {
		this.y = y;
		return this;
	}
	
	public float getZ() { return z; }
	
	public Vector4 setZ(float z) {
		this.z = z;
		return this;
	}
	
	public float getW() { return w; }
	
	public Vector4 setW(float w) {
		this.w = w;
		return this;
	}
	
	public float getValue(int index) {
		switch(index) {
			case 0: return x;
			case 1: return y;
			case 2: return z;
			case 3: return w;
			default: return 0;
		}
	}
	
	public Vector4 setValue(float value, int index) {
		switch(index) {
			case 0: x = value;
			case 1: y = value;
			case 2: z = value;
			case 3: w = value;
		}
		return this;
	}
	
	public Vector4 add(Vector4 v) {
		x += v.x;
		y += v.y;
		z += v.z;
		w += v.w;
		return this;
	}
	
	public Vector4 sub(Vector4 v) {
		x -= v.x;
		y -= v.y;
		z -= v.z;
		w -= v.w;
		return this;
	}
	
	public Vector4 mul(Vector4 v) {
		x *= v.x;
		y *= v.y;
		z *= v.z;
		w *= v.w;
		return this;
	}
	
	public Vector4 mul(float s) {
		x *= s;
		y *= s;
		z *= s;
		w *= s;
		return this;
	}
	
	public Vector4 div(Vector4 v) {
		x /= v.x;
		y /= v.y;
		z /= v.z;
		w /= v.w;
		return this;
	}
	
	public Vector4 div(float s) {
		x /= s;
		y /= s;
		z /= s;
		w /= s;
		return this;
	}
	
	public Vector4 normalize() {
		float len = len();
		x /= len;
		y /= len;
		z /= len;
		w /= len;
		return this;
	}
	
	public Vector4 proj(Vector4 v) {
		load(v.clone().normalize().mul(dot(this, v)));
		return this;
	}
	
	public Vector4 load(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}
	
	public Vector4 load(Vector4 v) {
		return load(v.x, v.y, v.z, v.w);
	}
	
	public float len() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}
	
	public float[] asArray() {
		return new float[] {x, y, z, w};
	}
	
	@Override
	public Vector4 clone() {
		return new Vector4(this);
	}
	
	public Vector3 xyz() {
		return new Vector3(x, y, z);
	}
	
	public Vector3 yzw() {
		return new Vector3(y, z, w);
	}
	
	public Vector2 xy() {
		return new Vector2(x, y);
	}
	
	public Vector2 yz() {
		return new Vector2(y, z);
	}
	
	public Vector2 zw() {
		return new Vector2(z, w);
	}
	
	public static Vector4 add(Vector4 v1, Vector4 v2) {
		return v1.clone().add(v2);
	}
	
	public static Vector4 sub(Vector4 v1, Vector4 v2) {
		return v1.clone().sub(v2);
	}
	
	public  static Vector4 mul(Vector4 v1, Vector4 v2) {
		return v1.clone().mul(v2);
	}
	
	public  static Vector4 mul(Vector4 v, float s) {
		return v.clone().mul(s);
	}
	
	public  static Vector4 div(Vector4 v1, Vector4 v2) {
		return v1.clone().div(v2);
	}
	
	public  static Vector4 div(Vector4 v, float s) {
		return v.clone().div(s);
	}
	
	public static Vector4 normalize(Vector4 v) {
		return v.clone().normalize();
	}
	
	public static boolean equal(Vector4 v1, Vector4 v2) {
		return Math.abs(v2.x - v1.x) < TOLERANCE &&
			   Math.abs(v2.y - v1.y) < TOLERANCE &&
			   Math.abs(v2.z - v1.z) < TOLERANCE &&
			   Math.abs(v2.w - v1.w) < TOLERANCE;
	}
	
	public static Vector4 proj(Vector4 v1, Vector4 v2) {
		return v1.clone().proj(v2);
	}
	
	public static float dist(Vector4 v1, Vector4 v2) {
		return v1.clone().sub(v2).len();
	}
	
	public static float dot(Vector4 v1, Vector4 v2) {
		return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
	}
	
	public static float angle(Vector4 v1, Vector4 v2) {
		return (float) Math.acos(dot(v1, v2) / (v1.len() * v2.len()));
	}
}