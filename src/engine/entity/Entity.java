package engine.entity;

import engine.World;
import engine.model.Model;
import engine.util.math.Transform;

public abstract class Entity {
	
	private World world;
	
	private Transform transf = new Transform();
	
	private Model model;
	
	public Entity(World world, Model model) {
		this(world, model, new Transform());
	}
	
	public Entity(World world, Model model, Transform transform) {
		this.world = world;
		this.model = model;
		world.addEntity(this);
		transf.loadTransformation(transform);
	}
	
	public Model getModel() { return model; }
	
	public Transform getTransform() { return transf; }
	
	public void delete() { world.removeEntity(this); }
}