package com.fb.occupations.behavior.requirements;

import java.util.HashMap;
import java.util.Map;

import com.fb.gameobject.Attribute;
import com.fb.gameobject.GameObject;
import com.fb.gameobject.GameObjectStore;
import com.fb.gameobject.Person;
import com.fb.gameobject.Worksite;

public class WorksiteRequirement extends Requirement {

	private String type;
	private Map<String, Attribute> attributes = new HashMap<String, Attribute>();

	protected WorksiteRequirement(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, Attribute> getAttributes() {
		return attributes;
	}

	public void addSimpleAttribute(String name, String value) {
		this.attributes.put(name, new Attribute(name, value));
	}

	public boolean check(Person person, Map<String, GameObject> requiredObjects) {

		Worksite worksite = GameObjectStore.findWorksite(this.attributes,
		        person);

		if (worksite == null) {
			// object not found!
			// System.out.println("\t\t\tA suitable worksite was not found.");
			return false;
		}

		// System.out.println("\t\t\tA suitable worksite was successfully found.");

		requiredObjects.put("worksite", worksite);

		return true;
	}

}
