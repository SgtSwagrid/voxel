package engine.entity;

import engine.World;
import engine.model.Material;
import engine.model.Model;
import engine.model.Texture;
import engine.temp.Shapes;
import engine.util.Colour;
import engine.util.math.Transform;

public class Wall extends Entity {
	
	private static final Model MODEL = new Model(
			Shapes.CUBE,
			Material.SHINE
				.withTexture(Texture.getTexture("res/texture/glacier.png"),
						Colour.WHITE)
	);
	
	public Wall(World world) { super(world, MODEL); }
	
	public Wall(World world, Transform transform) { super(world, MODEL, transform); }
}