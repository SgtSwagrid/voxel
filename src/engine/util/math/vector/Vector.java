package engine.util.math.vector;

public class Vector {
	
	private static final float TOLERANCE = 0.00001F;
	
	private final int size;
	
	private float[] A;
	
	public Vector(int size) {
		
		if(size <= 0)
			throw new IllegalArgumentException("Vector size must be positive.");
		
		this.size = size;
		A = new float[size];
	}
	
	public Vector(Vector v) {
		this(v.size);
		load(v);
	}
	
	public Vector(float... a) {
		this(a.length);
		load(a);
	}
	
	public float getValue(int index) { return A[index]; }
	
	public Vector setValue(int index, float value) {
		A[index] = value;
		return this;
	}
	
	public int getSize() { return size; }
	
	//...
	
	public Vector load(Vector v) { return load(v.A); }
	
	public Vector load(float... a) {
		checkCompatibility(size, a.length);
		A = a.clone();
		return this;
	}
	
	public float[] asArray() { return A.clone(); }
	
	@Override
	public Vector clone() { return new Vector(this); }
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Vector)) return false;
		else return equal(this, (Vector) o);
	}
	
	@Override
	public String toString() {
		
		String str = "[";
		
		for(int i = 0; i < size; i++) {
			str += A[i] + (i != size - 1 ? ", " : "]\n");
		}
		return str;
	}
	
	//...
	
	public static boolean equal(Vector v1, Vector v2) {
		
		if(v1.size != v2.size) return false;
		
		for(int i = 0; i < v1.size; i++) {
			
			if(Math.abs(v1.A[i] - v2.A[i]) > TOLERANCE)
				return false;
		}
		return true;
	}
	
	private static void checkCompatibility(int size1, int size2) {
		if(size1 != size2)
			throw new IllegalArgumentException("Vectors must be of equal size.");
	}
}
