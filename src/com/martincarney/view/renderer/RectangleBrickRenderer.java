package com.martincarney.view.renderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

import com.martincarney.model.brick.BrickInstance;
import com.martincarney.model.brick.RectangleBrick;
import com.martincarney.model.shared.Dimension;

import static com.martincarney.view.renderer.RendererConstants.*;

public class RectangleBrickRenderer implements BrickRenderer {
	
	private int xOffset;
	private int yOffset;
	
	private RectangleBrick currentBrick;

	@Override
	public void drawBrick(Graphics2D g) {
		if (currentBrick == null) {
			return;
		}
		
		int startX = xOffset + (currentBrick.getX() * SCREEN_X_JOG) + (currentBrick.getY() * SCREEN_X_JOG);
		int startY = yOffset - (currentBrick.getZ() * SCREEN_Z_JOG) + (currentBrick.getX() * SCREEN_Y_JOG)
				- (currentBrick.getY() * SCREEN_Y_JOG);
		
		Color midColor = currentBrick.getBaseColor();
		Color lightColor = getLighterColor(midColor);
		Color darkColor = getDarkerColor(midColor);
		
		int x = startX;
		int y = startY;
		Dimension brickSize = currentBrick.getDimensions();
		
		Polygon xFace = new Polygon();
		xFace.addPoint(x, y);
		y -= SCREEN_Z_JOG * brickSize.z;
		xFace.addPoint(x, y);
		x += SCREEN_X_JOG * brickSize.x;
		y += SCREEN_Y_JOG * brickSize.x;
		xFace.addPoint(x, y);
		y += SCREEN_Z_JOG * brickSize.z;
		xFace.addPoint(x, y);
		xFace.addPoint(startX, startY);
		
		Polygon yFace = new Polygon();
		x = startX + (SCREEN_X_JOG * brickSize.x);
		y = startY + (SCREEN_Y_JOG * brickSize.x);
		yFace.addPoint(x, y);
		y -= SCREEN_Z_JOG * brickSize.z;
		yFace.addPoint(x, y);
		x += SCREEN_X_JOG * brickSize.y;
		y -= SCREEN_Y_JOG * brickSize.y;
		yFace.addPoint(x, y);
		y += SCREEN_Z_JOG * brickSize.z;
		yFace.addPoint(x, y);
		yFace.addPoint(startX + (SCREEN_X_JOG * brickSize.x), startY + (SCREEN_Y_JOG * brickSize.x));
		
		Polygon zFace = new Polygon();
		x = startX;
		y = startY - (SCREEN_Z_JOG * brickSize.z);
		zFace.addPoint(x, y);
		x += SCREEN_X_JOG * brickSize.x;
		y += SCREEN_Y_JOG * brickSize.x;
		zFace.addPoint(x, y);
		x += SCREEN_X_JOG * brickSize.y;
		y -= SCREEN_Y_JOG * brickSize.y;
		zFace.addPoint(x, y);
		x -= SCREEN_X_JOG * brickSize.x;
		y -= SCREEN_Y_JOG * brickSize.x;
		zFace.addPoint(x, y);
		zFace.addPoint(startX, startY - (SCREEN_Z_JOG * brickSize.z));
		
		g.setColor(midColor);
		g.fill(xFace);
		g.setColor(darkColor);
		g.fill(yFace);
		g.setColor(lightColor);
		g.fill(zFace);
		
		currentBrick.setRendered(true);
	}

	private Color getLighterColor(Color midColor) {
		return midColor.brighter();
	}

	private Color getDarkerColor(Color midColor) {
		return midColor.darker();
	}

	@Override
	public void setBrick(BrickInstance brick) {
		if (brick == null || brick instanceof RectangleBrick) {
			currentBrick = (RectangleBrick) brick;
		} else {
			throw new IllegalStateException("Provided BrickInstance was not a RectangleBrick, but was instead "
				+ brick.getClass().getName());
		}
	}

	@Override
	public void setGraphicsOffset(int x, int y) {
		this.xOffset = x;
		this.yOffset = y;
	}

	@Override
	public boolean canRenderThisBrickType(BrickInstance brick) {
		if (brick != null && brick instanceof RectangleBrick) {
			return true;
		} else {
			return false;
		}
	}

}
