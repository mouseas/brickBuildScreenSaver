package com.martincarney.view.renderer;

import java.awt.Color;

/**
 * Constants used by {@link BrickRenderer BrickRenderers} to determine how to place and draw
 * bricks on screen in the correct positions.
 * @author Martin Carney
 */
public abstract class RendererConstants {
	
	/** number of pixels left or right that represents 1 brick square */
	public static final int SCREEN_X_JOG = 10;
	
	/** number of pixels up or down that represents 1 brick square */
	public static final int SCREEN_Y_JOG = 5;
	
	/**
	 * number of pixels that represent 1 brick's height. Note that a "flat" brick is 1
	 * unit tall, while a "normal" brick is actually 3 units tall.
	 */
	public static final int SCREEN_Z_JOG = 5;
	
	/**
	 * A set of standard colors approximately matching same name-brand plastic building blocks' colors.
	 * These can be referenced for procedurally-drawn bricks.
	 */
	public static final Color[] BRICK_COLORS = new Color[] {
		Color.decode("#AA0000"),
		Color.decode("#007700"),
		Color.decode("#0000BB"),
		Color.decode("#DDDDDD"),
		Color.decode("#AAAAAA"),
		Color.decode("#666666"),
		Color.decode("#333333"),
		Color.decode("#733E29"),
		Color.decode("#93B8D9"),
		Color.decode("#E8D60C"),
		Color.decode("#E67300"),
		Color.decode("#00AA00")
	};
}
