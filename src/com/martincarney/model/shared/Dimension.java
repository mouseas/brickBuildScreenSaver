package com.martincarney.model.shared;

/**
 * Represents the x, y, and z dimensions of an object. This is just a struct.
 * @author Martin Carney 2015
 */
public class Dimension {
	/** x dimension */
	public int x;
	
	/** y dimension */
	public int y;
	
	/** z dimension */
	public int z;
	
	public Dimension() {
		
	}
	
	public Dimension(int width, int breadth, int height) {
		this.x = width;
		this.y = breadth;
		this.z = height;
	}
	
	public Dimension(Dimension cloneSource) {
		this.x = cloneSource.x;
		this.y = cloneSource.y;
		this.z = cloneSource.z;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
