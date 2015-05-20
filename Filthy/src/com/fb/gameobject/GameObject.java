package com.fb.gameobject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.fb.object.person.Person;

public class GameObject {

	private Map<String, Attribute> attributes = new HashMap<String, Attribute>();
	
	private Set<String> types = new HashSet<String>();
	private String name;
	private List<Person> workers = new ArrayList<Person>();
	private int maxWorkers;
	private Person owner;

	public GameObject(String name) {
		this.name = name;
		addAttribute("objectId", UUID.randomUUID().toString());
		GameObjectStore.addGameObject(this);
	}

	public Map<String, Attribute> getAttributes() {
		return attributes;
	}

	public Attribute getAttribute(String name) {
		return this.attributes.get(name);
	}

	public void addAttribute(String name, String value) {

		this.attributes.put(name, new Attribute(name,value));

	}
	
	
	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	public void addWorker(Person worker){
		if(this.workers.size() < this.maxWorkers){
			this.workers.add(worker);
		}
		
	}
	
	public void removeWorker(Person worker){ 
		this.workers.remove(worker);
	}
	
	public int getMaxWorkers() {
		return maxWorkers;
	}

	public void setMaxWorkers(int maxWorkers) {
		this.maxWorkers = maxWorkers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
