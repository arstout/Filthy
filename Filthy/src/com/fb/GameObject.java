package com.fb;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class GameObject {

	private Map<String,String> attributes = new HashMap<String,String>();
	
	
	public GameObject(String name) {
		super();
		this.attributes.put("name", name);
		this.attributes.put("objectId", UUID.randomUUID().toString());
	}
	
	
	public Map<String, String> getAttributes() {
		return attributes;
	}

	public String getAttribute(String name){
		return this.attributes.get(name);
	}

	public void addAttribute(String name, String value) {
		
		if(this.attributes.get(name) != null) {
			this.attributes.remove(name);
		}
		
		this.attributes.put(name,value);
	
	}


	public String toString(){
		StringBuilder sb = new StringBuilder();
		Set<String> keys = this.attributes.keySet();
		Iterator<String> iter = keys.iterator();
		while(iter.hasNext()){
			String key = iter.next();
			sb.append(key + ": " + attributes.get(key)).append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}
	
}
