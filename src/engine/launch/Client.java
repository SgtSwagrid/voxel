package engine.launch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import engine.entity.Wall;
import engine.event.Event;
import engine.event.input.Input;
import engine.render.Camera;
import engine.render.UIRenderer;
import engine.render.Window;
import engine.render.WorldRenderer;
import engine.render.light.DirectionalLight;
import engine.render.shader.WorldShader;
import engine.util.Colour;
import engine.util.Timing;
import engine.util.math.vector.Matrix;
import engine.event.input.InputEvent.KeyboardEvent.KeyPressEvent;

public class Client {
	
	public static final Camera CAMERA = new Camera();
	
	public static void main(String[] args) {
		
		Matrix m = new Matrix(2, 3, 0, 1, 2, -3, 4, -5, 1);
		System.out.println(Matrix.mul(m, Matrix.invert(m)));
		
		/*Window window = new Window("Engine");
		window.setColour(Colour.TEAL);
		
		window.addRenderer(UIRenderer.INSTANCE);
		window.addRenderer(WorldRenderer.INSTANCE);
		
		while(!window.isOpen()) {}
		
		Event.addHandler(KeyPressEvent.class, e -> {
			if(e.KEY_ID == Keyboard.KEY_F) {
				System.out.println("Press F to pay respects.");
			}
		});
		
		CAMERA.getTransform().setPosition(new Vector3f(0.0F, 0.0F, 10.0F));
		
		Wall w = new Wall();
		
		WorldShader.INSTANCE.addLight(new DirectionalLight(Colour.WHITE, new Vector3f(0.0F, -1.0F, -1.0F)));
		
		while(window.isOpen()) {
			Timing.update();
			Input.update(window);
			
			w.getTransform().rotate(new Vector3f(0.000005F, 0.0F, 0.0F));
		}*/
	}
}