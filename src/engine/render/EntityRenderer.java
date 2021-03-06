package engine.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.Map;
import java.util.Set;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import engine.World;
import engine.entity.Entity;
import engine.model.Material;
import engine.model.Mesh;
import engine.model.Texture;
import engine.render.light.DirectionalLight;
import engine.temp.Shapes;
import engine.util.Colour;
import engine.util.math.Matrix;

public class EntityRenderer extends Renderer {
	
	private static final String VERTEX_SHADER   = "src/engine/render/shader/world_vertex.shdr",
								FRAGMENT_SHADER = "src/engine/render/shader/world_fragment.shdr";
	
	private static final float FOV       = 90.0F,
			   				   NEAR_CLIP = 0.1F,
			   				   FAR_CLIP  = 1000.0F;
	
	private static final int NUM_LIGHTS = 4;
	
	private Camera camera;
	private World world;
	private Viewport viewport;
	
	private Map<Mesh, Map<Texture, Set<Entity>>> entities;
	
	public EntityRenderer(Camera camera) {
		this(camera, new Viewport());
	}
	
	public EntityRenderer(Camera camera, Viewport viewport) {
		super(VERTEX_SHADER, FRAGMENT_SHADER);
		this.camera = camera;
		world = camera.getWorld();
		entities = world.getRenderIndexedEntities();
		this.viewport = viewport;
	}
	
	@Override
	protected void bindAttribs() {
		bindAttrib(0, "vertex");
		bindAttrib(1, "normal");
		bindAttrib(2, "texmap");
	}
	
	@Override
	protected void init() {
		
		Shapes.CUBE.getClass();
		
		loadProjection();
		
		for(int i = 0; i < NUM_LIGHTS; i++) {
			setUniform("lights[" + i + "].colour", Colour.BLACK.asVector());
			setUniform("lights[" + i + "].direction", new Vector3f(0.0F, 0.0F, -1.0F));
		}
	}
	
	@Override
	protected void onWindowResize() { loadProjection(); }
	
	private void loadProjection() {
		
		float aspect = getAspectRatio();
		
		Matrix4f perspective = Matrix.projection(FOV, aspect, NEAR_CLIP, FAR_CLIP);
		Matrix4f viewport = this.viewport.getMatrix();
		
		Matrix4f projection = new Matrix4f();
		Matrix4f.mul(viewport, perspective, projection);
		
		setUniform("projection", projection);
	}
	
	private float getAspectRatio() {
		
		float viewWidth = viewport.X2 - viewport.X1;
		float viewHeight = viewport.Y2 - viewport.Y1;
		return (Display.getWidth() * viewWidth) / (Display.getHeight() * viewHeight);
	}
	
	@Override
	protected void render() {
		
		loadFrame();
		
		for(Mesh mesh : entities.keySet()) {
			Map<Texture, Set<Entity>> meshGroup = entities.get(mesh);
			
			loadMesh(mesh);
			
			for(Texture texture : entities.get(mesh).keySet()) {
				Set<Entity> textureGroup = meshGroup.get(texture);
				
				loadTexture(texture);
				
				for(Entity entity : textureGroup) {
					
					renderEntity(entity);
				}
			}
			unloadMesh();
		}
	}
	
	private void renderEntity(Entity e) {
		
		setUniform("model", e.getTransform().asMatrix());
		loadMaterial(e.getModel().getMaterial());
		glDrawArrays(GL_TRIANGLES, 0, e.getModel().getMesh().getNumVertices());
	}
	
	private void loadMaterial(Material m) {
		
		setUniform("material.colour", m.getColour().asVector());
		setUniform("material.ambientLight", m.getAmbientLight());
		setUniform("material.diffuseLight", m.getDiffuseLight());
		setUniform("material.specularLight", m.getSpecularLight());
		setUniform("material.specularDamping", m.getSpecularDamping());
	}
	
	private void loadFrame() {
		
		setUniform("view", camera.getViewMatrix());
		
		for(int i = 0; i < world.getLights().size(); i++) {
			
			setUniform("lights[" + i + "].colour",
					world.getLights().get(i).getColour().asVector());
			
			setUniform("lights[" + i + "].direction",
					((DirectionalLight) world.getLights().get(i)).getDirection());
		}
	}
	
	private void loadMesh(Mesh m) {
		glBindVertexArray(m.getVaoId());
		for(int i = 0; i < 3; i++) {
			glEnableVertexAttribArray(i);
		}
	}
	
	private void unloadMesh() {
		for(int i = 0; i < 3; i++) {
			glDisableVertexAttribArray(i);
		}
		glBindVertexArray(0);
	}
	
	private void loadTexture(Texture t) {
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, t.getTextureId());
		
		if(t.isOpaque()) glEnable(GL_CULL_FACE);
		else glDisable(GL_CULL_FACE);
	}
}