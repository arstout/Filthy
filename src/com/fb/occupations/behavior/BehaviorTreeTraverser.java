package com.fb.occupations.behavior;

import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import com.fb.Main;
import com.fb.Person;
import com.fb.actions.Action;
public class BehaviorTreeTraverser {

	
	private Person person;
	private BehaviorTree behaviorTree;
	
	public BehaviorTreeTraverser(Person person) {
		this.person = person;
		this.behaviorTree = person.getOccupation().getBehaviorTree();
	}

	public void traverseNode(DecisionNode decisionNode){
		
		System.out.println("Person <" + person.getName() + "> is attempting to execute decision <" + decisionNode.getName() + ">.");
		// check requirements
		List<Requirement> requirements = decisionNode.getRequirements();
		Iterator<Requirement> requirementsIter = requirements.iterator();
		
		System.out.println("There are <" + requirements.size() + "> requirements for this decision.");
		boolean failedRequirement = false;
		
		while(requirementsIter.hasNext() && !failedRequirement) {
			Requirement currentRequirement = requirementsIter.next();
			if( Main.gameObjects.findGameObjectByType(currentRequirement.getType()) == null) {
				//object not found!
				failedRequirement = true;
				break;
			}
			
		}
		
		if(failedRequirement) {
			// FALSE PATH
			System.out.println("Decision <" + decisionNode.getName() + "> has failed pre-requisites for <" + person.getName() + ">.");
			Queue<DecisionStep> failedDesicionSteps = decisionNode.getFailedDecisionSteps();
			Iterator<DecisionStep> decisionStepIter = failedDesicionSteps.iterator();
			while(decisionStepIter.hasNext()){
				DecisionStep currentDecisionStep = decisionStepIter.next();
				switch(currentDecisionStep.getType()) {
					case "ACTION":
						System.out.println("adding action <" + currentDecisionStep.getName() + "> to queue of <" + this.person.getName() + ">.");
						Action action = Main.actions.get(currentDecisionStep.getName());
						this.person.addActionToQueue(new Action(action));
						break;
					case "DECISION":
						DecisionNode nextNode = behaviorTree.getDecisionNode(currentDecisionStep.getName());
						traverseNode(nextNode);
						break;
				}
			}
			
		} else {
			// TRUE PATH
			System.out.println("Decision <" + decisionNode.getName() + "> has passed pre-requisites for <" + person.getName() + ">.");
			Queue<DecisionStep> passedDesicionSteps = decisionNode.getPassedDecisionSteps();
			Iterator<DecisionStep> decisionStepIter = passedDesicionSteps.iterator();
			while(decisionStepIter.hasNext()){
				DecisionStep currentDecisionStep = decisionStepIter.next();
				switch(currentDecisionStep.getType()) {
					case "ACTION":
						System.out.println("adding action <" + currentDecisionStep.getName() + "> to queue of <" + this.person.getName() + ">.");
						Action action = Main.actions.get(currentDecisionStep.getName());
						this.person.addActionToQueue(new Action(action));
						break;
					case "DECISION":
						DecisionNode nextNode = behaviorTree.getDecisionNode(currentDecisionStep.getName());
						traverseNode(nextNode);
						break;
				}
			}
		}
		
	}
	public void traverse() {

		// does this person have actions on their queue?
		Action currentAction = person.getCurrentAction();
		if(currentAction != null){
			String state = currentAction.getState();
			System.out.println(person.getName() + "'s current action <" + currentAction.getName() + "> is in a state of <" + state + ">");
			switch (state) {
			case "QUEUED":
				// let's start this action
				currentAction.start();
				break;
			case "ACTIVE":
				// dude's doing work - do nothing
				break;
			case "COMPLETED":
				// execute post-action steps
				person.removeActionFromQueue();
				break;
			}
		} else {
			System.out.println(person.getName() + " has no current action.");
			// go to parent node
			traverseNode(behaviorTree.getParentNode());
		}
		
	}
}
