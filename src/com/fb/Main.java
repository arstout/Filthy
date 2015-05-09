package com.fb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fb.actions.Action;
import com.fb.changes.Change;
import com.fb.occupations.Occupation;
import com.fb.occupations.behavior.BehaviorTree;
import com.fb.occupations.behavior.BehaviorTreeTraverser;
import com.fb.occupations.behavior.DecisionNode;
import com.fb.occupations.behavior.DecisionStep;
import com.fb.occupations.behavior.Requirement;

public class Main {

	public static List<Person> people = new ArrayList<Person>();
	public static Map<String,Occupation> occupations = new HashMap<String,Occupation>();
	public static Map<String,Action> actions = new HashMap<String,Action>();
	public static GameObjectMap gameObjects = new GameObjectMap();
	public static Map<String,BehaviorTree> behaviorTrees = new HashMap<String,BehaviorTree>();
	
	public static int turn = 0;
	
	public static void main(String[] args) {
		
		// create game objects
		gameObjects.add(new GameObject("field1","planted_field"));
		
		// build behavior tree
		BehaviorTree simpleFarmerTree = new BehaviorTree("simple_farmer_tree");
		
		DecisionNode findFieldToCultivate = new DecisionNode("find_field_to_cultivate");
		findFieldToCultivate.addRequirement(new Requirement("field","world","field_1"));
		findFieldToCultivate.addFailedDecisionStep(new DecisionStep("ACTION","derp"));
		findFieldToCultivate.addPassedDecisionStep(new DecisionStep("ACTION","cultivate_field"));
		
		simpleFarmerTree.addDecisionNode(findFieldToCultivate);
		
		DecisionNode findFieldToPlant = new DecisionNode("find_field_to_plant");
		findFieldToPlant.addRequirement(new Requirement("cultivated_field","world","field_1"));
		findFieldToPlant.addFailedDecisionStep(new DecisionStep("DECISION","find_field_to_cultivate"));
		findFieldToPlant.addPassedDecisionStep(new DecisionStep("ACTION","plant_field"));
		
		simpleFarmerTree.addDecisionNode(findFieldToPlant);
		
		DecisionNode findFieldToWater = new DecisionNode("find_field_to_water");
		findFieldToWater.addRequirement(new Requirement("planted_field","world","field_1"));
		findFieldToWater.addFailedDecisionStep(new DecisionStep("DECISION","find_field_to_plant"));
		findFieldToWater.addPassedDecisionStep(new DecisionStep("ACTION","water_field"));
		
		simpleFarmerTree.addDecisionNode(findFieldToWater);
		
		simpleFarmerTree.setParentNode("find_field_to_water");
		
		// decisions 
		behaviorTrees.put(simpleFarmerTree.getName(),simpleFarmerTree);
		
		
		// set up occupations
		occupations.put("Simple Farmer",new Occupation("Farmer",behaviorTrees.get("simple_farmer_tree")));
		
		// set up people
		people.add(new Person("Farmer John",20, occupations.get("Simple Farmer")));
		
		
		// set up actions
		Action derp = new Action("derp",8);
		actions.put(derp.getName(), derp);
		Action cultivateField = new Action("cultivate_field",8);
		actions.put(cultivateField.getName(), cultivateField);
		Action waterField = new Action("water_field",4);
		actions.put(waterField.getName(), waterField);
		Action plantField = new Action("plant_field",8);
		actions.put(plantField.getName(), plantField);
		
		
		
		while(true){ 
			turn++;
			
			try{
				Thread.sleep(1000);
			}catch(Exception e){}
			
			System.out.println("TURN <" + turn + ">");
			
			Iterator<Person> personIter = people.iterator();
			while(personIter.hasNext()) {
				Person currentPerson = personIter.next();
				System.out.println(currentPerson);
				System.out.println(gameObjects);
				new BehaviorTreeTraverser(currentPerson).traverse();
				System.out.println(currentPerson);
				System.out.println(gameObjects);

			}
			
		}
				
	}

}
