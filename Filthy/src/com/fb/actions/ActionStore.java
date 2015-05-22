package com.fb.actions;

import java.util.HashMap;
import java.util.Map;

public class ActionStore {

	private static Map<String, Action> actionStore = new HashMap<String, Action>();

	public static void addAction(Action action) {
		actionStore.put(action.getName(), action);
	}

	public Map<String, Action> getActions() {
		return actionStore;
	}

	public Action getAction(String name) {
		return actionStore.get(name);
	}

}