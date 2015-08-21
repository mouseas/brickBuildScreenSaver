package com.martincarney.model.brick;

import com.martincarney.view.renderer.BrickRenderer;
import com.martincarney.view.renderer.FlatBrickRenderer;

/**
 * Simple implementation of a Brick, this represents a simple rectangular brick, without studs.
 * It acts as both a prototype and an instance.
 * @author Martin Carney 2015
 */
public class FlatBrick extends RectangleBrick {
	@Override
	public Class<? extends BrickRenderer> getRendererClass() {
		return FlatBrickRenderer.class;
	}
	
	@Override
	public BrickInstance createInstance() {
		FlatBrick result = new FlatBrick();
		
		result.getSize().x = this.getSize().x;
		result.getSize().y = this.getSize().y;
		result.getSize().z = this.getSize().z;
		if (this.getBaseColor() instanceof RandomColor) {
			result.setBaseColor(((RandomColor) this.getBaseColor()).pickRandomColor());
		} else {
			result.setBaseColor(this.getBaseColor());
		}
		
		return result;
	}
}
