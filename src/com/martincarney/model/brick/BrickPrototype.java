package com.martincarney.model.brick;

/**
 * A brick which represents a reusable prototype for individual brick instances,
 * and can generate such instances.
 * @author Martin Carney 2015
 *
 */
public interface BrickPrototype extends Brick {
	
	/**
	 * Generates an instance of this prototype which can be placed and moved in the world
	 * coordinate system.
	 */
	public BrickInstance createInstance();
}
