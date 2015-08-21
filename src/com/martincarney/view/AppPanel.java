package com.martincarney.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
	
	private static final int SEC = 1000;
	private static final int FPS = 50;
	
	private static final int COUNTDOWN_LENGTH = 3 * FPS; // 3 seconds
	private static final int FRAME_LENGTH = SEC / FPS;
	
	private StructureLoader structureLoader;
	private BrickStructure structure;
	
	private World world;
	
	private Map<Class<? extends BrickRenderer>, BrickRenderer> renderers;
	
	private Timer timer;
	
	private int doneCountdown = COUNTDOWN_LENGTH;
	
	public AppPanel() {
		renderers = new HashMap<Class<? extends BrickRenderer>, BrickRenderer>();
		setBackground(Color.BLACK);
		
		structureLoader = new StructureLoader();
		StructureLoader.findStructureFiles();
		structure = structureLoader.loadNextStructureFile();
		prepWorldForStructure(structure);
		
		timer = new Timer(FRAME_LENGTH, this);
		timer.setInitialDelay(SEC / 2);
		timer.start();
	}

	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		
		Graphics2D gfx = (Graphics2D) graphics;
		gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// center world horizontally on-screen
		int xOffset = (this.getWidth() / 2) -
				(((world.getDimension().x * SCREEN_X_JOG) + (world.getDimension().y * SCREEN_X_JOG)) / 2);
		
		// roughly center world vertically on-screen
		int yOffset = (this.getHeight() / 2) + ((world.getDimension().z * SCREEN_Z_JOG) / 2);
		
		// render each brick, in order based on its lowest, left-most, most-distant (rather than nearest) corner
		/*
		 * FIXME there is some sort of render order bug. Track it down and squish it.
		 * Looks like it needs to render back to front, rather than diagonally. Which is diagonal in terms
		 * of the grid.
		 */
		for (int i = 0; i < world.getDimension().x; i++) {
			for (int j = world.getDimension().y - 1; j >= 0; j--) {
				BrickInstance previousBrick = null;
				for (int k = 0; k < world.getDimension().z; k++) {
					BrickInstance brick = world.getBrickGrid().get(i, j, k);
					if (brick != null && brick != previousBrick) {
						try {
							BrickRenderer renderer = getBrickRenderer(brick);
							renderer.setBrick(brick);
							renderer.setGraphicsOffset(xOffset, yOffset);
							renderer.drawBrick(gfx, i, j);
						} catch (InstantiationException | IllegalAccessException e) {
							e.printStackTrace();
						}
					}
					previousBrick = brick;
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
	
	private void prepWorldForStructure(BrickStructure structure) {
		Dimension worldSize = structure.getStructureSize();
		worldSize.z += 15;
		world = new World(worldSize);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (world != null) {
			world.update(1);
			
			// if the current brick has landed, drop the next one.
			if (world.getCurrentlyFallingBrick() == null && structure != null) {
				if (structure.hasNextBrick()) {
					// get the next brick in the structure
					BrickInstance nextBrick = structure.popRandomBrickToDrop();
					nextBrick.getLocation().z = world.getDimension().z - nextBrick.getSize().z;
					world.setNextBrick(nextBrick);
				} else {
					if (doneCountdown > 0) {
						// wait a bit before loading the next structure file.
						doneCountdown--;
					} else {
						// done waiting, load another structure file.
						structure = structureLoader.loadNextStructureFile();
						prepWorldForStructure(structure);
						doneCountdown = COUNTDOWN_LENGTH;
					}
				}
			}
			
			// redraw
			repaint();
		}
	}
}
