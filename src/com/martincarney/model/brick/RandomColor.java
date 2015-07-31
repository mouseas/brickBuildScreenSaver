package com.martincarney.model.brick;

import java.awt.Color;
import java.util.Random;

import com.martincarney.view.renderer.RendererConstants;

/**
 * Dummy extension of {@link Color} which returns a randomly-chosen brick color. When placed on a
 * {@link BrickPrototype}, each {@link BrickInstance} generated from the prototype should have a
 * randomly-chosen color (rather than all of them having the same color).
 * @author Martin Carney 2015
 */
public class RandomColor extends Color {
	
	private static Random rand = new Random();
	
	public RandomColor() {
		super(0);
	}
	
	public Color pickRandomColor() {
		return RendererConstants.BRICK_COLORS[rand.nextInt(RendererConstants.BRICK_COLORS.length)];
	}
	
}
