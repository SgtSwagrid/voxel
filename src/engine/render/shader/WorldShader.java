package engine.render.shader;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import engine.util.math.Matrix;
import engine.entity.Entity;
import engine.event.Event;
import engine.model.Material;
import engine.model.Mesh;
import engine.model.Texture;
import engine.render.Camera;
import engine.render.Window.WindowResizeEvent;
import engine.render.light.DirectionalLight;
import engine.render.light.Light;
import engine.util.Colour;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;
import java.util.List;

public class WorldShader extends Shader {
	
	private static final float FOV       = 90.0F,
			   				   NEAR_CLIP = 0.1F,
			   				   FAR_CLIP  = 1000.0F;
	
	private static final String VERTEX_SHADER   = "src/engine/render/shader/world_vertex.shdr",
								FRAGMENT_SHADER = "src/engine/render/shader/world_fragment.shdr";
	
	private static final int NUM_LIGHTS = 4;
	
	public static final WorldShader INSTANCE = new WorldShader();
	
	/** List of lights present in the scene. */
	private static volatile List<Light> lights = new ArrayList<>();
	
	private WorldShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER);
	}
	
	@Override
	protected void bindAttribs() {
		bindAttrib(0, "vertex");
		bindAttrib(1, "normal");
		bindAttrib(2, "texmap");
		
	}

	@Override
	protected void init() {
		
		loadProjection();
		Event.addHandler(WindowResizeEvent.class, e -> loadProjection());
		
		for(int i = 0; i < NUM_LIGHTS; i++) {
			setUniform("lights[" + i + "].colour", Colour.BLACK.asVector());
			setUniform("lights[" + i + "].direction", new Vector3f(0.0F, 0.0F, -1.0F));
		}
	}
	
	private void loadProjection() {
		float aspect = (float) Display.getWidth() / (float) Display.getHeight();
		setUniform("projection", Matrix.projection(FOV, aspect, NEAR_CLIP, FAR_CLIP));
	}
	
	@Override
	protected void loadFrame(Camera c) {
		setUniform("view", c.getViewMatrix());
		for(int i = 0; i < lights.size(); i++) {
			setUniform("lights[" + i + "].colour", lights.get(i).getColour().asVector());
			setUniform("lights[" + i + "].direction", ((DirectionalLight) lights.get(i)).getDirection());
		}
		
	}
	
	@Override
	protected void onWindowResize(int width, int height) {
		float aspect = (float) width / (float) height;
		setUniform("projection", Matrix.projection(FOV, aspect, NEAR_CLIP, FAR_CLIP));
	}

	@Override
	public void loadMesh(Mesh m) {
		glBindVertexArray(m.getVaoId());
		for(int i = 0; i < 3; i++) {
			glEnableVertexAttribArray(i);
		}
	}

	@Override
	public void unloadMesh() {
		for(int i = 0; i < 3; i++) {
			glDisableVertexAttribArray(i);
		}
		glBindVertexArray(0);
	}

	@Override
	public void loadTexture(Texture t) {
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D,t.getTextureId());
		
		if(t.isOpaque()) glEnable(GL_CULL_FACE);
		else glDisable(GL_CULL_FACE);
	}
	
	public void render(Entity e) {
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
	
	/**
	 * Add a light to the scene.
	 */
	public void addLight(Light l) {
		lights.add(l);
	}
	
	/**
	 * Remove a light from the scene.
	 */
	public void removeLight(Light l) {
		lights.remove(l);
	}
}