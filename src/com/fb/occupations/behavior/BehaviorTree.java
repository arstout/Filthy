package com.fb.occupations.behavior;

import java.util.HashMap;
import java.util.Map;

public class BehaviorTree {

	
	private String name = null;
	private Map<String,DecisionNode> decisionNodeMap = new HashMap<String,DecisionNode>();
	private String parentNode;
	
	public DecisionNode getDecisionNode(String name){
		return this.decisionNodeMap.get(name);
	}
	
	public BehaviorTree(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void addDecisionNode(DecisionNode decisionNode) {
		decisionNodeMap.put(decisionNode.getName(), decisionNode);
	}
	
	public void setParentNode(String parentNode){
		this.parentNode = parentNode;
	}

	public DecisionNode getParentNode(){
		return decisionNodeMap.get(this.parentNode);
	}
	
}
