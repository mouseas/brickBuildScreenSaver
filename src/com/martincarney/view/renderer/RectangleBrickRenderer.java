package com.martincarney.view.renderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import com.martincarney.model.brick.BrickInstance;
import com.martincarney.model.brick.RectangleBrick;
import com.martincarney.model.shared.Dimension;

import static com.martincarney.view.renderer.RendererConstants.*;

public class RectangleBrickRenderer implements BrickRenderer {
	
	protected int xOffset;
	protected int yOffset;
	
	protected RectangleBrick currentBrick;
	
	protected int xRenderColumn;
	protected int yRenderColumn;
	
	@Override
	public void drawBrick(Graphics2D g, int columnX, int columnY) {
		if (currentBrick == null) {
			return;
		}
		
		this.xRenderColumn = columnX;
		this.yRenderColumn = columnY;
		
		Dimension brickLoc = currentBrick.getLocation();
		int startX = xOffset + (xRenderColumn * SCREEN_X_JOG) + (yRenderColumn * SCREEN_X_JOG);
		int startY = yOffset - (brickLoc.z * SCREEN_Z_JOG) + (xRenderColumn * SCREEN_Y_JOG)
				- (yRenderColumn * SCREEN_Y_JOG);
		
		if (yRenderColumn == brickLoc.y) {
			drawXFace(startX, startY, g);
		}
		if (xRenderColumn == brickLoc.x + currentBrick.getSize().x - 1) {
			drawYFace(startX, startY, g);
		}
		drawZFace(startX, startY, g);
		drawStuds(startX, startY, g);
	}
	
	protected void drawXFace(int startX, int startY, Graphics2D g) {
		Dimension brickSize = currentBrick.getSize();
		
		Polygon xFace = new Polygon();
		int x = startX;
		int y = startY;
		xFace.addPoint(x, y);
		y -= SCREEN_Z_JOG * brickSize.z;
		xFace.addPoint(x, y);
		x += (SCREEN_X_JOG) + (SCREEN_X_JOG);
		y += (SCREEN_Y_JOG) - (SCREEN_Y_JOG);
		xFace.addPoint(x, y);
		y += SCREEN_Z_JOG * brickSize.z;
		xFace.addPoint(x, y);
		x -= SCREEN_X_JOG;
		y += SCREEN_Y_JOG;
		xFace.addPoint(x, y);
		xFace.addPoint(startX, startY);
		
		g.setColor(currentBrick.getBaseColor());
		g.fill(xFace);
	}
	
	protected void drawYFace(int startX, int startY, Graphics2D g) {
		Dimension brickSize = currentBrick.getSize();
		
		Polygon yFace = new Polygon();
		int x = startX + (SCREEN_X_JOG);
		int y = startY + (SCREEN_Y_JOG);
		yFace.addPoint(x, y);
		y -= SCREEN_Z_JOG * brickSize.z;
		yFace.addPoint(x, y);
		x += SCREEN_X_JOG;
		y -= SCREEN_Y_JOG;
		yFace.addPoint(x, y);
		y += SCREEN_Z_JOG * brickSize.z;
		yFace.addPoint(x, y);
		yFace.addPoint(startX + (SCREEN_X_JOG), startY + (SCREEN_Y_JOG));
		
		g.setColor(getDarkerColor(currentBrick.getBaseColor()));
		g.fill(yFace);
	}
	
	protected void drawZFace(int startX, int startY, Graphics2D g) {
		Dimension brickSize = currentBrick.getSize();
		
		Polygon zFace = new Polygon();
		int x = startX;
		int y = startY - (SCREEN_Z_JOG * brickSize.z);
		zFace.addPoint(x, y);
		x += SCREEN_X_JOG;
		y += SCREEN_Y_JOG;
		zFace.addPoint(x, y);
		x += SCREEN_X_JOG;
		y -= SCREEN_Y_JOG;
		zFace.addPoint(x, y);
		x -= SCREEN_X_JOG;
		y -= SCREEN_Y_JOG;
		zFace.addPoint(x, y);
		zFace.addPoint(startX, startY - (SCREEN_Z_JOG * brickSize.z));
		
		g.setColor(getLighterColor(currentBrick.getBaseColor()));
		g.fill(zFace);
	}
	
	protected void drawStuds(int startX, int startY, Graphics2D g) {
		int x = startX + SCREEN_STUD_X_OFFSET;
		int y = startY - SCREEN_STUD_Y_OFFSET - (currentBrick.getSize().z * SCREEN_Z_JOG);
		Ellipse2D studBottom = new Ellipse2D.Float(x, y, SCREEN_STUD_WIDTH, SCREEN_STUD_TOP_HEIGHT);
		g.setColor(currentBrick.getBaseColor());
		g.fill(studBottom);
		
		Rectangle filling = new Rectangle(x, y + SCREEN_STUD_Y_OFFSET - SCREEN_STUD_Z_HEIGHT,
				SCREEN_STUD_WIDTH, SCREEN_STUD_Z_HEIGHT);
		g.fill(filling);
		
		y -= SCREEN_STUD_Z_HEIGHT;
		Ellipse2D studTop = new Ellipse2D.Float(x, y, SCREEN_STUD_WIDTH, SCREEN_STUD_TOP_HEIGHT);
		g.setColor(getLighterColor(currentBrick.getBaseColor()));
		g.fill(studTop);
	}

	protected Color getLighterColor(Color midColor) {
		return midColor.brighter();
	}

	protected Color getDarkerColor(Color midColor) {
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
