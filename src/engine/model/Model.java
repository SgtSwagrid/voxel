package engine.model;

import java.util.function.Function;

public class Model {
	
	private Mesh mesh;
	
	private Material material;
	
	public Model(Mesh mesh, Material material) {
		this.mesh = mesh;
		this.material = material;
	}
	
	public Mesh getMesh() { return mesh; }
	
	public Model withMesh(Mesh mesh) {
		Model m = clone(); m.mesh = mesh; return m;
	}
	
	public Material getMaterial() { return material; }
	
	public Model withMaterial(Material material) {
		Model m = clone(); m.material = material; return m;
	}
	
	public Model editMaterial(Function<Material, Material> modification) {
		return withMaterial(modification.apply(material));
	}
	
	@Override
	public Model clone() {
		return new Model(mesh, material);
	}
}