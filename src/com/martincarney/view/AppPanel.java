package com.martincarney.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JPanel;

import com.martincarney.model.World;
import com.martincarney.model.brick.BrickInstance;
import com.martincarney.model.brick.RectangleBrick;
import com.martincarney.model.shared.Dimension;
import com.martincarney.view.renderer.BrickRenderer;
import com.martincarney.view.renderer.RendererConstants;

public class AppPanel extends JPanel {

	protected World world;
	
	private Map<Class<? extends BrickRenderer>, BrickRenderer> renderers;

	public AppPanel() {
		renderers = new HashMap<Class<? extends BrickRenderer>, BrickRenderer>();
		setBackground(Color.BLACK);
		
		generateDebugWorld();
	}

	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D gfx = (Graphics2D) graphics;
		gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// reset each brick's hasRendered state so they'll get rendered.
		for (BrickInstance brick : world.getActiveBricks()) {
			brick.setRendered(false);
		}
		
		// render each brick, in order based on its lowest, left-most, most-distant (rather than nearest) corner
		int xOffset = 20; // TODO generate this programmatically from world size + window size
		int yOffset = 400; // TODO generate this programmatically from world size + window size
		for (int i = 0; i < world.getBrickGrid().length; i++) {
			for (int j = world.getBrickGrid()[i].length - 1; j >= 0; j--) {
				for (int k = 0; k < world.getBrickGrid()[i][j].length; k++) {
					BrickInstance brick = world.getBrickGrid()[i][j][k];
					if (brick != null && !brick.hasRendered()) {
						try {
							BrickRenderer renderer = getBrickRenderer(brick);
							renderer.setBrick(brick);
							renderer.setGraphicsOffset(xOffset, yOffset);
							renderer.drawBrick(gfx);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	private BrickRenderer getBrickRenderer(BrickInstance brick) throws InstantiationException, IllegalAccessException {
		Class<? extends BrickRenderer> rendererClass = brick.getRendererClass();
		if (!renderers.containsKey(rendererClass)) {
			BrickRenderer renderer = rendererClass.newInstance();
			renderers.put(rendererClass, renderer);
		}
		return renderers.get(rendererClass);
	}
	
	/**
	 * @deprecated implemented only for the development phase. Builds a World object with a few
	 * bricks in it to use to test the render engine.
	 */
	private void generateDebugWorld() {
		world = new World(new Dimension(30, 30, 30));
		Random rand = new Random();
		Collection<BrickInstance> bricks = world.getActiveBricks();
		BrickInstance baseplate = new RectangleBrick(new Dimension(30, 30, 1), new Color(0, 110, 0));
		bricks.add(baseplate);
		for (int i = 0; i < 100; i++) {
			Dimension size = new Dimension(1 + rand.nextInt(2), 1 + rand.nextInt(2), 3);
			Color color = RendererConstants.BRICK_COLORS[rand.nextInt(RendererConstants.BRICK_COLORS.length)];
			BrickInstance newBrick = new RectangleBrick(size, color);
			newBrick.setX(rand.nextInt(31 - size.x));
			newBrick.setY(rand.nextInt(31 - size.y));
			newBrick.setZ(1 + (3 * rand.nextInt(4)));
			bricks.add(newBrick);
		}
		world.refreshBrickGrid();
	}
}
