package com.fb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fb.actions.Action;
import com.fb.changes.ObjectAttributeChange;
import com.fb.occupations.Occupation;
import com.fb.occupations.behavior.BehaviorTree;
import com.fb.occupations.behavior.BehaviorTreeTraverser;
import com.fb.occupations.behavior.DecisionNode;
import com.fb.occupations.behavior.DecisionStep;
import com.fb.occupations.behavior.requirements.ObjectExistenceRequirement;

public class Main {

	public static List<Person> people = new ArrayList<Person>();
	public static Map<String,Occupation> occupations = new HashMap<String,Occupation>();
	public static GameObjectMap gameObjects = new GameObjectMap();
	public static Map<String,BehaviorTree> behaviorTrees = new HashMap<String,BehaviorTree>();
	
	public static int turn = 0;
	
	public static void main(String[] args) {
		
		// derp action
		Action derp = new Action("derp",8);
		
		// create game objects
		GameObject obj = new GameObject("Bob's South 40");
		obj.addAttribute("status", "uncultivated");
		obj.addAttribute("scope","world");
		
		List<String> types = new ArrayList<String>();
		types.add("field");
		types.add("location");
		gameObjects.add(obj, types);
		
		
		// build behavior tree
		BehaviorTree simpleFarmerTree = new BehaviorTree("simple_farmer_tree");
		
		DecisionNode findFieldToCultivate = new DecisionNode("find_field_to_cultivate");
		
		ObjectExistenceRequirement req = new ObjectExistenceRequirement("1", "field");
		req.addAttributeCheck("status","uncultivated");
		findFieldToCultivate.addRequirement(req);
		
		findFieldToCultivate.addFailedDecisionStep(new DecisionStep("ACTION","derp"));
		findFieldToCultivate.addPassedDecisionStep(new DecisionStep("ACTION","cultivate_field"));

		Action cultivateField = new Action("cultivate_field",8);
		cultivateField.addPostActionChange(new ObjectAttributeChange("1","status","cultivated"));
		
		findFieldToCultivate.addAction(cultivateField);

		findFieldToCultivate.addAction(derp);
		
		simpleFarmerTree.addDecisionNode(findFieldToCultivate);
		
		DecisionNode findFieldToPlant = new DecisionNode("find_field_to_plant");
		
		req = new ObjectExistenceRequirement("1", "field");
		req.addAttributeCheck("status","cultivated");
		findFieldToPlant.addRequirement(req);
		
		findFieldToPlant.addFailedDecisionStep(new DecisionStep("DECISION","find_field_to_cultivate"));
		findFieldToPlant.addPassedDecisionStep(new DecisionStep("ACTION","plant_field"));
		
		Action plantField = new Action("plant_field",8);
		plantField.addPostActionChange(new ObjectAttributeChange("1","status","planted"));
		
		findFieldToPlant.addAction(plantField);

		findFieldToPlant.addAction(derp);
		
		simpleFarmerTree.addDecisionNode(findFieldToPlant);
		
		DecisionNode findFieldToWater = new DecisionNode("find_field_to_water");
		
		req = new ObjectExistenceRequirement("1", "field");
		req.addAttributeCheck("status","planted");
		findFieldToWater.addRequirement(req);
		
		
		findFieldToWater.addFailedDecisionStep(new DecisionStep("DECISION","find_field_to_plant"));
		findFieldToWater.addPassedDecisionStep(new DecisionStep("ACTION","water_field"));
		
		Action waterField = new Action("water_field",8);
		waterField.addPostActionChange(new ObjectAttributeChange("1","status","watered"));
		
		findFieldToWater.addAction(waterField);
		findFieldToWater.addAction(derp);
		
		simpleFarmerTree.addDecisionNode(findFieldToWater);
		
		simpleFarmerTree.setParentNode("find_field_to_water");
		
		// decisions 
		behaviorTrees.put(simpleFarmerTree.getName(),simpleFarmerTree);
		
		
		// set up occupations
		occupations.put("Simple Farmer",new Occupation("Farmer",behaviorTrees.get("simple_farmer_tree")));
		
		// set up people
		people.add(new Person("Farmer John",20, occupations.get("Simple Farmer")));
		
		
		
		
		
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

			}
			
		}
				
	}

}
