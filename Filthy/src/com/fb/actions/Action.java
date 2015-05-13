package com.fb.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fb.GameObject;
import com.fb.Main;
import com.fb.Person;
import com.fb.changes.ObjectAttributeChange;
import com.fb.changes.Change;

public class Action {

	private String name;
	private int duration;
	private Person executor;
	private Map<String, GameObject> gameObjects = new HashMap<String, GameObject>();
	private int startTurn;
	private int finishTurn;
	private int pausedTurn;
	private String state = "QUEUED";
	private List<Change> postActionChanges = new ArrayList<Change>();

	public Action(String name, int duration) {
		super();
		this.name = name;
		this.duration = duration;
	}

	public Action(Action action, Person executor) {
		this.name = action.name;
		this.duration = action.duration;
		this.executor = executor;
		this.postActionChanges = action.postActionChanges;
	}

	public void start() {
		this.startTurn = Main.turn;
		this.finishTurn = this.startTurn + this.duration;
		this.state = "ACTIVE";

	}

	public void complete() {

		// execute post-action changes
		System.out.println("completing action <" + this.getName() + ">");
		System.out.println("there are <" + postActionChanges.size()
				+ "> changes to make.");
		Iterator<Change> changeIter = postActionChanges.iterator();
		while (changeIter.hasNext()) {
			Change change = changeIter.next();
			if (change instanceof ObjectAttributeChange) {
				GameObject objectToChange = gameObjects
						.get(((ObjectAttributeChange) change).getObjectId());
				String attribute = ((ObjectAttributeChange) change)
						.getAttribute();
				String value = ((ObjectAttributeChange) change).getValue();
				System.out.println("Changing object " + objectToChange);
				ObjectAttributeChange.modifyAttributeOnObject(objectToChange,
						attribute, value);
			}
		}

	}

	public void addGameObjects(Map<String, GameObject> gameObjects) {
		this.gameObjects = gameObjects;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Person getExecutor() {
		return executor;
	}

	public void setExecutor(Person executor) {
		this.executor = executor;
	}

	public int getStartTurn() {
		return startTurn;
	}

	public void setStartTurn(int startTurn) {
		this.startTurn = startTurn;
	}

	public int getFinishTurn() {
		return finishTurn;
	}

	public void setFinishTurn(int finishTurn) {
		this.finishTurn = finishTurn;
	}

	public int getPausedTurn() {
		return pausedTurn;
	}

	public void setPausedTurn(int pausedTurn) {
		this.pausedTurn = pausedTurn;
	}

	public String getState() {

		// before returning state, let's see if it needs moved to complete.
		if (Main.turn == this.finishTurn) {
			this.state = "COMPLETED";
		}
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<Change> getPostActionChanges() {
		return postActionChanges;
	}

	public void setPostActionChanges(List<Change> postActionChanges) {
		this.postActionChanges = postActionChanges;
	}

	public void addPostActionChange(Change change) {
		this.postActionChanges.add(change);
	}

	public void onDone() {
		// execute changes
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Action <" + this.name + "> is " + this.state).append(
				System.getProperty("line.separator"));
		switch (this.state) {
		case "QUEUED":
			sb.append("This action is QUEUED.").append(
					System.getProperty("line.separator"));
			break;
		case "ACTIVE":
			sb.append(
					"This action is ACTIVE.  Number of turns remaining is <"
							+ (this.finishTurn - Main.turn) + ">").append(
					System.getProperty("line.separator"));
			break;
		case "COMPLETED":
			sb.append("This action is COMPLETED").append(
					System.getProperty("line.separator"));
			break;
		}

		Collection<GameObject> gameObjects = this.gameObjects.values();
		Iterator<GameObject> objectIter = gameObjects.iterator();
		while (objectIter.hasNext()) {
			GameObject gameObject = objectIter.next();
			sb.append("Attached object: " + gameObject + ".").append(
					System.getProperty("line.separator"));
		}
		return sb.toString();
	}
}
