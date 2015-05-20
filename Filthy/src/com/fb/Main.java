package com.fb;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fb.actions.Action;
import com.fb.actions.ActionStore;
import com.fb.changes.ObjectAttributeChange;
import com.fb.gameobject.Attribute;
import com.fb.gameobject.GameObject;
import com.fb.gameobject.GameObjectStore;
import com.fb.object.person.Person;
import com.fb.occupations.Occupation;
import com.fb.occupations.OccupationStore;
import com.fb.occupations.behavior.BehaviorTree;
import com.fb.occupations.behavior.BehaviorTreeStore;
import com.fb.occupations.behavior.BehaviorTreeTraverser;
import com.fb.occupations.behavior.DecisionNode;
import com.fb.occupations.behavior.DecisionStep;
import com.fb.occupations.behavior.requirements.ObjectExistenceRequirement;
import com.fb.occupations.behavior.requirements.SkillCheckRequirement;

public class Main {

	
	public static int turn = 0;

	public static void main(String[] args) {

		// define actions
		Action derp = new Action("derp",8);
		ActionStore.addAction(derp);

		Action cultivateField = new Action("cultivate_field", 8);
		cultivateField.addPostActionChange(new ObjectAttributeChange("worksite",
				"status", "cultivated"));
		ActionStore.addAction(cultivateField);

		Action plantField = new Action("plant_field",  8);
		plantField.addPostActionChange(new ObjectAttributeChange("worksite", "status",
				"planted"));
		ActionStore.addAction(plantField);
		
		Action waterField = new Action("water_field", 8);
		waterField.addPostActionChange(new ObjectAttributeChange("worksite", "status",
				"watered"));
		ActionStore.addAction(waterField);
		
		

		
		// build farmer behavior tree tree
		BehaviorTree simpleFarmerTree = new BehaviorTree("simple_farmer_tree");

		
		
		DecisionNode findFieldToCultivate = new DecisionNode(
				"find_field_to_cultivate");

		// decision requirements
		ObjectExistenceRequirement req = new ObjectExistenceRequirement("field");
		req.addAttribute("status", new Attribute("status","uncultivated"));
		findFieldToCultivate.addRequirement(req);

		Map<String,Integer> skills = new HashMap<String,Integer>();
		skills.put("CULTIVATION",new Integer(1));
		SkillCheckRequirement sreq = new SkillCheckRequirement(skills);

		findFieldToCultivate.addRequirement(sreq);
		
		findFieldToCultivate.addFailedDecisionStep(new DecisionStep("ACTION",
				"derp"));
		findFieldToCultivate.addPassedDecisionStep(new DecisionStep("ACTION",
				"cultivate_field"));

		

		findFieldToCultivate.addAction(cultivateField);
		findFieldToCultivate.addAction(derp);

		simpleFarmerTree.addDecisionNode(findFieldToCultivate);

		DecisionNode findFieldToPlant = new DecisionNode("find_field_to_plant");

		req = new ObjectExistenceRequirement("field");
		req.addAttribute("status", new Attribute("status","cultivated"));
		findFieldToPlant.addRequirement(req);

		findFieldToPlant.addFailedDecisionStep(new DecisionStep("DECISION",
				"find_field_to_cultivate"));
		findFieldToPlant.addPassedDecisionStep(new DecisionStep("ACTION",
				"plant_field"));

		

		findFieldToPlant.addAction(plantField);
		findFieldToPlant.addAction(derp);

		simpleFarmerTree.addDecisionNode(findFieldToPlant);

		DecisionNode findFieldToWater = new DecisionNode("find_field_to_water");

		req = new ObjectExistenceRequirement("field");
		req.addAttribute("status", new Attribute("status","planted"));
		findFieldToWater.addRequirement(req);

		findFieldToWater.addFailedDecisionStep(new DecisionStep("DECISION",
				"find_field_to_plant"));
		findFieldToWater.addPassedDecisionStep(new DecisionStep("ACTION",
				"water_field"));



		findFieldToWater.addAction(waterField);
		findFieldToWater.addAction(derp);

		simpleFarmerTree.addDecisionNode(findFieldToWater);

		simpleFarmerTree.setParentNode("find_field_to_water");

		// decisions
		BehaviorTreeStore.addBehaviorTree(simpleFarmerTree);

		// set up occupations
		Occupation simpleFarmer = new Occupation("Simple Farmer");
		simpleFarmer.setBehaviorTree(simpleFarmerTree);
		
		OccupationStore.addOccupation(simpleFarmer);


		// set up people
		Person person = new Person("Farmer John","20");
		person.addSkill("CULTIVATION", new Integer(5));
		person.setOccupation(simpleFarmer);

		

		// create game objects
		GameObject bobsSouth40 = new GameObject("Bob's South 40");
		bobsSouth40.addAttribute("status", "uncultivated");
		bobsSouth40.setOwner(person);
		GameObjectStore.addGameObject(bobsSouth40);
		GameObjectStore.classifyGameObject(bobsSouth40, "field");
		GameObjectStore.classifyGameObject(bobsSouth40, "location");
		
		while (true) {
			turn++;

			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}

			System.out.println("TURN <" + turn + ">");

			Set<GameObject> people = GameObjectStore.getAllPeople();
			
			Iterator<GameObject> peopleIter = people.iterator();
		
			while (peopleIter.hasNext()) {
				Person currentPerson = (Person) (peopleIter.next());
				System.out.println(currentPerson);
				new BehaviorTreeTraverser(currentPerson).traverse();

			}

		}

	}

}
