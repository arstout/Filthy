package com.fb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameObjectMap {

	private Map<String, List<GameObject>> gameObjectMap = new HashMap<String, List<GameObject>>();

	public Map<String, List<GameObject>> getGameObjects() {
		return gameObjectMap;
	}

	public void add(GameObject gameObject, List<String> types) {
		Iterator<String> iter = types.iterator();
		while (iter.hasNext()) {
			String type = iter.next();
			List<GameObject> subList = gameObjectMap.get(type);
			if (subList == null) {
				subList = new ArrayList<GameObject>();
				gameObjectMap.put(type, subList);
			}
			subList.add(gameObject);
		}

	}

	public GameObject findGameObjectByType(String type) {
		List<GameObject> subList = gameObjectMap.get(type);
		if (subList == null || subList.size() == 0) {
			return null;
		}

		return subList.get(0);

	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Objects in the game:" + System.getProperty("line.separator"));
		Set<String> keys = gameObjectMap.keySet();
		Iterator<String> keyIter = keys.iterator();
		while (keyIter.hasNext()) {
			String key = keyIter.next();
			List<GameObject> gameObjectsByType = gameObjectMap.get(key);
			sb.append("Objects of type <" + key + ">").append(
					System.getProperty("line.separator"));
			Iterator<GameObject> objectIter = gameObjectsByType.iterator();
			while (objectIter.hasNext()) {
				GameObject gameObject = objectIter.next();
				sb.append(gameObject.toString()).append(
						System.getProperty("line.separator"));

			}

		}

		return sb.toString();
	}
}
