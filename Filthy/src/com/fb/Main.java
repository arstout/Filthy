package com.fb;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.fb.actions.Action;
import com.fb.actions.ActionStore;
import com.fb.changes.ObjectAttributeChange;
import com.fb.gameobject.Attribute;
import com.fb.gameobject.GameObject;
import com.fb.gameobject.GameObjectStore;
import com.fb.gameobject.Person;
import com.fb.gameobject.Worksite;
import com.fb.occupations.Occupation;
import com.fb.occupations.OccupationStore;
import com.fb.occupations.behavior.BehaviorTree;
import com.fb.occupations.behavior.BehaviorTreeStore;
import com.fb.occupations.behavior.BehaviorTreeTraverser;
import com.fb.occupations.behavior.DecisionNode;
import com.fb.occupations.behavior.DecisionStep;
import com.fb.occupations.behavior.requirements.WorksiteRequirement;
import com.fb.occupations.behavior.requirements.SkillCheckRequirement;

public class Main {

	public static int turn = 0;

	public static void main(String[] args) {

		// define actions
		Action derp = ActionStore.createSimpleAction("derp", 1);


		Action cultivateField = ActionStore.createWorkAction("cultivate_field");
		cultivateField.addChange(new ObjectAttributeChange("worksite",
		        "status", "cultivated"));

		Action plantField = ActionStore.createWorkAction("plant_field");
		plantField.addChange(new ObjectAttributeChange("worksite", "status",
		        "planted"));

		Action waterField = ActionStore.createWorkAction("water_field");
		waterField.addChange(new ObjectAttributeChange("worksite", "status",
		        "watered"));

		// build farmer behavior tree tree
		BehaviorTree simpleFarmerTree = new BehaviorTree("simple_farmer_tree");

		DecisionNode findFieldToCultivate = new DecisionNode(
		        "find_field_to_cultivate");

		// decision requirements

		// find a worksite
		WorksiteRequirement req = new WorksiteRequirement("field");
		req.addAttribute("status", new Attribute("status", "uncultivated"));
		findFieldToCultivate.addRequirement(req);

		Map<String, Integer> skills = new HashMap<String, Integer>();
		skills.put("CULTIVATION", new Integer(1));
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

		req = new WorksiteRequirement("field");
		req.addAttribute("status", new Attribute("status", "cultivated"));
		findFieldToPlant.addRequirement(req);

		skills = new HashMap<String, Integer>();
		skills.put("PLANTING", new Integer(1));
		sreq = new SkillCheckRequirement(skills);

		findFieldToPlant.addRequirement(sreq);

		findFieldToPlant.addFailedDecisionStep(new DecisionStep("DECISION",
		        "find_field_to_cultivate"));
		findFieldToPlant.addPassedDecisionStep(new DecisionStep("ACTION",
		        "plant_field"));

		findFieldToPlant.addAction(plantField);
		findFieldToPlant.addAction(derp);

		simpleFarmerTree.addDecisionNode(findFieldToPlant);

		DecisionNode findFieldToWater = new DecisionNode("find_field_to_water");

		req = new WorksiteRequirement("field");
		req.addAttribute("status", new Attribute("status", "planted"));
		findFieldToWater.addRequirement(req);

		skills = new HashMap<String, Integer>();
		skills.put("WATERING", new Integer(1));
		sreq = new SkillCheckRequirement(skills);

		findFieldToWater.addRequirement(sreq);

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

		Person john = GameObjectStore.createPerson("Farmer John", 20);
		john.addSkill("CULTIVATION", new Integer(5));
		john.addSkill("WATERING", new Integer(5));
		john.addSkill("PLANTING", new Integer(5));
		john.setOccupation(simpleFarmer);

		// set up people
		Person sam = GameObjectStore.createPerson("Farmer Sam", 19);
		sam.addSkill("PLANTING", new Integer(5));
		sam.setOccupation(simpleFarmer);
		sam.setEmployer(john);

		// set up people
		Person joe = GameObjectStore.createPerson("Farmer Joe", 19);
		joe.addSkill("CULTIVATION", new Integer(5));
		joe.addSkill("WATERING", new Integer(5));
		joe.setOccupation(simpleFarmer);
		joe.setEmployer(john);

		john.addEmployee(joe);
		john.addEmployee(sam);

		// create game objects
		Worksite bobsSouth40 = GameObjectStore.createWorksite("Bob's South 40");
		bobsSouth40.addAttribute("status", "uncultivated");
		bobsSouth40.setOwner(john);
		bobsSouth40.setMaxWorkers(5);
		bobsSouth40.addActionDuration("cultivate_field", 20);
		bobsSouth40.addActionDuration("plant_field", 5);
		bobsSouth40.addActionDuration("water_field", 10);
		GameObjectStore.classifyGameObject(bobsSouth40, "field");

		Worksite bobsNorth40 = GameObjectStore.createWorksite("Bob's North 80");
		bobsNorth40.addAttribute("status", "uncultivated");
		bobsNorth40.setOwner(john);
		bobsNorth40.setMaxWorkers(10);
		bobsNorth40.addActionDuration("cultivate_field", 30);
		bobsNorth40.addActionDuration("plant_field", 10);
		bobsNorth40.addActionDuration("water_field", 20);
		GameObjectStore.classifyGameObject(bobsNorth40, "field");

		while (true) {
			turn++;

			try {
				Thread.sleep(100);
			} catch (Exception e) {

			}

			System.out.println("TURN <" + turn + ">");

			Set<GameObject> people = GameObjectStore.getAllPeople();

			Iterator<GameObject> peopleIter = people.iterator();

			while (peopleIter.hasNext()) {

				Person currentPerson = (Person) (peopleIter.next());
				new BehaviorTreeTraverser(currentPerson).traverse();

			}

		}

	}

}
