package com.martincarney.view.renderer;

import java.awt.Color;

/**
 * Constants used by {@link BrickRenderer BrickRenderers} to determine how to place and draw
 * bricks on screen in the correct positions.
 * @author Martin Carney 2015
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
	
	public static final int SCREEN_STUD_WIDTH = 10;
	
	public static final int SCREEN_STUD_TOP_HEIGHT = 5;
	
	public static final int SCREEN_STUD_Z_HEIGHT = 3;
	
	public static final int SCREEN_STUD_X_OFFSET = ((SCREEN_X_JOG * 2) - SCREEN_STUD_WIDTH) / 2;
	
	public static final int SCREEN_STUD_Y_OFFSET = ((SCREEN_Y_JOG * 2) - SCREEN_STUD_TOP_HEIGHT) / 2;
	
	/**
	 * A set of standard colors approximately matching same name-brand plastic building blocks' colors.
	 * These can be referenced for procedurally-drawn bricks.
	 */
	public static final Color[] BRICK_COLORS = new Color[] {
		Color.decode("#AA0000"),
		Color.decode("#007700"),
		Color.decode("#0000BB"),
		Color.decode("#DDDDDD"),
		Color.decode("#999999"),
		Color.decode("#666666"),
		Color.decode("#333333"),
		Color.decode("#733E29"),
		Color.decode("#93B8D9"),
		Color.decode("#E8D60C"),
		Color.decode("#E67300"),
		Color.decode("#00AA00")
	};

	public static final int C_RED = 0;
	public static final int C_GREEN = 1;
	public static final int C_BLUE = 2;
	public static final int C_WHITE = 3;
	public static final int C_LIGHT_GREY = 4;
	public static final int C_DARK_GREY = 5;
	public static final int C_BLACK = 6;
	public static final int C_BROWN = 7;
	public static final int C_MEDIUM_BLUE = 8;
	public static final int C_YELLOW = 9;
	public static final int C_BRIGHT_GREEN = 10;
	// TODO: pink, dark purple, medium lavender, lime, pale yellow,
	// pale orange, tan, dark tan, dark red/maroon, dark blue,
	// sand green, olive green, dark green, medium azure,
	// maersk blue, magenta, dark pink, bright pink, light aqua,
	// light & dark grey (tan-ish vs blue-ish), dark orange
	// med dk flesh
	// SEE: http://www.bricksrkids.com/images/legocolors.jpg
	// SEE: https://www.bricklink.com/catalogColors.asp
}
