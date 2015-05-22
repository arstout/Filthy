package com.fb.actions;

import com.fb.actions.Action;
import com.fb.gameobject.Person;

public class SimpleAction extends Action {

	private int duration;
	private int turnsUntilComplete;

	public SimpleAction(String name, int duration) {
		this.name = name;
		this.duration = duration;
		this.turnsUntilComplete = duration;
	}

	public SimpleAction(SimpleAction action) {
		this.duration = action.duration;
		this.name = action.name;
	}

	@Override
	public void complete(Person person) {
		System.out.println(person.getName() + " has completed " + getName());
		person.removeActionFromQueue();
	}

	@Override
	public void start(Person person) {
		System.out.println(person.getName() + " is about to begin work on "
		        + getName());
		this.state = "ACTIVE";
	}

	public void turn(Person person) {
		System.out.println(person.getName() + " continues to work on "
		        + getName());
		this.turnsUntilComplete--;
		System.out.println(this.name + " has " + turnsUntilComplete
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
