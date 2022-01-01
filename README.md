# Flower-Shop-Cashier
To compile: 
1. Make sure to run the sql file provided.
2. Type the following in the terminal:
     javac src/Flowers.java src/Packaging.java src/Main.java
3. Then type the following in the terminal:
     javac -cp .:lib/mysql-connector-java-8.0.23.jar src/Connect.java && java -cp .:lib/mysql-connector-java-8.0.23.jar src/Connect    

How to use:
1. Employees are first taken to the database initialization page where they enter the dburl, username and password to connect to the database. 
   <img width="395" alt="Screen Shot 2021-12-31 at 10 49 10 PM" src="https://user-images.githubusercontent.com/32075424/147844802-3f018412-fcd0-4264-89e2-166adbe7f795.png">

2. Once the connection is established, the user is taken to the cashier page.
   <img width="797" alt="Screen Shot 2021-12-31 at 10 39 18 PM" src="https://user-images.githubusercontent.com/32075424/147844663-e2a74e5c-cd92-4068-b31c-3399c8d25929.png">

3. When the employee adds the customer's items, the item information is shown at the side and the total cost can be viewed at the bottom. Employeees can also apply a discount to the total cost.
   <img width="799" alt="Screen Shot 2021-12-31 at 10 39 52 PM" src="https://user-images.githubusercontent.com/32075424/147844670-8d2bfc98-17d5-495b-949a-066da4c40f58.png">
     
4. The employee clicks on the orange purchase button once the transaction is complete.
