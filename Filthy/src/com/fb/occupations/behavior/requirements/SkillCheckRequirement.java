package com.fb.occupations.behavior.requirements;

import java.util.HashMap;
import java.util.Map;

import com.fb.gameobject.Person;

public class SkillCheckRequirement extends Requirement {

	private Map<String, Integer> skills = new HashMap<String, Integer>();

	public SkillCheckRequirement( Map<String,Integer> skills) {
		this.skills = skills;
	}

	public Map<String, Integer> getSkills() {
		return skills;
	}

	public void setSkills(Map<String, Integer> skills) {
		this.skills = skills;
	}

	

}
