package com.fb.actions;

import java.util.Iterator;

import com.fb.Main;
import com.fb.changes.Change;
import com.fb.changes.ObjectAttributeChange;
import com.fb.gameobject.GameObject;
import com.fb.gameobject.Person;
import com.fb.gameobject.Worksite;

public class WorkAction extends Action {

	private Worksite worksite;
	
	public WorkAction(String name){
		super();
		this.name = name;
	}
	
	public WorkAction(WorkAction action){
		this.worksite = action.worksite;
		this.name = action.name;
		this.changes = action.changes;
	}
	

	public void complete(Person person) {


		System.out.println(person.getName() + " has completed " + getName());
		person.removeActionFromQueue();
		
		// execute post-action changes
		System.out.println("executing action complete steps");
		this.worksite.removeWorker(person);
		Iterator<Change> changeIter = changes.iterator();
		while (changeIter.hasNext()) {
			Change change = changeIter.next();
			if (change instanceof ObjectAttributeChange) {
				String objectId = ((ObjectAttributeChange) change).getObjectId();
				GameObject objectToChange = null;
				if(objectId.equals("worksite")){
					objectToChange = this.worksite;
				} else {
					objectToChange = gameObjects.get(objectId);
				}
				
				String attribute = ((ObjectAttributeChange) change)
						.getAttribute();
				String value = ((ObjectAttributeChange) change).getValue();
				System.out.println("Changing object " + objectToChange.getName() + ": Attribute " + attribute + " will be given a value of " + value);
				ObjectAttributeChange.modifyAttributeOnObject(objectToChange,
						attribute, value);
			}
		}

	}

	public void turn(Person person){
		System.out.println(person.getName() + " continues to work on " + getName());
		this.state = getWorksite().performWork(person.getWorkOutputPerTurn());
		System.out.println("Work left to do on " + getWorksite().getName() + " is " + getWorksite().getTurnsUntilWorkComplete());
	}
	
	public Worksite getWorksite() {
		return worksite;
	}

	public void setWorksite(Worksite worksite) {
		this.worksite = worksite;
	}

	@Override
	public void start(Person person) {
		System.out.println(person.getName() + " is about to begin work on " + getName());
		this.state = "ACTIVE";
		
	}

	
	
}
