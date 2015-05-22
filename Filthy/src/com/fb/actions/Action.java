package com.fb.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fb.changes.Change;
import com.fb.gameobject.GameObject;
import com.fb.gameobject.Person;

public abstract class Action {

	protected String name;
	protected Map<String, GameObject> gameObjects = new HashMap<String, GameObject>();
	protected String state = "QUEUED";
	protected List<Change> changes = new ArrayList<Change>();

	public abstract void start(Person person);

	public abstract void complete(Person person);

	public abstract void turn(Person person);

	public void addGameObjects(Map<String, GameObject> gameObjects) {
		this.gameObjects = gameObjects;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<Change> getChanges() {
		return changes;
	}

	public void setChanges(List<Change> changes) {
		this.changes = changes;
	}

	public void addChange(Change change) {
		this.changes.add(change);
	}

}
