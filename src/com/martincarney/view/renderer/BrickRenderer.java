package com.martincarney.view.renderer;

import java.awt.Graphics2D;

import com.martincarney.model.brick.BrickInstance;


public interface BrickRenderer {

	/**
	 * Renders the current brick using the provided {@link Graphics2D} instance. If the current brick
	 * is {@code null}, this method should do nothing. The renderer should always call
	 * {@link BrickInstance#setProcessed(boolean) BrickInstance.setRendered(true)} when it renders a
	 * brick, so that each brick is only drawn once per draw cycle, in the correct order.
	 */
	public void drawBrick(Graphics2D g, int xColumn, int yColumn);
	
	/**
	 * Sets the current brick to be rendered. The brick's type must be a type that the BrickRenderer
	 * accepts, or an exception should be thrown.
	 */
	public void setBrick(BrickInstance brick);
	
	/**
	 * Tells the renderer where a brick at (0,0,0) would have its lowest, left-most, nearest corner
	 * start drawing on the screen.
	 * @param x screen x position to start drawing from.
	 * @param y screen y position to start drawing from.
	 */
	public void setGraphicsOffset(int x, int y);
	
	/**
	 * Checks that the renderer can render the type of brick provided. Use this check before passing
	 * a brick to the renderer.
	 * @return {@code true} if the BrickInstance is a type that this renderer can correctly render,
	 * {@code false} if {@code brick} is {@code null}, or if the renderer cannot correctly render
	 * {@code brick}'s type of brick.
	 */
	public boolean canRenderThisBrickType(BrickInstance brick);
}
