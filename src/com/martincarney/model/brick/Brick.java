package com.martincarney.model.brick;

import java.awt.Color;

import com.martincarney.model.shared.Dimension;
import com.martincarney.view.renderer.BrickRenderer;

/**
 * Represents a building brick. This interface should not be used directly in an implementation;
 * use {@link BrickInstance} or {@link BrickPrototype} (or both) instead.
 * @author Martin Carney 2015
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
	
	/**
	 * Get the type of Renderer that this brick type can use.
	 * @return {@code Class} that implements {@code BrickRenderer} which can correctly render this type
	 * of brick. Every brick of one type should always return the same BrickRenderer class.
	 */
	public Class<? extends BrickRenderer> getRendererClass();
	
}
