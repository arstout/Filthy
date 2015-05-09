package com.fb;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GameObjectMap {

	private Map<String,GameObject> gameObjectMap = new HashMap<String,GameObject>();
	
	
	public Map<String,GameObject> getGameObjects() {
		return gameObjectMap;
	}
	
	public void add(GameObject gameObject){
		gameObjectMap.put(gameObject.getName(),gameObject);
	}
	
	public GameObject findGameObjectByType(String type){
		Collection<GameObject> gameObjects = gameObjectMap.values();
		Iterator<GameObject> objectIter = gameObjects.iterator();
		while(objectIter.hasNext()) {
			GameObject gameObject = objectIter.next();
			if(gameObject.getType().equals(type)) {
				return gameObject;
			}
		}
		
		
		return null;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Objects in the game:" + System.getProperty("line.separator"));
		Collection<GameObject> gameObjects = gameObjectMap.values();
		Iterator<GameObject> objectIter = gameObjects.iterator();
		while(objectIter.hasNext()) {
			GameObject gameObject = objectIter.next();
			sb.append(gameObject.toString()).append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}
}
