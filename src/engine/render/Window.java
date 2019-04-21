package engine.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import engine.util.Colour;
import engine.event.Event;
import engine.event.input.Input;

public class Window {
	
	private static final int SCREEN_WIDTH, SCREEN_HEIGHT;
	
	private List<Renderer> renderers = new LinkedList<>();
	
	private volatile int
			width  = SCREEN_WIDTH,
			height = SCREEN_HEIGHT,
			x      = SCREEN_WIDTH / 2,
			y      = SCREEN_HEIGHT / 2;
	
	private volatile String title = "";
	
	private volatile boolean open = false, resizable = true,
			moved = false, resized = false, fullscreen = false;
	
	private Colour colour = Colour.BLACK;
	
	private int maxFps = 240;
	
	static {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		SCREEN_WIDTH = (int) screen.getWidth();
		SCREEN_HEIGHT = (int) screen.getHeight();
	}
	
	public Window() {
	}
	
	public Window(int width, int height) {
		setSize(width, height);
	}
	
	public Window(String title) {
		this.title = title;
	}
	
	public Window(int width, int height, String title) {
		setSize(width, height);
		this.title = title;
	}
	
	public void addRenderer(Renderer renderer) {
		
		if(open)
			throw new IllegalStateException("Renderers can't be added after the window is open.");
		
		renderers.add(renderer);
	}
	
	public String getTitle() { return title; }
	
	public void setTitle(String title) { this.title = title; }
	
	public int getWidth() { return width; }
	
	public int getHeight() { return height; }
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		resized = true;
	}
	
	public int getX() { return x; }
	
	public int getY() { return y; }
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		moved = true;
	}
	
	public boolean isOpen() { return open; }
	
	public void close() { open = false; }
	
	public boolean isResizable() { return resizable; }
	
	public void setResizable(boolean resizable) { this.resizable = resizable; };
	
	public boolean isFullscreen() { return fullscreen; }
	
	public void setFullscreen(boolean fullscreen) { this.fullscreen = fullscreen; }
	
	public Colour getColour() { return colour; }
	
	public void setColour(Colour colour) { this.colour = colour; }
	
	public int getMaxFps() { return maxFps; }
	
	public void setMaxFps(int maxFps) { this.maxFps = maxFps; }
	
	public void open() {
		
		new Thread("render") {
			@Override public void run() {
				
				create();
				renderers.forEach(Renderer::doInit);
				open = true;
				
				while(!Display.isCloseRequested() && open) {
					
					update();
					renderers.forEach(Renderer::doRender);
				}
				renderers.forEach(Renderer::destroy);
				
				Display.destroy();
				open = false;
			}
		}.start();
		
		while(!open);
	}
	
	/**
	 * Create a new window ready for OpenGL rendering.
	 * 'update()' must be subsequently called upon each render loop.
	 */
	private void create() {
		
		//Create new OpenGL context to attach to window.
		ContextAttribs context = new ContextAttribs(3, 2)
				.withForwardCompatible(true)
				.withProfileCore(true);
		
		try {
			
			//Create a new window.
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create(new PixelFormat().withSamples(4), context);
			
		} catch(LWJGLException e) {
			e.printStackTrace();	
		}
		
		glEnable(GL_MULTISAMPLE);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
	}
	
	private void update() {
		
		if(Display.getWidth() != width || Display.getHeight() != height) {
			
			if(resized) {
				
				try {
					Display.setDisplayMode(new DisplayMode(width, height));
					resized = false;
				} catch(LWJGLException e) {
					e.printStackTrace();
				}
				
			} else {
				width = Display.getWidth();
				height = Display.getHeight();
			}
			
			glViewport(0, 0, width, height);
			new WindowResizeEvent(width, height);
		}
		
		resized = false;
		
		if(Display.getX() != x || Display.getY() != y) {
			
			if(moved) {
				Display.setLocation(x, y);
				
			} else {
				x = Display.getX();
				y = Display.getY();
			}
		}
		
		moved = false;
		
		if(Display.getTitle() != title) {
			Display.setTitle(title);
		}
		
		if(Display.isResizable() ^ resizable) {
			Display.setResizable(resizable);
		}
		
		if(Display.isFullscreen() ^ fullscreen) {
			try {
				Display.setFullscreen(fullscreen);
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
		}
		
		Display.sync(maxFps);
		Display.update();
		
		glEnable(GL_DEPTH_TEST);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 
		glClearColor(colour.R, colour.G, colour.B, colour.A);
	}
	
	public static class WindowResizeEvent extends Event {
		
		public final int WIDTH, HEIGHT;
		
		private WindowResizeEvent(int width, int height) {
			WIDTH = width;
			HEIGHT = height;
			trigger();
		}
	}
}