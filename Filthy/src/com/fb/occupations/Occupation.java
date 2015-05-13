package com.fb.occupations;

import com.fb.occupations.behavior.BehaviorTree;

public class Occupation {

	private String name;
	private BehaviorTree behaviorTree;

	public Occupation(String name, BehaviorTree behaviorTree) {
		this.name = name;
		this.behaviorTree = behaviorTree;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BehaviorTree getBehaviorTree() {
		return behaviorTree;
	}

	public void setBehaviorTree(BehaviorTree behaviorTree) {
		this.behaviorTree = behaviorTree;
	}

}
