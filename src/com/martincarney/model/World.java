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
				// TODO
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
