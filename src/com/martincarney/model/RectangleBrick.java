package com.martincarney.model;

import java.awt.Color;

/**
 * Simplest implementation of a Brick, this represents a simple rectangular brick. It acts as
 * both a prototype and an instance.
 * @author Martin Carney 2015
 *
 */
public class RectangleBrick implements BrickPrototype, BrickInstance {
	
	private Dimensions dimensions;
	private Color baseColor;
	
	private int x;
	private int y;
	private int z;
	
	public RectangleBrick(Dimensions dimensions, Color baseColor) {
		this.dimensions = dimensions;
		this.baseColor = baseColor;
	}
	
	@Override
	public Dimensions getDimensions() {
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
