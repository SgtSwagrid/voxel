package engine.entity;

import engine.model.Model;
import engine.render.WorldRenderer;
import engine.util.math.Transform;

public abstract class Entity {
	
	private Transform transf = new Transform();
	
	private Model model;
	
	public Entity(Model model) {
		this.model = model;
		WorldRenderer.INSTANCE.addEntity(this);
	}
	
	public Entity(Model model, Transform transform) {
		this(model);
		transf.loadTransformation(transform);
	}
	
	public Model getModel() { return model; }
	
	public Transform getTransform() { return transf; }
}