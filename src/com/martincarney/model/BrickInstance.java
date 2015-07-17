package com.martincarney.model;

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
	
}
