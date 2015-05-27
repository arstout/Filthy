package com.fb.occupations.behavior.requirements;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.fb.gameobject.GameObject;
import com.fb.gameobject.Person;

public class SkillCheckRequirement extends Requirement {

	private Map<String, Integer> skills = new HashMap<String, Integer>();

	public SkillCheckRequirement(Map<String, Integer> skills) {
		this.skills = skills;
	}

	public Map<String, Integer> getSkills() {
		return skills;
	}

	public void setSkills(Map<String, Integer> skills) {
		this.skills = skills;
	}

	public boolean check(Person person, Map<String, GameObject> requiredObjects) {
		// iterate through skills, make sure values are good

		Set<Entry<String, Integer>> setSkills = this.skills.entrySet();
		Iterator<Entry<String, Integer>> iter = setSkills.iterator();
		while (iter.hasNext()) {
			Entry<String, Integer> skill = iter.next();
			String skillName = skill.getKey();
			Integer skillValue = skill.getValue();
			Integer personSkillValue = person.getSkillValue(skillName);
			if (personSkillValue.intValue() < skillValue.intValue()) {
				// FAIL
				// System.out.println("\t\t\tRequired skill <" + skillName
				// + "> too low.  Expected <" + skillValue.intValue()
				// + ">, got <" + personSkillValue.intValue() + ">");
				return false;
			}

		}

		// System.out.println("\t\t\tRequired skills are sufficient.");
		return true;
	}

}
