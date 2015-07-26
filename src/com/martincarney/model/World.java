package com.martincarney.model;

import java.util.Collection;
import java.util.EventListener;
import java.util.HashSet;
import java.util.LinkedList;

import com.martincarney.model.brick.BrickInstance;
import com.martincarney.model.shared.Dimension;


public class World {

	private BrickInstance currentlyFallingBrick;
	private Dimension dimension;
	private BrickInstance[][][] brickGrid;
	private Collection<BrickInstance> activeBricks;
	
	private Collection<EventListener> brickLandedNotifyees;
	private Collection<EventListener> timePassedNotifyees;
	
	public World(Dimension dimension) {
		this.dimension = new Dimension(dimension);
		activeBricks = new LinkedList<BrickInstance>();
		brickGrid = new BrickInstance[dimension.x][dimension.y][dimension.z];

		brickLandedNotifyees = new HashSet<>();
		timePassedNotifyees = new HashSet<>();
	}
	
	public void update(int numSteps) {
		for (int i = 0; i < numSteps; i++) {
			// process falling brick, if any
			boolean brickLanded = false;
			if (currentlyFallingBrick != null) {
				// TODO implement brick falling physics
			}
			
			// if landed, notify notifyees (who should, in turn, give the world its next brick)
			if (brickLanded) {
				for (EventListener notifyee : brickLandedNotifyees) {
					notifyee.notify();
				}
			}
			
			// notify time passed notifyees
			for (EventListener notifyee : timePassedNotifyees) {
				notifyee.notify();
			}
			
		}
	}
	
	/**
	 * Clears and re-builds the {@code brickGrid} to accurately reflect the bricks in {@code activeBricks}.
	 */
	public void refreshBrickGrid() {
		// first mark every grid cell as empty.
		for (int i = 0; i < brickGrid.length; i++) {
			for (int j = 0; j < brickGrid[i].length; j++) {
				for (int k = 0; k < brickGrid[i][j].length; k++) {
					brickGrid[i][j][k] = null;
				}
			}
		}
		
		// then iterate over each brick in the world and place it in each grid space it occupies.
		for (BrickInstance brick : activeBricks) {
			Dimension brickSize = brick.getDimensions();
			for (int i = 0; i < brickSize.x; i++) {
				for (int j = 0; j < brickSize.y; j++) {
					for (int k = 0; k < brickSize.z; k++) {
						safeSetGridValue(i + brick.getX(), j + brick.getY(), k + brick.getZ(), brick);
					}
				}
			}
		}
	}
	
	/** Checks that the specified coordinate is valid, then sets the brick for that coordinate. */
	private void safeSetGridValue(int x, int y, int z, BrickInstance brick) {
		if (x < 0 || x >= brickGrid.length || y < 0 || y >= brickGrid[x].length || z < 0
				|| z >= brickGrid[x][y].length) {
			// TODO add a warning that part of a brick is out of the world boundaries.
			return;
		}
		// TODO add a check and a warning if that grid location is already occupied, as that means two bricks
		// are occupying the same space.
		brickGrid[x][y][z] = brick;
	}

	public void setNextBrick(BrickInstance nextFallingBrick) {
		if (currentlyFallingBrick != null) {
			throw new IllegalStateException("Can't provide the next brick if the current one is still falling!");
		}
		currentlyFallingBrick = nextFallingBrick;
		activeBricks.add(nextFallingBrick);
	}

	public void addBrickLandedEventListener(EventListener listener) {
		if (listener != null) {
			brickLandedNotifyees.add(listener);
		}
	}
	
	public void removeBrickLandedEventListener(EventListener listener) {
		brickLandedNotifyees.remove(listener);
	}
	
	public void addTimePassedEventListener(EventListener listener) {
		if (listener != null) {
			brickLandedNotifyees.add(listener);
		}
	}
	
	public void removeTimePassedEventListener(EventListener listener) {
		brickLandedNotifyees.remove(listener);
	}
	
	public BrickInstance[][][] getBrickGrid() {
		return brickGrid;
	}

	public BrickInstance getCurrentlyFallingBrick() {
		return currentlyFallingBrick;
	}
	
	public Collection<BrickInstance> getActiveBricks() {
		return activeBricks;
	}
	
	public Dimension getDimension() {
		return dimension;
	}
}
