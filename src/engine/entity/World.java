package engine.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import engine.model.Mesh;
import engine.model.Texture;
import engine.render.light.Light;

public class World {
	
	private Set<Entity> entities = new HashSet<>();
	private Map<Mesh, Map<Texture, Set<Entity>>> indexedEntities = new HashMap<>();
	
	private List<Light> lights = new ArrayList<>();
	
	public Map<Mesh, Map<Texture, Set<Entity>>> getRenderIndexedEntities() {
		return indexedEntities;
	}
	
	public void addEntity(Entity entity) {
		
		entities.add(entity);
		
		Mesh mesh = entity.getModel().getMesh();
		Texture texture = entity.getModel().getMaterial().getTexture();
		
		if(!indexedEntities.containsKey(mesh))
			indexedEntities.put(mesh, new HashMap<>());
		
		Map<Texture, Set<Entity>> meshGroup = indexedEntities.get(mesh);
		
		if(!meshGroup.containsKey(texture))
			meshGroup.put(texture, new HashSet<>());
		
		Set<Entity> textureGroup = meshGroup.get(texture);
		textureGroup.add(entity);
		
	}
	
	public void removeEntity(Entity entity) {
		
		entities.remove(entity);
		
		Mesh mesh = entity.getModel().getMesh();
		Texture texture = entity.getModel().getMaterial().getTexture();
		
		if(indexedEntities.containsKey(mesh)) {
			
			Map<Texture, Set<Entity>> meshGroup = indexedEntities.get(mesh);
			
			if(meshGroup.containsKey(texture)) {
				
				Set<Entity> textureGroup = meshGroup.get(texture);
				textureGroup.remove(entity);
			}
		}
	}
	
	public void addLight(Light l) { lights.add(l); }
	
	public void removeLight(Light l) { lights.remove(l); }
	
	public List<Light> getLights() { return lights; }
}