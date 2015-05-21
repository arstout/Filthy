package com.fb.occupations.behavior;

import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

import com.fb.actions.Action;
import com.fb.actions.SimpleAction;
import com.fb.actions.WorkAction;
import com.fb.gameobject.GameObject;
import com.fb.gameobject.Person;
import com.fb.gameobject.Worksite;

public class BehaviorTreeTraverser {

	private Person person;
	private BehaviorTree behaviorTree;

	public BehaviorTreeTraverser(Person person) {
		this.person = person;
		this.behaviorTree = person.getOccupation().getBehaviorTree();
	}

	public void traverseNode(DecisionNode decisionNode) {

		System.out.println(person.getName()
				+ " is attempting to execute decision "
				+ decisionNode.getName() + ".");

		Map<String, GameObject> requiredObjects = decisionNode
				.checkRequirements(person);

		if (requiredObjects == null) {
			// FALSE PATH
			System.out.println("Decision <" + decisionNode.getName()
					+ "> has failed pre-requisites for <" + person.getName()
					+ ">.");
			Queue<DecisionStep> failedDesicionSteps = decisionNode
					.getFailedDecisionSteps();
			Iterator<DecisionStep> decisionStepIter = failedDesicionSteps
					.iterator();
			while (decisionStepIter.hasNext()) {
				DecisionStep currentDecisionStep = decisionStepIter.next();
				switch (currentDecisionStep.getType()) {
				case "ACTION":

					handleActionStep(decisionNode, currentDecisionStep, requiredObjects);
					
					
					break;
				case "DECISION":
					DecisionNode nextNode = behaviorTree
							.getDecisionNode(currentDecisionStep.getName());
					traverseNode(nextNode);
					break;
				}
			}

		} else {
			// TRUE PATH
			System.out.println("Decision <" + decisionNode.getName()
					+ "> has passed pre-requisites for <" + person.getName()
					+ ">.");
			Queue<DecisionStep> passedDesicionSteps = decisionNode
					.getPassedDecisionSteps();
			Iterator<DecisionStep> decisionStepIter = passedDesicionSteps
					.iterator();
			while (decisionStepIter.hasNext()) {
				DecisionStep currentDecisionStep = decisionStepIter.next();
				switch (currentDecisionStep.getType()) {
				case "ACTION":
					handleActionStep(decisionNode, currentDecisionStep, requiredObjects);
					
					break;
				case "DECISION":
					DecisionNode nextNode = behaviorTree
							.getDecisionNode(currentDecisionStep.getName());
					traverseNode(nextNode);
					break;
				}
			}
		}

	}
	
	private void handleActionStep(DecisionNode decisionNode, DecisionStep currentDecisionStep, Map<String, GameObject> requiredObjects){
		System.out.println("adding action <"
				+ currentDecisionStep.getName() + "> to queue of <"
				+ this.person.getName() + ">.");
		Action action = decisionNode.getAction(currentDecisionStep
				.getName());
		if(action instanceof WorkAction){
			WorkAction workAction = new WorkAction((WorkAction)action);
			
			Worksite worksite = (Worksite)(requiredObjects.get("worksite"));

			//add person to workers
			worksite.addWorker(person);
			
			// set turns on worksite only if it is not in use
			if(!worksite.isInUse()){
				worksite.setTurnsUntilWorkComplete(workAction.getName());
			}
			
			// adjust person work output
			person.setWorkOutputPerTurn(1);
			
			// set worksite
			workAction.setWorksite(worksite);
			
			
			
			this.person.addActionToQueue(workAction);
		} else if(action instanceof SimpleAction){
			this.person.addActionToQueue(action);
		}
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
				if(currentAction instanceof WorkAction){
					currentAction.turn(person);
				} else if(currentAction instanceof SimpleAction){
					currentAction.turn(person);
				}
				break;
			case "COMPLETED":
				currentAction.complete(person);
				break;
			}
		} else {
			System.out.println(person.getName() + " has no current action.");
			// go to parent node
			traverseNode(behaviorTree.getParentNode());
		}
		

	}
	


}
