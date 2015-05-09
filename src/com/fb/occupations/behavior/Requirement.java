package com.fb.occupations.behavior;

public class Requirement {

	private String type;
	private String scope;
	private String objectId;
	
	public Requirement(String type, String scope, String objectId) {
		this.type = type;
		this.scope = scope;
		this.objectId = objectId;
		
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	
	
}
