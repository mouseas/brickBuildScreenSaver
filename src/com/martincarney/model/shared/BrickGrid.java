package com.martincarney.model.shared;

import java.util.Collection;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.martincarney.model.brick.BrickInstance;

/**
 * 3D array of bricks in space.
 * @author Martin Carney
 */
public class BrickGrid {
	
	private BrickInstance[][][] grid;
	private Dimension size;
	
	private boolean ignoreBrickOverlap;
	
	public BrickGrid(Dimension gridSize) {
		size = new Dimension(gridSize);
		grid = new BrickInstance[size.x][size.y][size.z];
		
		ignoreBrickOverlap = false; // by default, throw an error if two bricks overlap when refreshing.
	}
	
	public BrickInstance get(int x, int y, int z) {
		return grid[x][y][z];
	}
	
	public BrickInstance get(Dimension coords) {
		return grid[coords.x][coords.y][coords.z];
	}
	
	/**
	 * Sets a grid position. This should be avoided in favor of calling {@link #refreshGrid(Collection)}.
	 */
	public void set(int x, int y, int z, BrickInstance brick) {
		grid[x][y][z] = brick;
	}
	
	/**
	 * Sets a grid position. This should be avoided in favor of calling {@link #refreshGrid(Collection)}.
	 */
	public void set(Dimension coords, BrickInstance brick) {
		grid[coords.x][coords.y][coords.z] = brick;
	}
	
	/**
	 * refreshes the grid's contents.
	 * @param bricks A collection of bricks which must fit within the grid's dimensions. The
	 * grid's cells will be cleared, then each brick placed in the cells corresponding to the
	 * brick's location + size.
	 */
	public void refreshGrid(Collection<BrickInstance> bricks) {
		// TODO copy implementation over from World.
		throw new NotImplementedException(); // use ignoreBrickOverlap's setting when implementing.
	}
	
	public void setIgnoreBrickOverlap(boolean ignoreBrickOverlap) {
		this.ignoreBrickOverlap = ignoreBrickOverlap;
	}
}
