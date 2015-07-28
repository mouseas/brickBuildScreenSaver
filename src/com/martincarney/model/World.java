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
				if (fallingBrickCollide()) {
					brickLanded = true;
				} else {
					currentlyFallingBrick.setZ(currentlyFallingBrick.getZ() - 1);
				}
			}
			
			// if landed, notify notifyees (who should, in turn, give the world its next brick)
			if (brickLanded) {
				for (EventListener notifyee : brickLandedNotifyees) {
					notifyee.notify();
				}
				currentlyFallingBrick = null;
			}
			
			// notify time passed notifyees
			for (EventListener notifyee : timePassedNotifyees) {
				notifyee.notify();
			}
			
		}
	}
	
	/**
	 * Checks if any of the grid locations below the current brick are occupied.
	 * @return {@code true} if any grid position below the bottom of the brick already has a brick in them.
	 * {@code false} otherwise.
	 */
	private boolean fallingBrickCollide() {
		int collideHeight = currentlyFallingBrick.getDimensions().z - 1;
		for (int i = 0; i < currentlyFallingBrick.getDimensions().x; i++) {
			for (int j = 0; j < currentlyFallingBrick.getDimensions().y; j++) {
				if (brickGrid[i][j][collideHeight] != null) {
					return true;
				}
			}
		}
		return false;
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
