package com.martincarney.model.structure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.martincarney.model.World;
import com.martincarney.model.brick.BrickInstance;
import com.martincarney.model.shared.BrickGrid;
import com.martincarney.model.shared.Dimension;

/**
 * Stores the parts of a structure to be built.
 * @author Martin Carney 2015
 */
public class BrickStructure {
	
	private Random rand;
	
	private Dimension structureSize;
	
	private List<BrickInstance> rawBricks;
	
	private LinkedHashSet<BrickTreeNode> availableBricks;
	
	Map<BrickInstance, BrickTreeNode> brickNodes;
	
	public BrickStructure() {
		rand = new Random();
		
		rawBricks = new ArrayList<BrickInstance>();
		
		availableBricks = new LinkedHashSet<BrickTreeNode>();
		
		brickNodes = new HashMap<BrickInstance, BrickTreeNode>();
	}
	
	public void provideBricks(Collection<BrickInstance> unsortedBricks) {
		if (!rawBricks.isEmpty()) {
			throw new IllegalStateException("Can only provide bricks to a " + BrickStructure.class.getSimpleName()
					+ " once.");
		}
		
		rawBricks.addAll(unsortedBricks);
		buildBrickDropTree();
	}

	private void buildBrickDropTree() {
		if (rawBricks.isEmpty()) {
			return;
		}
		
		// determine the upper and lower bounds of each dimension of the structure.
		Dimension low = new Dimension(rawBricks.get(0).getDimensions());
		Dimension high = new Dimension(rawBricks.get(0).getDimensions());
		determineStructureBounds(low, high);
		
		normalizeBricksAndStructureSize(low, high);
		structureSize = new Dimension(high);
		
		// build a 3D grid and place the bricks in it
		BrickGrid grid = new BrickGrid(structureSize);
		grid.refreshGrid(rawBricks);
		
		// starting from the top layer, determine which bricks depend on which others
		for (BrickInstance brick : rawBricks) {
			brick.setProcessed(false);
		}
		for (int z = 0; z < grid.getSize().z; z++) {
			for (int y = 0; y < grid.getSize().y; y++) {
				for (int x = 0; x < grid.getSize().x; x++) {
					if (!grid.isEmpty(x, y, z) && !grid.get(x, y, z).hasBeenProcessed()) {
						findBrickDependencies(grid.get(x, y, z), grid);
						grid.get(x, y, z).setProcessed(true);
					}
				}
			}
		}
		
		// go through all the tree nodes and find all those without dependencies
		for (BrickTreeNode node : brickNodes.values()) {
			if (!node.hasAnyDependencies()) {
				availableBricks.add(node);
			}
		}
	}
	
	private void determineStructureBounds(Dimension low, Dimension high) {
		for (BrickInstance brick : rawBricks) {
			Dimension brickLoc = brick.getLocation();
			Dimension brickDim = brick.getDimensions();
			if (brickLoc.x < low.x) {
				low.x = brickLoc.x;
			}
			if (brickLoc.x + brickDim.x> high.x) {
				high.x = brickLoc.x + brickDim.x;
			}
			if (brickLoc.y < low.y) {
				low.y = brickLoc.y;
			}
			if (brickLoc.y + brickDim.y > high.y) {
				high.y = brickLoc.y + brickDim.y;
			}
			if (brickLoc.z < low.z) {
				low.z = brickLoc.z;
			}
			if (brickLoc.z + brickDim.z > high.z) {
				high.z = brickLoc.z + brickDim.z;
			}
		}
	}

	private void normalizeBricksAndStructureSize(Dimension low, Dimension high) {
		// normalize the bricks in the structure to (0, 0, 0)
		for (BrickInstance brick : rawBricks) {
			Dimension brickLoc = brick.getLocation();
			brickLoc.x -= low.x;
			brickLoc.y -= low.y;
			brickLoc.z -= low.z;
		}
		
		// adjust low and high to match
		high.x -= low.x;
		high.y -= low.y;
		high.z -= low.z;
		
		low.x = 0;
		low.y = 0;
		low.z = 0;
	}
	
	/**
	 * Check each cell directly below a brick, and if there's another brick there, add it as a dependency.
	 * @param brick Brick to process
	 * @param grid 3D brick grid
	 */
	private void findBrickDependencies(BrickInstance brick, BrickGrid grid) {
		BrickTreeNode newNode = new BrickTreeNode(this, brick);
		Dimension dim = brick.getDimensions();
		Dimension loc = brick.getLocation();
		
		// base case: brick is at the bottom of the grid, lands on the bottom/baseplate.
		if (loc.z <= 0) {
			return;
		}
		
		// otherwise: check every cell below the brick for the brick(s) it sits upon.
		for (int x = loc.x; x < loc.x + dim.x; x++) {
			for (int y = loc.y; y < loc.y + dim.y; y++) {
				BrickInstance brickBelow = grid.get(x, y, loc.z - 1);
				if (brickBelow != null) {
					newNode.addDependencyBrick(brickBelow);
				}
			}
		}
	}
	
	public BrickInstance popRandomBrickToDrop() {
		if (availableBricks.isEmpty()) {
			return null;
		}
		
		Iterator<BrickTreeNode> iter = availableBricks.iterator();
		int pick = rand.nextInt(availableBricks.size());
		for (int i = 0; i < pick - 1; i++) {
			iter.next();
		}
		BrickTreeNode pickedNode = iter.next();
		
		// remove the picked node from list of available nodes
		availableBricks.remove(pickedNode);
		
		for (BrickTreeNode dependentNode : pickedNode.getDependentBricks()) {
			// remove the picked node as a dependency for its dependents, and add any now-independent nodes as available.
			dependentNode.getDependencyBricks().remove(pickedNode);
			if (!dependentNode.hasAnyDependencies()) {
				availableBricks.add(dependentNode);
			}
		}
		
		return pickedNode.getBrick();
	}
	
	public boolean hasNextBrick() {
		return !availableBricks.isEmpty();
	}
}
