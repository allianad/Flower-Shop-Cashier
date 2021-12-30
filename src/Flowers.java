package src;

import java.sql.*;
import java.util.ArrayList;

public class Flowers {
    private Connection myConnect;
    
    //allFlowers contain name and colour of the flowers
    private ArrayList<String[]> allFlowers = new ArrayList<String[]>();
    
    //flowerList contains id, name, colour, price, quanity of the flowers after purchase
    private ArrayList<String[]> flowerList = new ArrayList<String[]>();

    /**
     * Flowers constructor
     * @param myConnect Connection to database
     */
    public Flowers(Connection myConnect){
        this.myConnect = myConnect;
        createAllFlowers();
    }

    /**
     * Goes through the database and stores the names and colours of all the 
     * flowers in the ArrayList<String[]> allFlowers
     */
    public void createAllFlowers(){
        try {                    
          Statement myStmt = this.myConnect.createStatement();
          ResultSet results = myStmt.executeQuery("SELECT * FROM flower");

          while (results.next()){
              //adds all names and colours of flowers in ArrayList
              String[] newFlower = {results.getString("Name"), results.getString("Colour")};
              allFlowers.add(newFlower);
            }

          myStmt.close();

        } catch (SQLException ex) {
          ex.printStackTrace();
        }   
    }

    /**
     * Checks if flower specified is in stock
     * If in stock, adds name, colour, price and quantity of flower to be 
     * purchased to flowerList
     * @param name  Name of flower to be purchased
     * @param colour Colour of flower to be purchased
     * @param quantity Quantity of flowers to be purchased
     * @return Number of specified flowers in database
     */
    public int addFlowerList(String name, String colour, int quantity){
        String[] potentialFlower = moreInfo(name, colour);
        int quantityStored = Integer.valueOf(potentialFlower[4]);

        //If there are enough flowers in the database, the information of the 
        //flower specified is stored in flowerList
        if(quantity <= quantityStored){

            //store quantity of flowers left when purchase is made
            int quantityLeft = quantityStored - quantity;
            potentialFlower[4] = Integer.toString(quantityLeft);

            flowerList.add(potentialFlower);
        }

        return quantityStored;
    }

    /**
     * Goes through the database to find the id, quantity and price of the 
     * specified flower
     * @param name  Name of flower
     * @param colour Colour of flower
     * @return String[] of all the information about the flower
     */
    public String[] moreInfo(String name, String colour){
        String[] flowerInfo = new String[5];
        flowerInfo[1] = name;
        flowerInfo[2] = colour;

        try {                    
            Statement myStmt = this.myConnect.createStatement();
            ResultSet results = myStmt.executeQuery("SELECT * FROM flower");
  
            while (results.next()){

                //get quantity of specified name and colour of flower
                if(results.getString("Name").equals(name) && results.getString("Colour").equals(colour)){
                    flowerInfo[0] = results.getString("FlowerID");
                    flowerInfo[3] = String.format("%.2f", results.getDouble("Price"));
                    flowerInfo[4] = results.getString("Quantity");
                }
            }
  
            myStmt.close();
  
          } catch (SQLException ex) {
            ex.printStackTrace();
          }

          return flowerInfo;
    }

    /**
     * Goes through the list of flowers to be purchased and returns the price of
     * the specified flower
     * @param name  Name of flower 
     * @param colour Colour of flower
     * @return Price of the specified flower
     */
    public String findPrice(String name, String colour){
        String result = "";

        //Goes through flowerList starting from the end
        for( int i = flowerList.size() - 1; i >= 0; i--){

            //Find flower by name and colour
            if(flowerList.get(i)[1].equals(name) &&
                flowerList.get(i)[2].equals(colour)){
                
                //Get price and store in result
                result = flowerList.get(i)[3];
            }
        }
        return result;
    }

    /**
     * Filters list of all flowers by chosen colour and gives a list of flower names
     * @param colour Colour of flower chosen
     * @return String[] of flower names with the chosen colour
     */
    public String[] filterByColour(String colour){
        ArrayList<String> flowerNames = new ArrayList<String>();

        //Goes through all the flowers
        for(int i = 0; i < allFlowers.size(); i++){

            //If the current flower has the right colour add the name of the 
            //flower to flowerNames
            if(allFlowers.get(i)[1].equals(colour)){
                flowerNames.add(allFlowers.get(i)[0]);
            }
        }

        //converting flowerNames to String[]
        String[] result = new String[flowerNames.size()];
        result = flowerNames.toArray(result);
        return result;
    }

    /**
     * Filters list of all flowers by name and gives a list of available
     * colours with the specified name
     * @param name Name of flower chosen
     * @return String[] of flower colours with the chosen name
     */
    public String[] filterByName(String name){
        ArrayList<String> flowerColours = new ArrayList<String>();

        //Goes through all the flowers
        for(int i = 0; i < allFlowers.size(); i++){

            //If the current flower has the right name add the colour of the 
            //flower to flowerNames
            if(allFlowers.get(i)[0].equals(name)){
                flowerColours.add(allFlowers.get(i)[1]);
            }
        }

        //converting flowerColours to String[]
        String[] result = new String[flowerColours.size()];
        result = flowerColours.toArray(result);
        return result;
    }

    /**
     * Updates quantity in database to reflect how many flowers are left when 
     * purchase is made
     */
    public void updateQuantity(){
        
        try{
            //updates the quantity using flowerID to identify the correct flower
            String query = "UPDATE flower set Quantity = ? WHERE FlowerID = ?";
            PreparedStatement myStmt = this.myConnect.prepareStatement(query);

            // goes through each flower to be purchased 
            for(int i = 0; i < flowerList.size(); i++){
                myStmt.setInt(1, Integer.valueOf(flowerList.get(i)[4]));
                myStmt.setString(2, flowerList.get(i)[0]);
                myStmt.executeUpdate();
            }
                myStmt.close();
         }

        catch (SQLException ex) {
            ex.printStackTrace();
        }    
    }


    
}
