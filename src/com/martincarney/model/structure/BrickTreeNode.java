package com.martincarney.model.structure;

import java.util.HashSet;
import java.util.Set;

import com.martincarney.model.brick.BrickInstance;
import com.martincarney.model.structure.BrickStructure.BrickTreeNode;

class BrickTreeNode {
	/**
	 * 
	 */
	private final BrickStructure brickStructure;

	private BrickInstance brick;
	
	private Set<BrickTreeNode> dependentBricks; // these are dependent on this brick
	private Set<BrickTreeNode> dependencyBricks; // this brick depends on these
	
	public BrickTreeNode(BrickStructure brickStructure, BrickInstance brick) {
		this.brickStructure = brickStructure;
		if (brick == null) {
			throw new NullPointerException("Node must have a non-null " + BrickInstance.class.getSimpleName() + ".");
		}
		
		this.brick = brick;
		
		dependentBricks = new HashSet<BrickTreeNode>();
		dependencyBricks = new HashSet<BrickTreeNode>();
	}
	
	public BrickInstance getBrick() {
		return brick;
	}
	
	public void addDependencyBrick(BrickInstance brickThisBrickDependsOn) {
		BrickTreeNode node = this.brickStructure.brickNodes.get(brickThisBrickDependsOn);
		if (node == null) {
			node = new BrickTreeNode(this.brickStructure, brickThisBrickDependsOn);
			this.brickStructure.brickNodes.put(brickThisBrickDependsOn, node);
		}
		node.dependentBricks.add(this);
		dependencyBricks.add(node);
		
	}
	
	public boolean hasAnyDependencies() {
		return !dependencyBricks.isEmpty();
	}

	
	public Set<BrickTreeNode> getDependentBricks() {
		return dependentBricks;
	}

	
	public Set<BrickTreeNode> getDependencyBricks() {
		return dependencyBricks;
	}
}