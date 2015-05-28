package com.fb.actions;

import java.util.List;
import java.util.Map;

import com.fb.changes.Change;
import com.fb.changes.ObjectAttributeChange;
import com.fb.gameobject.GameObject;
import com.fb.gameobject.Person;
import com.fb.gameobject.Worksite;

public class WorkAction extends Action {

	private Worksite worksite;

	public WorkAction(String name) {
		super();
		this.name = name;
	}

	public WorkAction(WorkAction action) {
		this.worksite = action.worksite;
		this.name = action.name;
		this.preActionChanges = action.preActionChanges;
		this.postActionChanges = action.postActionChanges;
		this.perTurnActionChanges = action.perTurnActionChanges;
	}

	@Override
	public void executeChanges(List<Change> changes) {

		for (Change change : changes) {
			if (change instanceof ObjectAttributeChange) {
				String objectId = ((ObjectAttributeChange) change)

				.getObjectId();

				GameObject objectToChange = null;
				if (objectId.equals("worksite")) {
					objectToChange = this.worksite;
				} else {
					objectToChange = gameObjects.get(objectId);
				}

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

	public void prepare(Person person, Map<String, GameObject> requiredObjects) {

		// get worksite
		Worksite worksite = (Worksite) (requiredObjects.get("worksite"));

		// add person to workers
		worksite.addWorker(person);

		// set turns on worksite only if it is not in use
		if (!worksite.isInUse()) {
			worksite.setTurnsUntilWorkComplete(getName());
		}

		// adjust person work output
		person.setWorkOutputPerTurn(1);

		// set worksite
		setWorksite(worksite);

		person.addActionToQueue(this);

	}

	public void complete(Person person) {

		System.out.println("\t" + person.getName() + " has completed "
		        + getName() + ".");

		person.removeActionFromQueue();

		// execute post-action changes
		System.out
		        .println("\t\tExecuting action complete steps (only if workers = 0)");
		this.worksite.removeWorker(person);
		if (this.worksite.getWorkerCount() == 0) {
			// run post action changes
			this.executeChanges(this.postActionChanges);
		}
	}

	public void turn(Person person) {
		System.out.println("\t" + person.getName() + " continues to work on "

		+ getName());

		if (getWorksite().getTurnsUntilWorkComplete() > 0) {

			this.state = getWorksite().performWork(
			        person.getWorkOutputPerTurn());

			this.executeChanges(this.perTurnActionChanges);

			System.out.println("\t\tWork left to do on "
			        + getWorksite().getName()

			        + " is " + getWorksite().getTurnsUntilWorkComplete());

		} else {
			this.state = "COMPLETED";
		}

	}

	public Worksite getWorksite() {
		return worksite;
	}

	public void setWorksite(Worksite worksite) {
		this.worksite = worksite;
	}

	@Override
	public void start(Person person) {

		System.out.println("\t" + person.getName()
		        + " is about to begin work on " + getName());

		// pre action steps

		this.executeChanges(this.preActionChanges);

		this.state = "ACTIVE";

	}

}
