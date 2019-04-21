package engine.entity;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import engine.event.Event;
import engine.event.input.InputEvent.KeyboardEvent.KeyHoldEvent;
import engine.event.input.InputEvent.MouseEvent.MouseMoveEvent;
import engine.model.Material;
import engine.model.Model;
import engine.temp.Shapes;
import engine.util.Colour;
import engine.util.math.Matrix;

public class LocalPlayer extends Entity {
	
	private static final Model MODEL = new Model(
			Shapes.CUBE,
			Material.SHINE
				.withColour(Colour.GREEN)
	);
	
	private static final float MOVE_SPEED = 0.5F;
	
	public LocalPlayer() {
		
		super(MODEL);
		
		Event.addHandler(KeyHoldEvent.class, e -> {
			switch(e.KEY_NAME) {
				case "W":
					getTransform().translate(new Vector3f(0.0F, MOVE_SPEED, 0.0F)); break;
				case "A":
					getTransform().translate(new Vector3f(-MOVE_SPEED, 0.0F, 0.0F)); break;
				case "S":
					getTransform().translate(new Vector3f(0.0F, -MOVE_SPEED, 0.0F)); break;
				case "D":
					getTransform().translate(new Vector3f(MOVE_SPEED, 0.0F, 0.0F)); break;
				case "F":
					getTransform().loadMatrix(Matrix.remRotation(getTransform().asMatrix()));
			}
		});
		
		Event.addHandler(MouseMoveEvent.class, e -> {
			int cx = Display.getWidth() / 2;
			int cy = Display.getHeight() / 2;
			int rx = e.MOUSE_X - cx;
			int ry = e.MOUSE_Y - cy;
			float a = (float) (180 / Math.PI * Math.atan2(ry, rx)) - 90.0F;
			getTransform().setOrientation(new Vector3f(0.0F, 0.0F, a));
		});
	}
}