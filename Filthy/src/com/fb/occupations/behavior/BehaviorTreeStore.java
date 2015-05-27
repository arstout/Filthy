package com.fb.occupations.behavior;

import java.util.HashMap;
import java.util.Map;

public class BehaviorTreeStore {

	private static Map<String, BehaviorTree> behaviorTreeStore = new HashMap<String, BehaviorTree>();

	public Map<String, BehaviorTree> getBehaviorTrees() {
		return behaviorTreeStore;
	}

	public static BehaviorTree createBehaviorTree(String name) {
		BehaviorTree bt = new BehaviorTree(name);
		behaviorTreeStore.put(name, bt);
		return bt;
	}

	public BehaviorTree getBehaviorTree(String name) {
		return behaviorTreeStore.get(name);
	}

}
