# assessment
application to display food trucks

Steps to execute the application:
1. You can download the zip file from this repository and unzip it to your local drive.
2. Navigate to the folder which contains the redfin.jar file using command prompt.
3. Run the following command:
    java -jar redfin.jar
    
write-up:

If this application has to be built as a full scale web application we would have to build APIs for the CRUD operations. We can follow the MVC( Model-View-Controller) design pattern to build the web application. Controller class will help us to separate the model and view and will provide a better data flow. The model will hold the data which in my case is the FoodTruck and FoodTrucksList class and controller will correspond to the FoodTruckFinder class. Only addition will be the View component which will hold the front end code. If the number of users accessing the web application increases, we will have to make sure the server can handle the traffic. We might also have to build a database if we have to store the user specific information to improve future search results.

Also I have used ArrayList as the data structure to store the json objects received as a response. I think it will work even if we have huge data to be stored as ArrayList can increase it's size as needed. I also thought of using a priority queue, but in future if we want to iterate over the dataset without removing the objects then ArrayList is a better choice. Adding an object will cost O(log n) for a priority queue as compared to O(1) for ArrayList. This can make a huge difference if the dataset is large. Adding security features could be one of the design aspects for a web application to prevent SQL injections. If we are building an application to return the food trucks based on the user location, we can get the user coordinates and use BFS to suggest the nearest ones. To make sure all the methods perform as expected we need to design test classes to make sure we have maximum code coverage and the application is safe for deployment. 
