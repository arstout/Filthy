package com.fb.changes;

import com.fb.GameObject;

public class ObjectAttributeChange extends Change {

	private String objectId;
	private String attribute;
	private String value;

	public ObjectAttributeChange(String objectId, String attribute, String value) {
		super();
		this.type = "ATTRIBUTE";
		this.objectId = objectId;
		this.attribute = attribute;
		this.value = value;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static void modifyAttributeOnObject(GameObject object,
			String attributeName, String attributeValue) {

		try {

			object.addAttribute(attributeName, attributeValue);

			// Class<?> c = o.getClass();
			// Field f = c.getDeclaredField(attributeName);
			// f.setAccessible(true);
			// f.set(o,attributeValue);

		} catch (Exception e) {
		}
	}

}
