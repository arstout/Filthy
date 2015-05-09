package com.fb;

public class GameObject {

	private String name;
	private String type;
	
	
	public GameObject(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String toString(){
		return "Object <" + this.name + "> is of type <" + this.type +">";
	}
	
}
