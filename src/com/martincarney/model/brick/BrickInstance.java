package com.martincarney.model.brick;

import com.martincarney.model.World;
import com.martincarney.view.renderer.BrickRenderer;

/**
 * Represents an instance of a Brick, which has a changeable (x, y, z) world position
 * @author Martin Carney 2015
 */
public interface BrickInstance extends Brick {
	
	/** Gets the x position of the brick. */
	public int getX();

	/** Gets the y position of the brick. */
	public int getY();

	/** Gets the y position of the brick. */
	public int getZ();

	/** Sets the x position of the brick. */
	public void setX(int newX);
	
	/** Sets the y position of the brick. */
	public void setY(int newY);
	
	/** Sets the z position of the brick. */
	public void setZ(int newZ);
	
	/**
	 * Checks whether the brick has been drawn this draw cycle.
	 * @return {@code true} if the brick has been drawn this draw cycle, {@code false} if it still needs
	 * to be drawn.
	 */
	public boolean hasRendered();
	
	/**
	 * Sets whether the brick has been drawn this draw cycle. {@link World} sets this to {@code false}
	 * on every brick at the start of a draw cycle, and {@link BrickRenderer} sets it to {@code true}
	 * when it draws the brick during a draw cycle.
	 * @param rendered Whether the brick has been drawn this draw cycle.
	 */
	public void setRendered(boolean rendered);
	
}
