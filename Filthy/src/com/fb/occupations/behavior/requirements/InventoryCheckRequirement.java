package com.fb.occupations.behavior.requirements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fb.gameobject.Attribute;
import com.fb.gameobject.GameObject;
import com.fb.gameobject.Person;

public class InventoryCheckRequirement extends Requirement {

	private String type;
	private String objectId;
	private Map<String, Attribute> attributes = new HashMap<String, Attribute>();

	protected InventoryCheckRequirement(String type, String objectId) {
		this.type = type;
		this.objectId = objectId;
	}

	public Map<String, Attribute> getAttributes() {
		return attributes;
	}

	public void addSimpleAttribute(String name, String value) {
		this.attributes.put(name, new Attribute(name, value));
	}

	public boolean check(Person person, Map<String, GameObject> requiredObjects) {

		System.out.println("checking inventory");
		// check Person's inventory for a suitable item
		List<GameObject> personInventory = person.getInventory();

		boolean failedInvCheck = false;
		for (GameObject gameObject : personInventory) {

			// is it of the correct type?
			if (!gameObject.getTypes().contains(type)) {
				failedInvCheck = true;
				continue;
			}

			// check each attribute
			for (String attributeName : attributes.keySet()) {

				String attr1 = attributes.get(attributeName).getValue();
				String attr2 = gameObject.getAttribute(attributeName)

				.getValue();

				if (!attr1.equals(attr2)) {
					// this attribute does not match.
					failedInvCheck = true;
					break;
				}

			}

			// we could be done - let's check
			if (!failedInvCheck) {
				requiredObjects.put(objectId, gameObject);
				System.out
				        .println("\t\t\tA suitable inventory item was successfully found.");
				return true;
			}

		}

		// object not found!
		System.out.println("\t\t\tA suitable inventory item was not found.");
		return false;
	}

}
