package com.fb.occupations.behavior;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class DecisionNode {

	private String name;
	private List<Requirement> requirements = new ArrayList<Requirement>();
	private Queue<DecisionStep> passedDecisionSteps = new PriorityQueue<DecisionStep>();
	private Queue<DecisionStep> failedDecisionSteps = new PriorityQueue<DecisionStep>();
	
	public DecisionNode(String name) {
		super();
		this.name = name;
	}
	
	

	public void addFailedDecisionStep(DecisionStep decisionStep){
		this.failedDecisionSteps.add(decisionStep);
	}
	
	public Queue<DecisionStep> getFailedDecisionSteps(){
		return this.failedDecisionSteps;
	}
	
	public void addPassedDecisionStep(DecisionStep decisionStep){
		this.passedDecisionSteps.add(decisionStep);
	}
	
	public Queue<DecisionStep> getPassedDecisionSteps(){
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
		return requirements;
	}

	public void setRequirements(List<Requirement> requirements) {
		this.requirements = requirements;
	}
	
	
	
}
