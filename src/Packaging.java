package src;

import java.sql.*;
import java.util.ArrayList;

public class Packaging {
    private Connection myConnect;

    //allPacks contain type, colour and size of the packagings
    private ArrayList<String[]> allPacks = new ArrayList<String[]>();
    
    //packList contains id, type, colour, size, price, quanity of the packagings after purchase
    private ArrayList<String[]> packList = new ArrayList<String[]>();

    /**
     * Packages constructor
     * @param myConnect Connection to database
     */
    public Packaging(Connection myConnect){
        this.myConnect = myConnect;
        createAllPacks();
    }

    /**
     * Goes through the database and stores the names, colours and sizes
     * of all the packagings in the ArrayList<String[]> allPacks
     */
    public void createAllPacks(){
        try {                    
          Statement myStmt = this.myConnect.createStatement();
          ResultSet results = myStmt.executeQuery("SELECT * FROM packaging");

          while (results.next()){
              //adds all type, colour and size of packagings in ArrayList
              String[] newPack = {results.getString("Type"), 
              results.getString("Colour"), results.getString("Size")};
              allPacks.add(newPack);
            }

          myStmt.close();

        } catch (SQLException ex) {
          ex.printStackTrace();
        }   
    }

    /**
     * Checks if packaging specified is in stock
     * If in stock, adds Type, colour, size, price and quantity of packaging to 
     * be purchased to packList
     * @param type  Type of packaging to be purchased
     * @param colour Colour of packaging to be purchased
     * @param size Size of packaging to be purchased
     * @param quantity Quantity of packaging to be purchased
     * @return Number of specified packaging in database
     */
    public int addPackList(String type, String colour, String size, int quantity){
      String[] potentialPack = moreInfo(type, colour, size);
      int quantityStored = Integer.valueOf(potentialPack[5]);

      //If there are enough packaging in the database, the information of the 
      //packaging specified is stored in packList
      if(quantity <= quantityStored){

          //store quantity of packagings left when purchase is made
          int quantityLeft = quantityStored - quantity;
          potentialPack[5] = Integer.toString(quantityLeft);

          packList.add(potentialPack);
      }

      return quantityStored;
    }

    /**
     * Goes through the database to find the id, quantity and price of the 
     * specified packing
     * @param type  Type of packing
     * @param colour Colour of packing
     * @param size Size of packing
     * @return String[] of all the information about the packaging
     */
    public String[] moreInfo(String type, String colour, String size){
      String[] packInfo = new String[6];
      packInfo[1] = type;
      packInfo[2] = colour;
      packInfo[3] = size;

      try {                    
          Statement myStmt = this.myConnect.createStatement();
          ResultSet results = myStmt.executeQuery("SELECT * FROM packaging");

          while (results.next()){

              //get quantity of specified type, colour and size of packing
              if(results.getString("Type").equals(type) && results.getString("Colour").equals(colour)
              && results.getString("Size").equals(size)){
                  packInfo[0] = results.getString("PackID");
                  packInfo[4] = String.format("%.2f", results.getDouble("Price"));
                  packInfo[5] = results.getString("Quantity");
              }
          }

          myStmt.close();

        } catch (SQLException ex) {
          ex.printStackTrace();
        }

        return packInfo;
    }

    /**
     * Goes through the list of packaging to be purchased and returns the price of
     * the specified packing
     * @param type  Type of packing 
     * @param colour Colour of packing
     * @param size Size of packing
     * @return Price of the specified packing
     */
    public String findPrice(String type, String colour, String size){
        String result = "";

        //Goes through packList starting from the end
        for( int i = packList.size() - 1; i >= 0; i--){

            //Find packing by type, colour and size
            if(packList.get(i)[1].equals(type) &&
                packList.get(i)[2].equals(colour) &&
                packList.get(i)[3].equals(size)){
                
                //Get price and store in result
                result = packList.get(i)[4];
            }
        }
        return result;
    }
    
    /**
     * Gives a list of packaging info by set type
     * @param type Type of packaging
     * @return ArrayList<String> of packagings and their info with the chosen type
     */
    public ArrayList<String[]> filterByType(String type){
      ArrayList<String[]> packByType = new ArrayList<String[]>();

      //Goes through all the packagings
      for(int i = 0; i < allPacks.size(); i++){

          //If the current packaging has the right type add packaging info to packByType
          if(allPacks.get(i)[0].equals(type)){
              packByType.add(allPacks.get(i));
          }
      }
      return packByType;
    }

    /**
     * Gives a list of packaging info by set colour
     * @param colour Colour of packaging
     * @return ArrayList<String> of packagings and their info with the chosen colour
     */
    public ArrayList<String[]> filterByColour(String colour){
      ArrayList<String[]> packByColour = new ArrayList<String[]>();

      //Goes through all the packagings
      for(int i = 0; i < allPacks.size(); i++){

          //If the current packaging has the right colour add packaging info to packByColour
          if(allPacks.get(i)[1].equals(colour)){
              packByColour.add(allPacks.get(i));
          }
      }
      return packByColour;
    }
    
    /**
     * Gives a list of packaging info by set size
     * @param size Size of packaging
     * @return ArrayList<String> of packagings and their info with the chosen size
     */
    public ArrayList<String[]> filterBySize(String size){
      ArrayList<String[]> packBySize = new ArrayList<String[]>();

      //Goes through all the packagings
      for(int i = 0; i < allPacks.size(); i++){

          //If the current packaging has the right size add packaging info to packBySize
          if(allPacks.get(i)[2].equals(size)){
              packBySize.add(allPacks.get(i));
          }
      }
      return packBySize;
    }

     /**
     * Updates quantity in database to reflect how many packagings are left when 
     * purchase is made
     */
    public void updateQuantity(){
        
      try{
          //updates the quantity using PackID to identify the correct packaging
          String query = "UPDATE packaging set Quantity = ? WHERE PackID = ?";
          PreparedStatement myStmt = this.myConnect.prepareStatement(query);

          // goes through each packaging to be purchased 
          for(int i = 0; i < packList.size(); i++){
              myStmt.setInt(1, Integer.valueOf(packList.get(i)[5]));
              myStmt.setString(2, packList.get(i)[0]);
              myStmt.executeUpdate();
          }
              myStmt.close();
       }

      catch (SQLException ex) {
          ex.printStackTrace();
      }    
  }
}
