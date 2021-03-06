package com.martincarney.model;

import java.util.Collection;
import java.util.EventListener;
import java.util.HashSet;
import java.util.LinkedList;

import com.martincarney.model.brick.BrickInstance;
import com.martincarney.model.shared.BrickGrid;
import com.martincarney.model.shared.Dimension;

/**
 * Represents a region of space with bricks in it, and handles dropping bricks and passing time.
 * @author Martin Carney 2015
 */
public class World {

	private BrickInstance currentlyFallingBrick;
	private Dimension size;
	private BrickGrid brickGrid;
	private Collection<BrickInstance> activeBricks;
	
	private Collection<EventListener> brickLandedNotifyees;
	private Collection<EventListener> timePassedNotifyees;
	
	public World(Dimension size) {
		this.size = new Dimension(size);
		activeBricks = new HashSet<BrickInstance>();
		brickGrid = new BrickGrid(size);

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
					currentlyFallingBrick.getLocation().z--;
					refreshBrickGrid();
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
		int collideHeight = currentlyFallingBrick.getLocation().z - 1;
		if (collideHeight < 0) { // brick has reached the bottom without landing on anything.
			return true;
		}
		for (int i = 0; i < currentlyFallingBrick.getSize().x; i++) {
			for (int j = 0; j < currentlyFallingBrick.getSize().y; j++) {
				if (!brickGrid.isEmpty(i + currentlyFallingBrick.getLocation().x,
						j + currentlyFallingBrick.getLocation().y, collideHeight)) {
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
		brickGrid.refreshGrid(activeBricks);
	}

	public void setNextBrick(BrickInstance nextFallingBrick) {
		if (currentlyFallingBrick != null) {
			throw new IllegalStateException("Can't provide the next brick if the current one is still falling!");
		}
		currentlyFallingBrick = nextFallingBrick;
		activeBricks.add(nextFallingBrick);
		refreshBrickGrid();
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
	
	public BrickGrid getBrickGrid() {
		return brickGrid;
	}

	public BrickInstance getCurrentlyFallingBrick() {
		return currentlyFallingBrick;
	}
	
	public Collection<BrickInstance> getActiveBricks() {
		return activeBricks;
	}
	
	public Dimension getDimension() {
		return size;
	}
}
