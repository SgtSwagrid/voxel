package engine.entity;

import engine.model.Model;
import engine.util.math.Transform;

public abstract class Entity {
	
	private Transform transf = new Transform();
	
	private Model model;
	
	public Entity(World world, Model model) {
		this.model = model;
		world.addEntity(this);
	}
	
	public Entity(World world, Model model, Transform transform) {
		this(world, model);
		transf.loadTransformation(transform);
	}
	
	public Model getModel() { return model; }
	
	public Transform getTransform() { return transf; }
}