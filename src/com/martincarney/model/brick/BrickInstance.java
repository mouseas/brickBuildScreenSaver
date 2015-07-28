package com.martincarney.model.brick;

import com.martincarney.model.World;
import com.martincarney.model.shared.Dimension;
import com.martincarney.view.renderer.BrickRenderer;

/**
 * Represents an instance of a Brick, which has a changeable (x, y, z) world position
 * @author Martin Carney 2015
 */
public interface BrickInstance extends Brick {
	
	/** Gets the editable position of the brick. */
	public Dimension getLocation();
	
	/**
	 * Checks whether the brick has been processed, e.g. drawn this draw cycle.
	 * @return {@code true} if the brick has been processed during the current process. For rendering,
	 * that means it's been drawn this draw cycle. {@code false} if it still needs
	 * to be processed.
	 */
	public boolean hasBeenProcessed();
	
	/**
	 * Sets whether the brick has been processed during this process. For example, {@link World} sets this
	 * to {@code false} on every brick at the start of a draw cycle, and {@link BrickRenderer} sets it to
	 * {@code true} when it draws the brick during a draw cycle.
	 * @param processed Whether the brick has been processed during this process.
	 */
	public void setProcessed(boolean processed);
	
}
