package com.martincarney.model.brick;

import java.awt.Color;

import com.martincarney.model.shared.Dimension;
import com.martincarney.view.renderer.BrickRenderer;
import com.martincarney.view.renderer.RectangleBrickRenderer;

/**
 * Simplest implementation of a Brick, this represents a simple rectangular brick. It acts as
 * both a prototype and an instance.
 * @author Martin Carney 2015
 */
public class RectangleBrick implements BrickPrototype, BrickInstance {
	
	private Dimension dimensions;
	private Color baseColor;
	
	private boolean hasRendered;
	
	private int x;
	private int y;
	private int z;
	
	public RectangleBrick(Dimension dimensions, Color baseColor) {
		this.dimensions = dimensions;
		this.baseColor = baseColor;
	}

	@Override
	public Dimension getDimensions() {
		return dimensions;
	}

	@Override
	public Color getBaseColor() {
		return baseColor;
	}

	@Override
	public BrickInstance createInstance() {
		return new RectangleBrick(this.dimensions, this.baseColor);
	}

	@Override
	public Class<? extends BrickRenderer> getRendererClass() {
		return RectangleBrickRenderer.class;
	}

	@Override
	public boolean hasRendered() {
		return hasRendered;
	}

	@Override
	public void setRendered(boolean rendered) {
		this.hasRendered = rendered;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getZ() {
		return z;
	}

	@Override
	public void setX(int newX) {
		x = newX;
	}

	@Override
	public void setY(int newY) {
		y = newY;
	}

	@Override
	public void setZ(int newZ) {
		z = newZ;
	}
}
