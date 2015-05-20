package com.fb.object.person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.fb.actions.Action;
import com.fb.gameobject.GameObject;
import com.fb.gameobject.GameObjectStore;
import com.fb.occupations.Occupation;

public class Person extends GameObject{

	private Queue<Action> actionQueue = new PriorityQueue<Action>();
	private Occupation occupation;
	private Person employer;
	private List<Person> employees = new ArrayList<Person>();
	private Map<String,Integer> skills = new HashMap<String,Integer>();
	
	public Person(String name, String age) {
		super(name);
		GameObjectStore.classifyGameObject(this, "person");
		this.addAttribute("age",age);
	}
	
	public void addSkill(String skill, Integer value){
		skills.put(skill,value);
	}
	
	public Integer getSkillValue(String name) {
		Integer val = skills.get(name);
		if(val == null) {
			return new Integer(0);
		}
		return val;
	}
	
	public Person getEmployer() {
		return employer;
	}

	public void setEmployer(Person employer) {
		this.employer = employer;
	}



	public List<Person> getEmployees() {
		return employees;
	}



	public void setEmployees(List<Person> employees) {
		this.employees = employees;
	}



	public Occupation getOccupation() {
		return occupation;
	}



	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}



	public Action getCurrentAction() {
		return this.actionQueue.peek();
	}

	public void removeActionFromQueue() {
		this.actionQueue.remove();
	}

	public void addActionToQueue(Action action) {
		this.actionQueue.add(action);
	}

	

}
