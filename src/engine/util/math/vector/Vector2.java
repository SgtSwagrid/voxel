package engine.util.math.vector;

public class Vector2 {
	
	private static final float TOLERANCE = 0.00001F;
	
	public float x = 0, y = 0;
	
	public Vector2() {}
	
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2(float[] a) {
		x = a[0];
		y = a[1];
	}
	
	public Vector2(Vector2 v) {
		x = v.x;
		y = v.y;
	}
	
	public float getX() { return x; }
	
	public Vector2 setX(float x) {
		this.x = x;
		return this;
	}
	
	public float getY() { return y; }
	
	public Vector2 setY(float y) {
		this.y = y;
		return this;
	}
	
	public float getValue(int index) {
		switch(index) {
			case 0: return x;
			case 1: return y;
			default: return 0;
		}
	}
	
	public Vector2 setValue(float value, int index) {
		switch(index) {
			case 0: x = value;
			case 1: y = value;
		}
		return this;
	}
	
	public Vector2 add(Vector2 v) {
		x += v.x;
		y += v.y;
		return this;
	}
	
	public Vector2 sub(Vector2 v) {
		x -= v.x;
		y -= v.y;
		return this;
	}
	
	public Vector2 mul(Vector2 v) {
		x *= v.x;
		y *= v.y;
		return this;
	}
	
	public Vector2 mul(float s) {
		x *= s;
		y *= s;
		return this;
	}
	
	public Vector2 div(Vector2 v) {
		x /= v.x;
		y /= v.y;
		return this;
	}
	
	public Vector2 div(float s) {
		x /= s;
		y /= s;
		return this;
	}
	
	public Vector2 normalize() {
		float len = len();
		x /= len;
		y /= len;
		return this;
	}
	
	public Vector2 proj(Vector2 v) {
		load(v.clone().normalize().mul(dot(this, v)));
		return this;
	}
	
	public Vector2 load(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}
	
	public Vector2 load(Vector2 v) {
		return load(v.x, v.y);
	}
	
	public float len() {
		return (float) Math.sqrt(x * x + y * y);
	}
	
	public float[] asArray() {
		return new float[] {x, y};
	}
	
	@Override
	public Vector2 clone() {
		return new Vector2(this);
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
	
	public static boolean equal(Vector2 v1, Vector2 v2) {
		return Math.abs(v2.x - v1.x) < TOLERANCE &&
			   Math.abs(v2.y - v1.y) < TOLERANCE;
	}
	
	public static Vector2 proj(Vector2 v1, Vector2 v2) {
		return v1.clone().proj(v2);
	}
	
	public static float dist(Vector2 v1, Vector2 v2) {
		return v1.clone().sub(v2).len();
	}
	
	public static float dot(Vector2 v1, Vector2 v2) {
		return v1.x * v2.x + v1.y * v2.y;
	}
	
	public static float angle(Vector2 v1, Vector2 v2) {
		return (float) Math.acos(dot(v1, v2) / (v1.len() * v2.len()));
	}
}