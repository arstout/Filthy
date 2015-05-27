package com.fb.occupations.behavior;

import java.util.Map;
import java.util.Queue;

import com.fb.actions.Action;
import com.fb.actions.ActionStore;
import com.fb.gameobject.GameObject;
import com.fb.gameobject.Person;

public class BehaviorTreeTraverser {

	private Person person;
	private BehaviorTree behaviorTree;

	public BehaviorTreeTraverser(Person person) {
		this.person = person;
		this.behaviorTree = person.getOccupation().getBehaviorTree();
	}

	public void traverseNode(DecisionNode decisionNode) {

		// System.out.println("\t\t" + person.getName()
		// + " is attempting to execute decision "
		// + decisionNode.getName() + ".");

		boolean decisionPassed = decisionNode.checkRequirements(person);

		Queue<DecisionStep> decisionSteps = null;

		if (!decisionPassed) {

			// failed steps are the chosen ones
			// System.out.println("\t\t\tDecision <" + decisionNode.getName()
			// + "> has failed pre-requisites for <" + person.getName()
			// + ">.");
			decisionSteps = decisionNode.getFailedDecisionSteps();
		} else {

			// passed steps are the chosen ones
			// System.out.println("\t\tDecision <" + decisionNode.getName()
			// + "> has passed pre-requisites for <" + person.getName()
			// + ">.");
			decisionSteps = decisionNode.getPassedDecisionSteps();
		}

		// execute decision steps
		for (DecisionStep currentDecisionStep : decisionSteps) {
			switch (currentDecisionStep.getType()) {
			case "ACTION":
				handleActionStep(decisionNode, currentDecisionStep,
				        decisionNode.getRequiredObjects());
				break;
			case "DECISION":
				DecisionNode nextNode = behaviorTree
				        .getDecisionNode(currentDecisionStep.getName());
				traverseNode(nextNode);
				break;
			}
		}

	}

	private void handleActionStep(DecisionNode decisionNode,
	        DecisionStep currentDecisionStep,
	        Map<String, GameObject> requiredObjects) {

		System.out.println("\t\tAdding action <"
		        + currentDecisionStep.getName() + "> to queue of <"
		        + this.person.getName() + ">.\n");

		Action action = ActionStore.getAction(currentDecisionStep.getName());
		action.prepare(person, requiredObjects);

	}

	public void traverse() {

		// does this person have actions on their queue?
		Action currentAction = person.getCurrentAction();
		if (currentAction != null) {
			String state = currentAction.getState();
			switch (state) {
			case "QUEUED":
				// let's start this action
				currentAction.start(person);
				break;
			case "ACTIVE":

				currentAction.turn(person);
				break;
			case "COMPLETED":
				currentAction.complete(person);
				break;
			}
		} else {

			System.out.println("\t" + person.getName()
			        + " has no current action.");
			// go to parent node
			traverseNode(behaviorTree.getParentNode());
		}

	}

}
