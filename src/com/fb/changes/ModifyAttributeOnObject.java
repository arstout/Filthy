package com.fb.changes;

import java.lang.reflect.Field;

public class ModifyAttributeOnObject extends Change{

	
	public ModifyAttributeOnObject(Object o, String attributeName, String attributeValue)
	{
		
		try {
		
			Class<?> c = o.getClass();
			Field f = c.getDeclaredField(attributeName);
			f.setAccessible(true);
			f.set(o,attributeValue);
			
		} catch (Exception e) {}
	}
}
