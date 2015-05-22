package com.fb.occupations.behavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import com.fb.actions.Action;
import com.fb.gameobject.Attribute;
import com.fb.gameobject.GameObject;
import com.fb.gameobject.GameObjectStore;
import com.fb.gameobject.Person;
import com.fb.gameobject.Worksite;
import com.fb.occupations.behavior.requirements.WorksiteRequirement;
import com.fb.occupations.behavior.requirements.Requirement;
import com.fb.occupations.behavior.requirements.SkillCheckRequirement;

public class DecisionNode {

	/* Name of the Decision Node */
	private String name;

	/* Array list of the requirements */
	private List<Requirement> requirements = new ArrayList<Requirement>();

	/* Steps that will be processed if Node returns true */
	private Queue<DecisionStep> passedDecisionSteps = new PriorityQueue<DecisionStep>();

	/* Steps that will be processed if Node returns false */
	private Queue<DecisionStep> failedDecisionSteps = new PriorityQueue<DecisionStep>();

	/* Map of actions to take for this Node */
	private Map<String, Action> actions = new HashMap<String, Action>();

	public DecisionNode(String name) {
		super();
		this.name = name;
	}

	public Map<String, GameObject> checkRequirements(Person person) {

		// check requirements
		Iterator<Requirement> requirementsIter = this.requirements.iterator();

		Map<String, GameObject> requiredObjects = new HashMap<String, GameObject>();
		while (requirementsIter.hasNext()) {
			Requirement currentRequirement = requirementsIter.next();

			// Check to see if an object exists and verify attributes on this
			// object.
			// TODO: Verify this with additional scope (personal, communal,
			// global)
			if (currentRequirement instanceof WorksiteRequirement) {

				Map<String, Attribute> attributes = ((WorksiteRequirement) currentRequirement)
				        .getAttributes();

				Worksite worksite = GameObjectStore.findWorksite(attributes,
				        person);

				if (worksite == null) {
					// object not found!
					System.out.println("A suitable worksite was not found.");
					return null;

				}

				System.out
				        .println("A suitable worksite was successfully found.");
				requiredObjects.put("worksite", worksite);

			} else if (currentRequirement instanceof SkillCheckRequirement) {

				// iterate through skills, make sure values are good
				Map<String, Integer> requiredSkills = ((SkillCheckRequirement) currentRequirement)
				        .getSkills();
				Set<Entry<String, Integer>> setSkills = requiredSkills
				        .entrySet();
				Iterator<Entry<String, Integer>> iter = setSkills.iterator();
				while (iter.hasNext()) {
					Entry<String, Integer> skill = iter.next();
					String skillName = skill.getKey();
					Integer skillValue = skill.getValue();
					Integer personSkillValue = person.getSkillValue(skillName);
					if (personSkillValue.intValue() < skillValue.intValue()) {
						// FAIL
						System.out.println("required skill <" + skillName
						        + "> too low.  Expected <"
						        + skillValue.intValue() + ">, got <"
						        + personSkillValue.intValue() + ">");
						return null;
					} else {
						System.out.println("required skill <" + skillName
						        + "> is sufficient.");
					}

				}

			}

		}

		return requiredObjects;

	}

	public void addFailedDecisionStep(DecisionStep decisionStep) {
		this.failedDecisionSteps.add(decisionStep);
	}

	public Queue<DecisionStep> getFailedDecisionSteps() {
		return this.failedDecisionSteps;
	}

	public void addPassedDecisionStep(DecisionStep decisionStep) {
		this.passedDecisionSteps.add(decisionStep);
	}

	public Queue<DecisionStep> getPassedDecisionSteps() {
		return this.passedDecisionSteps;
	}

	public void addAction(Action action) {
		this.actions.put(action.getName(), action);
	}

	public Action getAction(String name) {
		return actions.get(name);
	}

	public void addRequirement(Requirement requirement) {
		this.requirements.add(requirement);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Requirement> getRequirements() {
		return this.requirements;
	}

	public void setRequirements(List<Requirement> requirements) {
		this.requirements = requirements;
	}

}