package engine.launch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import engine.entity.Wall;
import engine.entity.World;
import engine.event.Event;
import engine.event.input.Input;
import engine.render.Camera;
import engine.render.Window;
import engine.render.light.DirectionalLight;
import engine.render.shader.EntityShader;
import engine.util.Colour;
import engine.util.Timing;
import engine.event.input.InputEvent.KeyboardEvent.KeyPressEvent;

public class Client {
	
	public static final World WORLD = new World();
	public static final Camera CAMERA = new Camera(WORLD);
	public static final EntityShader ENTITY_SHADER = new EntityShader(CAMERA);
	
	public static void main(String[] args) {
		
		Window window = new Window("Engine");
		window.setColour(Colour.TEAL);
		
		window.addShader(ENTITY_SHADER);
		
		while(!window.isOpen()) {}
		
		Event.addHandler(KeyPressEvent.class, e -> {
			if(e.KEY_ID == Keyboard.KEY_F) {
				System.out.println("Press F to pay respects.");
			}
		});
		
		CAMERA.getTransform().setPosition(new Vector3f(0.0F, 0.0F, 10.0F));
		
		Wall w = new Wall(WORLD);
		
		WORLD.addLight(new DirectionalLight(Colour.WHITE, new Vector3f(0.0F, -1.0F, -1.0F)));
		
		while(window.isOpen()) {
			Timing.update();
			Input.update(window);
			
			w.getTransform().rotate(new Vector3f(0.000005F, 0.0F, 0.0F));
		}
	}
}