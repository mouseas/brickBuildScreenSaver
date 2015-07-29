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
	private Dimension location;
	private Color baseColor;
	
	private boolean hasBeenProcessed;
	
	public RectangleBrick(Dimension dimensions, Color baseColor) {
		this.dimensions = dimensions;
		this.baseColor = baseColor;
		location = new Dimension();
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
	public boolean hasBeenProcessed() {
		return hasBeenProcessed;
	}

	@Override
	public void setProcessed(boolean processed) {
		this.hasBeenProcessed = processed;
	}

	@Override
	public Dimension getLocation() {
		return location;
	}
}
