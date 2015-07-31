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
	
	private Dimension size;
	private Dimension location;
	private Color baseColor;
	
	private boolean hasBeenProcessed;
	
	public RectangleBrick() {
		size = new Dimension();
		location = new Dimension();
		baseColor = null;
	}

	@Override
	public Dimension getSize() {
		return size;
	}

	@Override
	public Color getBaseColor() {
		return baseColor;
	}

	@Override
	public BrickInstance createInstance() {
		RectangleBrick result = new RectangleBrick();
		
		result.size.x = this.size.x;
		result.size.y = this.size.y;
		result.size.z = this.size.z;
		if (this.baseColor instanceof RandomColor) {
			result.baseColor = ((RandomColor) this.baseColor).pickRandomColor();
		} else {
			result.baseColor = this.baseColor;
		}
		
		return result;
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

	@Override
	public void setBaseColor(Color color) {
		this.baseColor = color;
	}
}
