package com.fb.actions;

import java.util.HashMap;
import java.util.Map;

public class ActionStore {

	private static Map<String, Action> actionStore = new HashMap<String, Action>();

	public static SimpleAction createSimpleAction(String name, int duration) {
		SimpleAction action = new SimpleAction(name, duration);
		actionStore.put(name, action);

		return action;

	}

	public static WorkAction createWorkAction(String name) {
		WorkAction action = new WorkAction(name);
		actionStore.put(name, action);

		return action;
	}

	public static Action getAction(String name) {
		Action action = actionStore.get(name);
		if (action instanceof WorkAction) {
			return new WorkAction((WorkAction) action);
		} else if (action instanceof SimpleAction) {
			return new SimpleAction((SimpleAction) action);
		}

		return null;
	}

}
