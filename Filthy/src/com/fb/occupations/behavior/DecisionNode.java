package com.fb.occupations.behavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.fb.gameobject.GameObject;
import com.fb.gameobject.Person;
import com.fb.occupations.behavior.requirements.Requirement;

public class DecisionNode {

	/* Name of the Decision Node */
	private String name;

	/* Array list of the requirements */
	private List<Requirement> requirements = new ArrayList<Requirement>();

	/* Steps that will be processed if Node returns true */
	private Queue<DecisionStep> passedDecisionSteps = new PriorityQueue<DecisionStep>();

	/* Steps that will be processed if Node returns false */
	private Queue<DecisionStep> failedDecisionSteps = new PriorityQueue<DecisionStep>();

	/* Required objects for this decision */
	private Map<String, GameObject> requiredObjects = new HashMap<String, GameObject>();

	public DecisionNode(String name) {
		super();
		this.name = name;
	}

	public Map<String, GameObject> getRequiredObjects() {
		return requiredObjects;
	}

	public boolean checkRequirements(Person person) {

		// check requirements
		Iterator<Requirement> requirementsIter = this.requirements.iterator();

		while (requirementsIter.hasNext()) {
			Requirement currentRequirement = requirementsIter.next();

			// TODO: Verify this with additional scope (personal, communal,
			// global)

			if (!currentRequirement.check(person, this.requiredObjects)) {
				// requirement not met
				return false;

			}

		}

		// all requirements met
		return true;

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
