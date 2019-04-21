package engine.render;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import engine.entity.Entity;
import engine.launch.Client;
import engine.model.Mesh;
import engine.model.Texture;
import engine.render.shader.WorldShader;
import engine.temp.Shapes;

public class WorldRenderer implements Renderer {
	
	public static WorldRenderer INSTANCE = new WorldRenderer();
	
	private Map<Mesh, Map<Texture, Set<Entity>>> entities = new HashMap<>();

	@Override
	public void init() {
		Shapes.CUBE.getClass();
		WorldShader.INSTANCE.getClass();
	}

	@Override
	public void render() {
		
		WorldShader.INSTANCE.start(Client.CAMERA);
		
		for(Mesh mesh : entities.keySet()) {
			Map<Texture, Set<Entity>> meshGroup = entities.get(mesh);
			
			WorldShader.INSTANCE.loadMesh(mesh);
			
			for(Texture texture : entities.get(mesh).keySet()) {
				Set<Entity> textureGroup = meshGroup.get(texture);
				
				WorldShader.INSTANCE.loadTexture(texture);
				
				for(Entity entity : textureGroup) {
					
					WorldShader.INSTANCE.render(entity);
				}
			}
		}	
		WorldShader.INSTANCE.stop();
	}

	@Override
	public void stop() {
		WorldShader.INSTANCE.destroy();
	}
	
	public void addEntity(Entity entity) {
		
		Mesh mesh = entity.getModel().getMesh();
		Texture texture = entity.getModel().getMaterial().getTexture();
		
		if(!entities.containsKey(mesh))
			entities.put(mesh, new HashMap<>());
		
		Map<Texture, Set<Entity>> meshGroup = entities.get(mesh);
		
		if(!meshGroup.containsKey(texture))
			meshGroup.put(texture, new HashSet<>());
		
		Set<Entity> textureGroup = meshGroup.get(texture);
		textureGroup.add(entity);
	}
	
	public void removeEntity(Entity entity) {
		
		Mesh mesh = entity.getModel().getMesh();
		Texture texture = entity.getModel().getMaterial().getTexture();
		
		if(entities.containsKey(mesh)) {
			
			Map<Texture, Set<Entity>> meshGroup = entities.get(mesh);
			
			if(meshGroup.containsKey(texture)) {
				
				Set<Entity> textureGroup = meshGroup.get(texture);
				textureGroup.remove(entity);
			}
		}
	}	
}