package com.fb.occupations;

import com.fb.occupations.behavior.BehaviorTree;

public class Occupation {

	private String name;
	private BehaviorTree behaviorTree;

	public Occupation(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BehaviorTree getBehaviorTree() {
		return this.behaviorTree;
	}

	public void setBehaviorTree(BehaviorTree behaviorTree) {
		this.behaviorTree = behaviorTree;
	}

}
