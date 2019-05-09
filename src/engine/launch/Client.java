package engine.launch;

import org.lwjgl.util.vector.Vector3f;

import engine.World;
import engine.entity.Wall;
import engine.event.Event;
import engine.event.input.Input;
import engine.render.Camera;
import engine.render.EntityRenderer;
import engine.render.Window;
import engine.render.light.DirectionalLight;
import engine.util.Colour;
import engine.util.Timing;
import engine.util.Timing.GameTickEvent;

public class Client {
	
	public static final World WORLD = new World();
	public static final Camera CAMERA = new Camera(WORLD);
	public static final EntityRenderer ENTITY_RENDERER = new EntityRenderer(CAMERA);
	
	public static void main(String[] args) {
		
		Window window = new Window("Engine");
		window.setColour(Colour.TEAL);
		window.addRenderer(ENTITY_RENDERER);
		window.open();
		
		Input.init(window);
		Timing.init(window);
		
		CAMERA.getTransform().setPosition(new Vector3f(0.0F, 0.0F, 10.0F));
		Wall cube = new Wall(WORLD);
		new DirectionalLight(WORLD, Colour.WHITE, new Vector3f(0.0F, -1.0F, -1.0F));
		
		Event.addHandler(GameTickEvent.class, e ->
				cube.getTransform().rotate(new Vector3f(0.5F, 0.0F, 0.0F)));
	}
}