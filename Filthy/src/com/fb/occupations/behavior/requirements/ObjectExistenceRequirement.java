package com.fb.occupations.behavior.requirements;

import java.util.HashMap;
import java.util.Map;

import com.fb.gameobject.Attribute;

public class ObjectExistenceRequirement extends Requirement {

	private String type;
	private Map<String, Attribute> attributes = new HashMap<String, Attribute>();

	public ObjectExistenceRequirement(String type) {
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

	public void addAttribute(String attributeName, Attribute attribute) {
		this.attributes.put(attributeName, attribute);
	}


}
