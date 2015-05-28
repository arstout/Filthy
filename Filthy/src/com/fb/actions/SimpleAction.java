package com.fb.actions;

import java.util.List;
import java.util.Map;

import com.fb.actions.Action;
import com.fb.changes.Change;
import com.fb.changes.ObjectAttributeChange;
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
		this.preActionChanges = action.preActionChanges;
		this.postActionChanges = action.postActionChanges;
		this.perTurnActionChanges = action.perTurnActionChanges;

		this.turnsUntilComplete = action.duration;
	}

	public void prepare(Person person, Map<String, GameObject> requiredObjects) {

		person.addActionToQueue(this);
		this.turnsUntilComplete = this.duration;

	}

	@Override
	public void executeChanges(List<Change> changes) {

		for (Change change : changes) {

			if (change instanceof ObjectAttributeChange) {
				String objectId = ((ObjectAttributeChange) change)

				.getObjectId();

				GameObject objectToChange = null;
				objectToChange = gameObjects.get(objectId);

				String attribute = ((ObjectAttributeChange) change)
				        .getAttribute();
				String value = ((ObjectAttributeChange) change).getValue();
				System.out.println("\t\tChanging object "

				+ objectToChange.getName() + ": Attribute " + attribute
				        + " will be given a value of " + value + ".");

				ObjectAttributeChange.modifyAttributeOnObject(objectToChange,
				        attribute, value);
			}
		}
	}

	@Override
	public void complete(Person person) {

		System.out.println("\t" + person.getName() + " has completed "
		        + getName());

		person.removeActionFromQueue();

		this.executeChanges(this.postActionChanges);

	}

	@Override
	public void start(Person person) {

		System.out.println("\t" + person.getName()
		        + " is about to begin work on " + getName());

		this.executeChanges(this.preActionChanges);
		this.state = "ACTIVE";
	}

	public void turn(Person person) {

		System.out.println("\t" + person.getName() + " continues to perform "

		+ getName());
		if (this.turnsUntilComplete > 0) {

			this.turnsUntilComplete--;
			System.out.println("\t\t" + this.name + " has "
			        + turnsUntilComplete + " turns left until completion.");

			this.executeChanges(this.perTurnActionChanges);

		}

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
