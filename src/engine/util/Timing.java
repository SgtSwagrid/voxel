package engine.util;

import engine.event.Event;
import engine.render.Window;

/**
 * Helper class featuring timing-related utilities and events.
 * @author Alec
 */
public class Timing {
	
	/** The target time in milliseconds between ticks. */
	public static final double TICK_LENGTH_MS = 20.0;
	
	/** The time at which the previous (most recent) tick occurred. */
	private static double lastTick = getTime();
	
	
	/** The difference between the local time and server time. */
	private static long offset = 0;
	
	/**
	 * Get the current time in milliseconds.
	 * This time is synced with any relevant servers and peers.
	 * As such, 'System.nanoTime()' shouldn't be used.
	 * @return relative time (ms).
	 */
	public static double getTime() {
		return (System.nanoTime() + offset) / 1000000.0;
	}
	
	/**
	 * 
	 * @param window
	 */
	public static void init(Window window) {
		
		new Thread("game-tick") {
			@Override public void run() {
				
				while(window.isOpen()) {
					
					//Trigger the event if a sufficient time has passed.
					if(getTime() - lastTick >= TICK_LENGTH_MS) {
						new GameTickEvent(getTime() - lastTick);
						lastTick += TICK_LENGTH_MS;
					}
				}
			}
		}.start();
	}
	
	public static class GameTickEvent extends Event {
		
		/**
		 * The amount of time which has passed (in milliseconds) since the previous tick.
		 * This may not be exactly equal to TICK_LENGTH_MS (although it should be close).
		 */
		public final double DELTA;
		
		/**
		 * Constructs a new GameTickEvent with the given delta time.
		 * @param delta the time passed since the previous such event.
		 */
		private GameTickEvent(double delta) {
			DELTA = delta;
			trigger();
		}
	}
}