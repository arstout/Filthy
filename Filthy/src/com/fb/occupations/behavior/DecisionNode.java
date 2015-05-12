package com.fb.occupations.behavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import com.fb.GameObject;
import com.fb.Main;
import com.fb.actions.Action;
import com.fb.occupations.behavior.requirements.ObjectExistenceRequirement;
import com.fb.occupations.behavior.requirements.Requirement;

public class DecisionNode {

	private String name;
	private List<Requirement> requirements = new ArrayList<Requirement>();
	private Queue<DecisionStep> passedDecisionSteps = new PriorityQueue<DecisionStep>();
	private Queue<DecisionStep> failedDecisionSteps = new PriorityQueue<DecisionStep>();
	private Map<String,Action> actions = new HashMap<String,Action>();
	
	
	public DecisionNode(String name) {
		super();
		this.name = name;
	}
	
	

	public Map<String,GameObject> checkRequirements(){ 
		
		
		// check requirements
				Iterator<Requirement> requirementsIter = this.requirements.iterator();
				
				System.out.println("There are <" + this.requirements.size() + "> requirements for this decision.");

				Map<String,GameObject> requiredObjects = new HashMap<String,GameObject>();
				while(requirementsIter.hasNext()) {
					Requirement currentRequirement = requirementsIter.next();
					if(currentRequirement instanceof ObjectExistenceRequirement) {
						System.out.println("checking for existence of an object...");
						String type = ((ObjectExistenceRequirement)currentRequirement).getType();
						Map<String,String> attributeChecks = ((ObjectExistenceRequirement)currentRequirement).getAttributeChecks();
						
						GameObject requiredObject = Main.gameObjects.findGameObjectByType(type);
						if( requiredObject == null) {
							//object not found!
							return null;
						} else {
							Set<String> keys = attributeChecks.keySet();
							Iterator<String> iter = keys.iterator();
							while(iter.hasNext()){
								String key = iter.next();
								String value = attributeChecks.get(key);
								if(!requiredObject.getAttribute(key).equals(value)){
									System.out.println("value for attribute <" + key + "> not valid.  was <" + requiredObject.getAttribute(key) + ">, expected <" + value + ">.");
									return null;
								}
							}
							System.out.println("object found!");
							requiredObjects.put(((ObjectExistenceRequirement) currentRequirement).getObjectId(),requiredObject);
						}
					}
					
					
				}
				return requiredObjects;
			
				
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
		return requirements;
	}

	public void setRequirements(List<Requirement> requirements) {
		this.requirements = requirements;
	}
	
	
	
}
