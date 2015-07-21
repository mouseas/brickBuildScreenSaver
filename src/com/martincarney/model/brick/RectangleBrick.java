package com.martincarney.model.brick;

import java.awt.Color;

import com.martincarney.model.shared.Dimension;

/**
 * Simplest implementation of a Brick, this represents a simple rectangular brick. It acts as
 * both a prototype and an instance.
 * @author Martin Carney 2015
 */
public class RectangleBrick implements BrickPrototype, BrickInstance {
	
	private Dimension dimensions;
	private Color baseColor;
	
	private int x;
	private int y;
	private int z;
	
	public RectangleBrick(Dimension dimensions, Color baseColor) {
		this.dimensions = dimensions;
		this.baseColor = baseColor;
	}
	
	public Dimension getDimensions() {
		return dimensions;
	}

	public Color getBaseColor() {
		return baseColor;
	}
	
	public BrickInstance createInstance() {
		return new RectangleBrick(this.dimensions, this.baseColor);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public void setX(int newX) {
		x = newX;
	}

	public void setY(int newY) {
		y = newY;
	}

	public void setZ(int newZ) {
		z = newZ;
	}
}
