package engine.event.input;

import static engine.event.input.InputEvent.KeyboardEvent.*;
import static engine.event.input.InputEvent.MouseEvent.*;
import static engine.event.input.InputEvent.MouseEvent.MouseButtonEvent.*;

import java.util.HashSet;
import java.util.Set;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import engine.event.Event;
import engine.render.Window;
import engine.util.Timing.GameTickEvent;

/**
 * Manages user input, triggering any relevant events when appropriate.
 * Ensure to call update() as often as possible for this to work properly.
 * @author Alec
 */
public class Input {
	
	/** The set of all keys which are currently held down. */
	private static Set<Integer> heldKeys = new HashSet<>();
	
	/** The set of all mouse buttons which are currently held down.  */
	private static Set<Integer> heldButtons = new HashSet<>();
	
	public static void init(Window window) {
		
		try {
			//Initialize keyboard and mouse.
			Keyboard.create();
			Mouse.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		//Trigger appropriate button hold events each tick.
		Event.addHandler(GameTickEvent.class, event -> {
			
			//Trigger key hold events when appropriate.
			for(int key : heldKeys) {
				new KeyHoldEvent(key);
			}
			
			//Trigger click hold events when appropriate.
			for(int button : heldButtons) {
				new ClickHoldEvent(button, Mouse.getX(), Mouse.getY());
			}
		});
		
		new Thread("input") {
			@Override public void run() {
				
				while(window.isOpen()) {
					
					updateKeyboard();
					updateMouseClicks();
					updateCursor();
					updateScrollWheel();
				}
			}
		}.start();
	}
	
	/**
	 * Trigger appropriate events for any keyboard activity.
	 */
	private static void updateKeyboard() {
		
		//For each key which has been pressed since the last update.
		while(Keyboard.next()) {
			
			int key = Keyboard.getEventKey();
			
			//Key pressed.
			if(Keyboard.getEventKeyState()) {
				new KeyPressEvent(key);
				heldKeys.add(key);
				
			//Key released.
			} else {
				new KeyReleaseEvent(key);
				heldKeys.remove(key);
			}
		}
	}
	
	/**
	 * Trigger appropriate events for any mouse button activity.
	 */
	private static void updateMouseClicks() {
		
		//For each mouse button which has been pressed since the last update.
		while(Mouse.next()) {
			
			int button = Mouse.getEventButton();
			
			//Button pressed.
			if(Mouse.getEventButtonState()) {
				new ClickEvent(button, Mouse.getX(), Mouse.getY());
				heldButtons.add(button);
				
			//Button released.
			} else {
				new ClickReleaseEvent(button, Mouse.getX(), Mouse.getY());
				heldButtons.remove(button);
			}
		}
	}
	
	/**
	 * Trigger an appropriate event if the mouse has moved.
	 */
	private static void updateCursor() {
		
		int mouseDX = Mouse.getDX();
		int mouseDY = Mouse.getDY();
		
		if(mouseDX != 0 || mouseDY != 0) {
			new MouseMoveEvent(mouseDX, mouseDY, Mouse.getX(), Mouse.getY());
		}
	}
	
	/**
	 * Trigger an appropriate event if the scroll wheel has moved.
	 */
	private static void updateScrollWheel() {

		int dWheel = Mouse.getDWheel();
		
		if(dWheel != 0) {
			new ScrollWheelEvent(dWheel, Mouse.getX(), Mouse.getY());
		}
	}
}