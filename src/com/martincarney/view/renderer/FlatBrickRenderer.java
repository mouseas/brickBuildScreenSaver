package com.martincarney.view.renderer;

import java.awt.Graphics2D;

import com.martincarney.model.brick.BrickInstance;
import com.martincarney.model.brick.FlatBrick;
import com.martincarney.model.brick.RectangleBrick;

public class FlatBrickRenderer extends RectangleBrickRenderer {
	@Override
	protected void drawStuds(int startX, int startY, Graphics2D g) {
		// don't draw studs.
	}
	
	@Override
	public void setBrick(BrickInstance brick) {
		if (brick == null || brick instanceof RectangleBrick) {
			currentBrick = (RectangleBrick) brick;
		} else {
			throw new IllegalStateException("Provided BrickInstance was not a FlatBrick, but was instead "
				+ brick.getClass().getName());
		}
	}
	
	@Override
	public boolean canRenderThisBrickType(BrickInstance brick) {
		if (brick != null && brick instanceof FlatBrick) {
			return true;
		} else {
			return false;
		}
	}
}
