package com.martincarney.model.brick;

import java.awt.Color;

import com.martincarney.model.shared.Dimension;

/**
 * Represents a building brick. This interface should not be used directly in an implementation;
 * use {@link BrickInstance} or {@link BrickPrototype} (or both) instead.
 * @author Martin Carney 2015
 *
 */
public interface Brick {
	
	/**
	 * Gets the outer bounding dimensions of the brick, which can be used for preliminary collision detection
	 * and for verifying a structure is valid.
	 */
	public Dimension getDimensions();
	
	/**
	 * Gets the base color for the brick, which is used when rendering the brick's visual representation.
	 * @return
	 */
	public Color getBaseColor();
	
}
