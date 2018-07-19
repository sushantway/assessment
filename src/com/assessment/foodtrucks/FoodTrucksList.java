package com.assessment.foodtrucks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FoodTrucksList {

	//ArrayList to store the FoodTruck objects
	private ArrayList<FoodTruck> foodTruckList;
	//maximum number of food trucks to be displayed each time
	final static int maxFoodTrucks = 10;

	public FoodTrucksList() {
		foodTruckList = new ArrayList<>();
	}

	//Method to add FoodTruck objects to the ArrayList
	public void addFoodTruck(FoodTruck obj) {
		foodTruckList.add(obj);
	}

	//Method to sort the ArrayList using Comparator on the food truck name
	public void sortList() {
		Collections.sort(foodTruckList, new Comparator<FoodTruck>() {
			public int compare(FoodTruck obj1, FoodTruck obj2) {
				return obj1.getName().compareTo(obj2.getName());
			}
		});
	}

	//Method to iterate and return the FoodTruck objects in a list(max 10)
	public List<FoodTruck> iterateList() {
		List<FoodTruck> list = new ArrayList<>();
		if (foodTruckList.isEmpty()) {
			return list;
		}
		int start = 0;
		while (start != maxFoodTrucks) {
			if (foodTruckList.isEmpty()) {
				break;
			}
			list.add(foodTruckList.remove(0));
			start++;
		}
		return list;
	}

}
