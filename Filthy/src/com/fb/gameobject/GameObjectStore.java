package com.fb.gameobject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameObjectStore {

	// object store implemented as in-memory map of lists (map key = type)
	private static Map<String, Set<GameObject>> gameObjectStore = new HashMap<String, Set<GameObject>>();

	static {
		gameObjectStore.put("all", new HashSet<GameObject>());
	}

	public Set<GameObject> getAllGameObjects() {
		return getSubStore("all");
	}

	public static Person createPerson(String name, int age) {

		return new Person(name, age);

	}

	public static GameObject createGameObject(String name) {
		return new GameObject(name);
	}

	public static Worksite createWorksite(String name) {
		return new Worksite(name);
	}

	public static Set<GameObject> getAllPeople() {
		return getSubStore("person");
	}

	public static void addGameObject(GameObject gameObject) {
		getSubStore("all").add(gameObject);
	}

	public static Set<GameObject> getSubStore(String type) {

		Set<GameObject> subStore = gameObjectStore.get(type);
		if (subStore == null) {
			subStore = new HashSet<GameObject>();
			gameObjectStore.put(type, subStore);
		}
		return subStore;
	}

	public static void classifyGameObject(GameObject gameObject, String type) {

		getSubStore(type).add(gameObject);
		gameObject.addType(type);
	}

	public static void declassifyGameObject(GameObject gameObject, String type) {

		getSubStore(type).remove(gameObject);
		gameObject.removeType(type);
	}

	public static void removeGameObject(GameObject gameObject) {
		Set<String> keys = gameObjectStore.keySet();
		for (String key : keys) {
			declassifyGameObject(gameObject, key);
		}
	}

	public static GameObject findGameObject(String type,

	Map<String, Attribute> attributes, Person owner) {

		Set<GameObject> subSet = getSubStore(type);

		// if subSet is null, an object of this type doesn't exist, therefore
		// the object doesn't exist.
		if (subSet == null) {
			return null;
		}

		if (subSet.size() == 0) {
			return null;
		}

		// we need to be a bit specific here. Don't allow no attributes
		if (attributes == null) {
			return null;
		}

		if (attributes.size() == 0) {
			return null;
		}

		// ok, there are objects. Let's filter further
		for (GameObject gameObject : subSet) {

			boolean failedAttrCheck = false;
			// check each attribute
			for (String attributeName : attributes.keySet()) {

				String attr1 = attributes.get(attributeName).getValue();
				String attr2 = gameObject.getAttribute(attributeName)

				.getValue();

				if (!attr1.equals(attr2)) {
					// this attribute does not match.
					failedAttrCheck = true;
					break;
				}

			}

			// check if we matched on all attributes
			if (!failedAttrCheck && gameObject.getOwner().equals(owner)) {
				// winner winner
				return gameObject;
			}

		}

		// If we're here, then no objects matched.
		return null;

	}

	public static Worksite findWorksite(Map<String, Attribute> attributes,

	Person owner) {

		Set<GameObject> subSet = getSubStore("worksite");

		// if subSet is null, an object of this type doesn't exist, therefore
		// the object doesn't exist.
		if (subSet == null) {
			return null;
		}

		if (subSet.size() == 0) {
			return null;
		}

		// we need to be a bit specific here. Don't allow no attributes
		if (attributes == null) {
			return null;
		}

		if (attributes.size() == 0) {
			return null;
		}

		// ok, there are objects. Let's filter further
		for (GameObject gameObject : subSet) {

			boolean failedAttrCheck = false;
			// check each attribute
			for (String attributeName : attributes.keySet()) {

				String attr1 = attributes.get(attributeName).getValue();
				String attr2 = gameObject.getAttribute(attributeName)

				.getValue();

				if (!attr1.equals(attr2)) {
					// this attribute does not match.
					failedAttrCheck = true;
					break;
				}

			}

			// check if we matched on all attributes

			if (!failedAttrCheck
			        && (gameObject.getOwner().equals(owner) || gameObject
			                .getOwner().equals(owner.getEmployer()))
			        && ((Worksite) gameObject).hasAvailableWorkerRoom()) {
				// winner winner chicken dinner
				return (Worksite) gameObject;
			}

		}

		// If we're here, then no objects matched.
		return null;

	}

}
