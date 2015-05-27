package com.fb.actions;

import java.util.Map;

import com.fb.actions.Action;
import com.fb.gameobject.GameObject;
import com.fb.gameobject.Person;

public class SimpleAction extends Action {

	private int duration;
	private int turnsUntilComplete;

	protected SimpleAction(String name, int duration) {

		this.name = name;
		this.duration = duration;
		this.turnsUntilComplete = duration;
	}

	protected SimpleAction(SimpleAction action) {

		this.duration = action.duration;
		this.name = action.name;

		this.turnsUntilComplete = action.duration;
	}

	public void prepare(Person person, Map<String, GameObject> requiredObjects) {

		person.addActionToQueue(this);
		this.turnsUntilComplete = this.duration;

	}

	@Override
	public void complete(Person person) {

		System.out.println("\t" + person.getName() + " has completed "
		        + getName());

		person.removeActionFromQueue();
	}

	@Override
	public void start(Person person) {

		System.out.println("\t" + person.getName()
		        + " is about to begin work on " + getName());

		this.state = "ACTIVE";
	}

	public void turn(Person person) {

		System.out.println("\t" + person.getName() + " continues to perform "

		+ getName());
		this.turnsUntilComplete--;
		System.out.println("\t\t" + this.name + " has " + turnsUntilComplete
		        + " turns left until completion.");
		if (this.turnsUntilComplete <= 0) {
			this.state = "COMPLETED";
		}
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
