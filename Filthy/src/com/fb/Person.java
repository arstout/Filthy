package com.fb;

import java.util.PriorityQueue;
import java.util.Queue;

import com.fb.actions.Action;
import com.fb.occupations.Occupation;

public class Person {

	private String name;
	private int age;
	private Object[] inventory;
	private Queue<Action> actionQueue = new PriorityQueue<Action>();
	private Occupation occupation;
	
	
	public Person(String name, int age, Occupation occupation) {
		this.name = name;
		this.age = age;
		this.occupation = occupation;
	}

	public Action getCurrentAction(){
		return this.actionQueue.peek();
	}

	public void removeActionFromQueue() {
		this.actionQueue.remove();
	}
	public void addActionToQueue(Action action){
		this.actionQueue.add(action);
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public Object[] getInventory() {
		return inventory;
	}


	public void setInventory(Object[] inventory) {
		this.inventory = inventory;
	}


	public Occupation getOccupation() {
		return occupation;
	}


	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.name + " is a " + this.occupation.getName()).append(System.getProperty("line.separator"));
		sb.append("Current Action: " + this.actionQueue.peek());
		return sb.toString();
	}
	
	
}
