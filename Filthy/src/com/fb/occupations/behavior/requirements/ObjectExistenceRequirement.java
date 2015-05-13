package com.fb.occupations.behavior.requirements;

import java.util.HashMap;
import java.util.Map;

public class ObjectExistenceRequirement extends Requirement {

	private String objectId;
	private String type;
	private Map<String, String> attributes = new HashMap<String, String>();

	public ObjectExistenceRequirement(String objectId, String type) {
		this.objectId = objectId;
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, String> getAttributeChecks() {
		return attributes;
	}

	public void addAttributeCheck(String attribute, String value) {
		this.attributes.put(attribute, value);
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

}
