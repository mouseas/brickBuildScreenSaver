package com.martincarney.model.shared;

import java.util.Collection;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.martincarney.model.brick.BrickInstance;

/**
 * 3D array of bricks in space.
 * @author Martin Carney 2015
 */
public class BrickGrid {
	
	private BrickInstance[][][] grid;
	private Dimension size;
	
	private boolean ignoreProblems;
	
	public BrickGrid(Dimension gridSize) {
		size = new Dimension(gridSize);
		grid = new BrickInstance[size.x][size.y][size.z];
		
		ignoreProblems = false; // by default, throw an error if two bricks overlap when refreshing.
	}
	
	/**
	 * Gets the brick (or null) at the specified coordinates. If {@link #ignoreProblems} is {@code false}
	 * and the coordinates are out of bounds, an exception will be thrown. If it's {@code true}, {@code null}
	 * will be returned instead.
	 */
	public BrickInstance get(int x, int y, int z) {
		if (!isInBounds(x, y, z) && ignoreProblems) {
			return null;
		}
		return grid[x][y][z];
	}
	
	/**
	 * Gets the brick (or null) at the specified coordinates. If {@link #ignoreProblems} is {@code false}
	 * and the coordinates are out of bounds, an exception will be thrown. If it's {@code true}, {@code null}
	 * will be returned instead.
	 */
	public BrickInstance get(Dimension coords) {
		if (!isInBounds(coords) && ignoreProblems) {
			return null;
		}
		return grid[coords.x][coords.y][coords.z];
	}
	
	/**
	 * Sets a grid position. This should be avoided in favor of calling {@link #refreshGrid(Collection)}.
	 */
	public void set(int x, int y, int z, BrickInstance brick) {
		safeSetGridValue(x, y, z, brick);
	}
	
	/**
	 * Sets a grid position. This should be avoided in favor of calling {@link #refreshGrid(Collection)}.
	 */
	public void set(Dimension coords, BrickInstance brick) {
		safeSetGridValue(coords.x, coords.y, coords.z, brick);
	}
	
	/**
	 * Refreshes the grid's contents. The cells are all cleared, then populated with the provided bricks.
	 * @param bricks A collection of bricks which must fit within the grid's dimensions. The
	 * grid's cells will be cleared, then each brick placed in the cells corresponding to the
	 * brick's location + size.
	 */
	public void refreshGrid(Collection<BrickInstance> bricks) {
		// first mark every grid cell as empty.
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				for (int k = 0; k < grid[i][j].length; k++) {
					grid[i][j][k] = null;
				}
			}
		}
		
		// then iterate over each brick in the world and place it in each grid space it occupies.
		for (BrickInstance brick : bricks) {
			Dimension brickSize = brick.getDimensions();
			for (int i = 0; i < brickSize.x; i++) {
				for (int j = 0; j < brickSize.y; j++) {
					for (int k = 0; k < brickSize.z; k++) {
						safeSetGridValue(i + brick.getLocation().x, j + brick.getLocation().y, k + brick.getLocation().z, brick);
					}
				}
			}
		}
	}
	
	/** Checks that the specified coordinate is valid, then sets the brick for that coordinate. */
	private void safeSetGridValue(int x, int y, int z, BrickInstance brick) {
		if (x < 0 || x >= grid.length || y < 0 || y >= grid[x].length || z < 0
				|| z >= grid[x][y].length) {
			if (!ignoreProblems) {
				System.out.println("A brick was out of the grid's bounds!");
			}
			return;
		}
		
		// check for brick overlap.
		if (!ignoreProblems && grid[x][y][z] != null && grid[x][y][z] != brick) {
			throw new IllegalStateException("A brick is already assigned at (" + x + ", " + y + ", " + z + ").");
		}
		
		grid[x][y][z] = brick;
	}
	
	public void setIgnoreProblems(boolean ignoreProblems) {
		this.ignoreProblems = ignoreProblems;
	}
	
	public Dimension getSize() {
		return size;
	}
	
	public boolean isEmpty(Dimension coord) {
		return grid[coord.x][coord.y][coord.z] == null;
	}
	
	public boolean isEmpty(int x, int y, int z) {
		return grid[x][y][z] == null;
	}
	
	public boolean isInBounds(Dimension coord) {
		return coord.x >= 0 && coord.y >= 0 && coord.z >= 0 && coord.x < size.x && coord.y < size.y
				&& coord.z < size.z;
	}
	
	public boolean isInBounds(int x, int y, int z) {
		return x >= 0 && y >= 0 && z >= 0 && x < size.x && y < size.y && z < size.z;
	}
}
