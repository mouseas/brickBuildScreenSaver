package com.martincarney.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.martincarney.control.StructureLoader;
import com.martincarney.model.World;
import com.martincarney.model.brick.BrickInstance;
import com.martincarney.model.brick.RectangleBrick;
import com.martincarney.model.shared.Dimension;
import com.martincarney.model.structure.BrickStructure;
import com.martincarney.view.renderer.BrickRenderer;
import com.martincarney.view.renderer.RendererConstants;

import static com.martincarney.view.renderer.RendererConstants.*;

/**
 * Visible part of the screensaver when operating normally.
 * @author Martin Carney 2015
 */
public class AppPanel extends JPanel implements ActionListener {
	
	private StructureLoader structureLoader;
	private BrickStructure structure;
	
	private World world;
	
	private Map<Class<? extends BrickRenderer>, BrickRenderer> renderers;
	
	private Timer timer;
	
	public AppPanel() {
		renderers = new HashMap<Class<? extends BrickRenderer>, BrickRenderer>();
		setBackground(Color.BLACK);
		
//		structureLoader = new StructureLoader();
//		StructureLoader.findStructureFiles();
//		structureLoader.loadStructureFromJSONFile(StructureLoader.getStructureFileList().get(0));
		
		
		generateDebugWorld();
		
		timer = new Timer(33, this);
		timer.setInitialDelay(500);
		timer.start();
	}

	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		
		Graphics2D gfx = (Graphics2D) graphics;
		gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// reset each brick's hasRendered state so they'll get rendered.
		for (BrickInstance brick : world.getActiveBricks()) {
			brick.setProcessed(false);
		}
		
		// center world horizontally on-screen
		int xOffset = (this.getWidth() / 2) -
				(((world.getDimension().x * SCREEN_X_JOG) + (world.getDimension().y * SCREEN_X_JOG)) / 2);
		
		// roughly center world vertically on-screen
		int yOffset = (this.getHeight() / 2) + ((world.getDimension().z * SCREEN_Z_JOG) / 2);
		
		// render each brick, in order based on its lowest, left-most, most-distant (rather than nearest) corner
		for (int i = 0; i < world.getDimension().z; i++) {
			for (int j = world.getDimension().y - 1; j >= 0; j--) {
				for (int k = 0; k < world.getDimension().x; k++) {
					BrickInstance brick = world.getBrickGrid().get(k, j, i);
					if (brick != null && !brick.hasBeenProcessed()) {
						try {
							BrickRenderer renderer = getBrickRenderer(brick);
							renderer.setBrick(brick);
							renderer.setGraphicsOffset(xOffset, yOffset);
							renderer.drawBrick(gfx);
						} catch (InstantiationException | IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	/**
	 * Gets the appropriate renderer for the provided brick. A cache is used to hold 1 instance of each type
	 * of renderer so that we don't instantiate unused renderers and so we don't have duplicate renderers.
	 * @param brick
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
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
		world = new World(new Dimension(30, 30, 90));
		world.getBrickGrid().setIgnoreProblems(true);
		Random rand = new Random();
		Collection<BrickInstance> bricks = world.getActiveBricks();
		BrickInstance baseplate = new RectangleBrick(new Dimension(30, 30, 1), BRICK_COLORS[C_YELLOW]);
		bricks.add(baseplate);
		for (int i = 0; i < 100; i++) {
			Dimension size = new Dimension(1 + rand.nextInt(2), 1 + rand.nextInt(2), 1 + rand.nextInt(3));
			Color color = BRICK_COLORS[rand.nextInt(BRICK_COLORS.length)];
			BrickInstance newBrick = new RectangleBrick(size, color);
			newBrick.getLocation().x = rand.nextInt(31 - size.x);
			newBrick.getLocation().y = rand.nextInt(31 - size.y);
			newBrick.getLocation().z = 1 + (3 * rand.nextInt(4));
			bricks.add(newBrick);
		}
		world.refreshBrickGrid();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (world != null) {
			world.update(1);
			
			// if the current brick has landed, drop the next one.
			if (world.getCurrentlyFallingBrick() == null && structure != null) {
				if (structure.hasNextBrick()) {
					world.setNextBrick(structure.popRandomBrickToDrop());
				} else {
					// TODO load another structure.
				}
			}
			
			// redraw
			repaint();
		}
	}
}
