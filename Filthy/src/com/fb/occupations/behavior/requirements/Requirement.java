package com.fb.occupations.behavior.requirements;

import java.util.Map;

import com.fb.gameobject.GameObject;
import com.fb.gameobject.Person;

public abstract class Requirement {

	public abstract boolean check(Person person,
	        Map<String, GameObject> requiredObjects);

}