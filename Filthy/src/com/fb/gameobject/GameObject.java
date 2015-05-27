package com.fb.gameobject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class GameObject {

	private Map<String, Attribute> attributes = new HashMap<String, Attribute>();

	private String name = "";
	private Person owner;
	private List<GameObject> inventory = new ArrayList<GameObject>();
	private Set<String> types = new HashSet<String>();

	protected GameObject(String name) {
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

	public Set<String> getTypes() {
		return types;
	}

	public void addType(String type) {
		this.types.add(type);
	}

	public void removeType(String type) {
		this.types.remove(type);
	}

	public void addAttribute(String name, String value) {
		this.attributes.put(name, new Attribute(name, value));
	}

	public List<GameObject> getInventory() {
		return this.inventory;
	}

	public void addToInventory(GameObject gameObject) {
		this.inventory.add(gameObject);
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
