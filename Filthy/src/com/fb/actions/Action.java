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
	protected List<Change> preActionChanges = new ArrayList<Change>();
	protected List<Change> postActionChanges = new ArrayList<Change>();
	protected List<Change> perTurnActionChanges = new ArrayList<Change>();

	
	protected Action(String name){
		this.name = name;
	}
	
	protected Action(Action action){
		this.name = action.name;
		this.gameObjects = action.gameObjects;
		this.state = "QUEUED";
		this.preActionChanges = action.preActionChanges;
		this.postActionChanges = action.postActionChanges;
		this.perTurnActionChanges = action.perTurnActionChanges;
		
	}
	
	public abstract void prepare(Person person,
	        Map<String, GameObject> requiredObjects);

	public abstract void start(Person person);

	public abstract void complete(Person person);

	public abstract void turn(Person person);

	public abstract void executeChanges(List<Change> changes);

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

	public List<Change> getPreActionChanges() {
		return preActionChanges;
	}

	public List<Change> getPostActionChanges() {
		return postActionChanges;
	}

	public List<Change> getPerTurnActionChanges() {
		return perTurnActionChanges;
	}

	public void addPreActionChange(Change change) {
		this.preActionChanges.add(change);
	}

	public void addPostActionChange(Change change) {
		this.postActionChanges.add(change);
	}

	public void addPerTurnActionChange(Change change) {
		this.perTurnActionChanges.add(change);
	}

}
