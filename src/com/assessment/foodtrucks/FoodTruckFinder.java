package com.assessment.foodtrucks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class FoodTruckFinder {

	static FoodTrucksList foodTrucksList = new FoodTrucksList();
	final static String app_token = "UO17cUwwgwkFOZB6MJD7tZLkP";
	
	
	//Method to format the output
	private static void printRowOutlined(String left, String right) {
		System.out.printf("%-60s%15s%n", left.trim(), right.trim());
    }

	// this method prints the available food trucks at any given time
	private static void printFoodTrucks() {
		List<FoodTruck> listOfTrucks = new ArrayList<>();
		listOfTrucks = foodTrucksList.iterateList();
		if (listOfTrucks.size() == 0) {
			System.out.println("No more food trucks opened at this time!");
			System.exit(0);
		} else {
			System.out.printf("%-60s%15s%n","Name","ADDRESS");
			Iterator<FoodTruck> iter = listOfTrucks.iterator();
			while (iter.hasNext()) {
				FoodTruck truck = iter.next();
				printRowOutlined(truck.getName(), truck.getLocation());
			}

		}
	}

	public static void main(String[] args) {

		// flag to keep track if user wants to see more food trucks
		boolean showMore = true;
		Scanner sc = new Scanner(System.in);
		try {
			StringBuilder result = new StringBuilder();
			URL url = new URL("http://data.sfgov.org/resource/bbb8-hzi6.json?$$app_token=" + app_token);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();

			// get day of the week
			int todaysDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
			// since Monday = 1 in the given API and Monday = 2 in
			// java.util.Calendar
			todaysDay--;
			// get current hour
			Calendar rightNow = Calendar.getInstance();
			int hour = rightNow.get(Calendar.HOUR_OF_DAY);

			// using jsonArray to store the response from URL
			JSONArray jsonArray = new JSONArray(result.toString());
			// iterate loop
			for (int i = 0; i < jsonArray.length(); i++) {

				// get the JSON Object
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				String applicant = jsonObj.getString("applicant");
				String location = jsonObj.getString("location");
				String startTimeStr = jsonObj.getString("starttime");
				int startTime;
				if (startTimeStr.contains("AM")) {
					startTime = Integer.valueOf(startTimeStr.replaceAll("AM", ""));
				} else {
					startTime = Integer.valueOf(startTimeStr.replaceAll("PM", "")) + 12;
				}
				String endTimeStr = jsonObj.getString("endtime");
				int endTime;
				if (endTimeStr.contains("AM")) {
					endTime = Integer.valueOf(endTimeStr.replaceAll("AM", ""));
				} else {
					endTime = Integer.valueOf(endTimeStr.replaceAll("PM", "")) + 12;
				}

				int dayInt = jsonObj.getInt("dayorder");

				/*
				 * if the day of the food truck equals current day of the week
				 * and if current time is in between the start time and end time
				 * we add the food truck object to our ArrayList
				 */
				if (todaysDay == dayInt && hour >= startTime && hour <= endTime) {
					FoodTruck obj = new FoodTruck();
					obj.setName(applicant);
					obj.setLocation(location);
					obj.setDayOfWeek(dayInt);
					obj.setStartTime(startTime);
					foodTrucksList.addFoodTruck(obj);
				}
			}

			/*
			 * call the sortList method from the FoodTrucksList class to sort
			 * the ArrayList based on the name of the food truck
			 */
			foodTrucksList.sortList();

			// call the method to print the food trucks
			printFoodTrucks();

			// loop until user wants to see more food trucks
			do {
				System.out.println("\nDo you want to see more food trucks(Y/N)");
				String input = sc.nextLine();
				if (input.trim().equalsIgnoreCase("Y")) {
					printFoodTrucks();
				} else if (input.trim().equalsIgnoreCase("N")) {
					showMore = false;
					System.out.println("Application Stopped!");
				} else {
					System.out.println("\nInvalid input. Try Again..");
				}
			} while (showMore);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		sc.close();
	}

}
