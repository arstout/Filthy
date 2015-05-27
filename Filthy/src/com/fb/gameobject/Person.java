package com.fb.gameobject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import com.fb.actions.Action;
import com.fb.occupations.Occupation;

public class Person extends GameObject {

	private Queue<Action> actionQueue = new PriorityQueue<Action>();
	private Occupation occupation;
	private Person employer;
	private int age;
	private Set<Person> employees = new HashSet<Person>();
	private Map<String, Integer> skills = new HashMap<String, Integer>();
	private int workOutputPerTurn = 1;


	protected Person(String name, int age) {
		super(name);

		GameObjectStore.classifyGameObject(this, "person");
	}

	public void addSkill(String skill, Integer value) {
		skills.put(skill, value);
	}


	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Integer getSkillValue(String name) {
		Integer val = skills.get(name);
		if (val == null) {
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

	public void addEmployee(Person employee) {
		this.employees.add(employee);
	}

	public void removeEmployee(Person employee) {
		this.employees.remove(employee);
	}

	public int getWorkOutputPerTurn() {
		return workOutputPerTurn;
	}

	public void setWorkOutputPerTurn(int workOutputPerTurn) {
		this.workOutputPerTurn = workOutputPerTurn;
	}

	public Set<Person> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Person> employees) {
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
