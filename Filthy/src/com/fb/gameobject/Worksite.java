package com.fb.gameobject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Worksite extends GameObject {

	public Map<String, Integer> actionDurationMap = new HashMap<String, Integer>();

	private int turnsUntilWorkComplete = 0;
	private List<Person> workers = new ArrayList<Person>();
	private int maxWorkers;
	private boolean inUse = false;;

	protected Worksite(String name) {
		super(name);
		GameObjectStore.classifyGameObject(this, "worksite");
	}

	public String performWork(int workUnits) {
		this.turnsUntilWorkComplete -= workUnits;
		if (this.turnsUntilWorkComplete <= 0) {
			return "COMPLETED";
		} else {
			return "ACTIVE";
		}
	}

	public void addActionDuration(String name, int duration) {
		this.actionDurationMap.put(name, new Integer(duration));
	}

	public boolean isInUse() {
		return inUse;
	}

	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}

	public int getTurnsUntilWorkComplete() {
		return turnsUntilWorkComplete;
	}

	public void setTurnsUntilWorkComplete(String actionName) {
		this.turnsUntilWorkComplete = this.actionDurationMap.get(actionName)
		        .intValue();
		this.inUse = true;
	}

	public int getActionDuration(String name) {
		Integer duration = this.actionDurationMap.get(name);
		if (duration == null) {
			return 0;
		}
		return duration.intValue();
	}

	public void addWorker(Person worker) {
		if (this.workers.size() < this.maxWorkers) {
			this.workers.add(worker);
		}
	}

	public int getWorkerCount() {
		return this.workers.size();
	}

	public void removeWorker(Person worker) {
		this.workers.remove(worker);
		if (getWorkerCount() <= 0) {
			this.inUse = false;
		}
	}

	public int getMaxWorkers() {
		return maxWorkers;
	}

	public boolean hasAvailableWorkerRoom() {
		return this.workers.size() < this.maxWorkers;
	}

	public void setMaxWorkers(int maxWorkers) {
		this.maxWorkers = maxWorkers;
	}

}
