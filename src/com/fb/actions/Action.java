package com.fb.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fb.Main;
import com.fb.Person;
import com.fb.changes.Change;

public class Action {

	private String name;
	private int duration;
	private Person executor;
	private Map<String,Object> objects = new HashMap<String,Object>();
	private int startTurn;
	private int finishTurn;
	private int pausedTurn;
	private String state = "QUEUED";
	private List<Change> changes;
	
	
	public Action(String name, int duration) {
		super();
		this.name = name;
		this.duration = duration;
	}
	
	public Action(Action action){
		this.name = action.name;
		this.duration = action.duration;
	}
	
	public void start() {
		this.startTurn = Main.turn;
		this.finishTurn = this.startTurn + this.duration;
		this.state = "ACTIVE";
		
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
		
		// before retrning state, let's see if it needs moved to complete.
		if(Main.turn == this.finishTurn) {
			this.state = "COMPLETED";
		}
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
	

	public void onDone() {
		// execute changes
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Action <" + this.name + "> is " + this.state).append(System.getProperty("line.separator"));
		switch(this.state){
		case "QUEUED":
			sb.append("This action is QUEUED.").append(System.getProperty("line.separator"));
			break;
		case "ACTIVE":
			sb.append("This action is ACTIVE.  Number of turns remaining is <" + (this.finishTurn - Main.turn) + ">" ).append(System.getProperty("line.separator"));
			break;
		case "COMPLETED":
			sb.append("This action is COMPLETED").append(System.getProperty("line.separator"));
			break;
		}
		return sb.toString();
	}
}
