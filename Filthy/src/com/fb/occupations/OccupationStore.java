package com.fb.occupations;

import java.util.HashMap;
import java.util.Map;

public class OccupationStore {

	private static Map<String,Occupation> occupationStore = new HashMap<String,Occupation>();

	
	public Map<String, Occupation> getActions() {
		return occupationStore;
	}

	public static void addOccupation(Occupation occupation) {
		occupationStore.put(occupation.getName(),occupation);
	}
	
	public Occupation getOccupation(String name){
		return occupationStore.get(name);
	}
	

	
}
