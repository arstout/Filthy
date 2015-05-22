package com.fb.occupations.behavior;

import java.util.HashMap;
import java.util.Map;

public class BehaviorTreeStore {

	private static Map<String, BehaviorTree> behaviorTreeStore = new HashMap<String, BehaviorTree>();

	public Map<String, BehaviorTree> getBehaviorTrees() {
		return behaviorTreeStore;
	}

	public static void addBehaviorTree(BehaviorTree behaviorTree) {
		behaviorTreeStore.put(behaviorTree.getName(), behaviorTree);
	}

	public BehaviorTree getBehaviorTree(String name) {
		return behaviorTreeStore.get(name);
	}

}
