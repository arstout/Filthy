package com.fb.occupations.behavior.requirements;

public class RequirementFactory {

	public static WorksiteRequirement createWorksiteRequirement(String name) {
		return new WorksiteRequirement(name);
	}

	public static SkillCheckRequirement createSkillCheckRequirement() {
		return new SkillCheckRequirement();
	}

	public static InventoryCheckRequirement createInventoryCheckRequirement(
	        String type, String objectId) {
		return new InventoryCheckRequirement(type, objectId);
	}

}
