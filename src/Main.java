package src;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.*;

import java.awt.event.*;
import java.awt.*;

import javax.swing.table.DefaultTableModel;

import java.sql.*;
import java.util.*;


public class Main extends JFrame implements ActionListener{

    private String flowerChoice;
    private String flowColourChoice;
    private int flowQuantity;
    private String packChoice;
    private String packColourChoice;
    private String packSizeChoice;
    private int packQuantity;
    private int discount;

    private JLabel totalLabel;

    private JComboBox<String> flowerCombo;
    private JComboBox<String> flowColourCombo;
    private JComboBox<String> packagingCombo;
    private JComboBox<String> packColourCombo;
    private JComboBox<String> packSizeCombo;

    private JSpinner flowSpinner;
    private JSpinner packSpinner;
    private JSpinner discountSpinner;

    private JButton addFlowButton;
    private JButton addPackButton;
    private JButton purchaseButton;

    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;

    private Object[][] itemsInfo;
    private String[] columnNames = {"Item", "Colour", "Size", "Quantity", "Price"};

    //Below are lists of all the available options for flowers and packages
    String[] flowerList = {"Carnation", "Chrysanthemum", "Daisy", "Gerbera", "Gypsophila", "Hydrangea", "Lily", "Orchid", "Rose",  "Sunflower", "Tulip"};
    String[] flowColourList = {"Blue", "Orange", "Pink", "Purple", "Red", "Yellow", "White",};
    String[] packagingList = {"Basket", "Circular Vase", "Cylinder Vase", "Rectangular Vase", };
    String[] packColourList = {"Brown", "Clear"};
    String[] packSizeList = {"Small", "Medium", "Large"};

    //booleans to know if a field has been selected
    boolean fName = false;
    boolean fColour = false;

    boolean pType = false;
    boolean pColour = false;
    boolean pSize = false;

    Connection myConnect;
    Flowers flowers;
    Packaging packaging;

    public void initializeConnection(String dburl, String username, String pass){
        try{
            myConnect = DriverManager.getConnection(dburl, username, pass);
        }

        catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error connection to database. Please make sure correct information is provided");
            System.exit(1);
        }
    }

    public Main(String dburl, String username, String pass){
        super("Flower Shop Cashier");
        initializeConnection(dburl, username, pass);
        flowers = new Flowers(myConnect);
        packaging = new Packaging(myConnect);
        setLayout(new BorderLayout());
        setSize(800, 700);
        setupMainGUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setupMainGUI(){
        //Header Panel Components
        ImageIcon title = new ImageIcon("assets/title.png");
        JLabel icon = new JLabel();
        icon.setIcon(title);
        icon.setPreferredSize(new Dimension(800, 60));
        icon.setHorizontalAlignment(JLabel.CENTER);

        JLabel introLabel = new JLabel("Add items to purchase.");
        introLabel.setPreferredSize(new Dimension(800, 20));
        introLabel.setVerticalAlignment(JLabel.CENTER);
        introLabel.setHorizontalAlignment(JLabel.CENTER);

        //Add Panel Components
        //Adding Flower 
        JLabel flowerLabel = new JLabel("FLOWERS");
        flowerLabel.setFont(new Font("Arial", 50, 16));
        flowerLabel.setPreferredSize(new Dimension(250, 30));

        JLabel flowTypeLabel = new JLabel("Name: ");
        flowTypeLabel.setPreferredSize(new Dimension(65, 25));


        flowerCombo = new JComboBox<>(flowerList);
        flowerCombo.setSelectedIndex(-1);
        flowerCombo.addActionListener(this);
        flowerCombo.setPreferredSize(new Dimension(160, 25));

        JLabel flowColourLabel = new JLabel("Colour: ");
        flowColourLabel.setPreferredSize(new Dimension(65, 25));


        flowColourCombo = new JComboBox<>(flowColourList);
        flowColourCombo.setSelectedIndex(-1);
        flowColourCombo.addActionListener(this);
        flowColourCombo.setPreferredSize(new Dimension(160, 25));

        JLabel flowQuantityLabel = new JLabel("Quantity: ");
        flowQuantityLabel.setPreferredSize(new Dimension(65, 25));

        flowSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        flowSpinner.setPreferredSize(new Dimension(160, 25));

        addFlowButton = new JButton("+");
        addFlowButton.setBackground(new Color(120, 196, 140));
        addFlowButton.setForeground(Color.white);
        addFlowButton.setOpaque(true);
        addFlowButton.setBorderPainted(false);
        addFlowButton.setPreferredSize(new Dimension(250, 25));
        addFlowButton.addActionListener(this);
        

        //Adding Package
        JLabel packageLabel = new JLabel("PACKAGINGS");
        packageLabel.setFont(new Font("Arial", 50, 16));
        packageLabel.setPreferredSize(new Dimension(250, 30));

        JLabel packTypeLabel = new JLabel("Type: ");
        packTypeLabel.setPreferredSize(new Dimension(65, 25));


        packagingCombo = new JComboBox<>(packagingList);
        packagingCombo.setSelectedIndex(-1);
        packagingCombo.addActionListener(this);
        packagingCombo.setPreferredSize(new Dimension(160, 25));

        JLabel packColourLabel = new JLabel("Colour: ");
        packColourLabel.setPreferredSize(new Dimension(65, 25));


        packColourCombo = new JComboBox<>(packColourList);
        packColourCombo.setSelectedIndex(-1);
        packColourCombo.setPreferredSize(new Dimension(160, 25));
        packColourCombo.addActionListener(this);

        JLabel packSizeLabel = new JLabel("Size: ");
        packSizeLabel.setPreferredSize(new Dimension(65, 25));


        packSizeCombo = new JComboBox<>(packSizeList);
        packSizeCombo.setSelectedIndex(-1);
        packSizeCombo.setPreferredSize(new Dimension(160, 25));
        packSizeCombo.addActionListener(this);

        JLabel packQuantityLabel = new JLabel("Quantity: ");
        packQuantityLabel.setPreferredSize(new Dimension(65, 25));

        packSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        packSpinner.setPreferredSize(new Dimension(160, 25));

        addPackButton = new JButton("+");
        addPackButton.setBackground(new Color(120, 196, 140));
        addPackButton.setForeground(Color.white);
        addPackButton.setOpaque(true);
        addPackButton.setBorderPainted(false);
        addPackButton.setPreferredSize(new Dimension(250, 25));
        addPackButton.addActionListener(this);

        //footer components
        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setHorizontalAlignment(JLabel.CENTER);
        totalLabel.setPreferredSize(new Dimension(350, 70));

        JLabel discountLabel = new JLabel("Discount(%):");
        discountLabel.setHorizontalAlignment(JLabel.CENTER);

        discountSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 5));
        discountSpinner.setPreferredSize(new Dimension(70, 25));

        purchaseButton = new JButton("Purchase");
        purchaseButton.setBackground(new Color(244, 151, 90));
        purchaseButton.setForeground(Color.white);
        purchaseButton.setOpaque(true);
        purchaseButton.setBorderPainted(false);
        purchaseButton.setPreferredSize(new Dimension(150, 50));
        purchaseButton.addActionListener(this);
        

        //table
        model = new DefaultTableModel(itemsInfo, columnNames);
        table = new JTable(model);
        table.setEnabled(false);
        scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        table.setFillsViewportHeight(true);

        //creating panels
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        headerPanel.setBackground(new Color(255,242,244));
        headerPanel.setPreferredSize(new Dimension(100, 120));
        
        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 30, 20));
        addPanel.setBackground(Color.white);
        addPanel.setPreferredSize(new Dimension(100, 100));

        JPanel flowerPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 10));
        flowerPanel.setBackground(Color.white);
        flowerPanel.setPreferredSize(new Dimension(250, 190));

        JPanel packagePanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 10));
        packagePanel.setBackground(Color.white);
        packagePanel.setPreferredSize(new Dimension(250, 220));

        JPanel listPanel = new JPanel();
        listPanel.setBackground(new Color(255,242,244));
        listPanel.setPreferredSize(new Dimension(300, 100));

        JPanel purchasePanel = new JPanel();
        purchasePanel.setBackground(new Color(255,242,244));
        purchasePanel.setPreferredSize(new Dimension(100, 80));

        //adding labels, input box and button to panels
        headerPanel.add(icon);
        headerPanel.add(introLabel);

        flowerPanel.add(flowerLabel);
        flowerPanel.add(flowTypeLabel);
        flowerPanel.add(flowerCombo);
        flowerPanel.add(flowColourLabel);
        flowerPanel.add(flowColourCombo);
        flowerPanel.add(flowQuantityLabel);
        flowerPanel.add(flowSpinner);
        flowerPanel.add(addFlowButton);

        packagePanel.add(packageLabel);
        packagePanel.add(packTypeLabel);
        packagePanel.add(packagingCombo);
        packagePanel.add(packColourLabel);
        packagePanel.add(packColourCombo);
        packagePanel.add(packSizeLabel);
        packagePanel.add(packSizeCombo);
        packagePanel.add(packQuantityLabel);
        packagePanel.add(packSpinner);
        packagePanel.add(addPackButton);

        purchasePanel.add(discountLabel);
        purchasePanel.add(discountSpinner);
        purchasePanel.add(totalLabel);
        purchasePanel.add(purchaseButton);

        //adding panel to panel
        addPanel.add(flowerPanel);
        addPanel.add(packagePanel);

        add(headerPanel, BorderLayout.NORTH);
        add(addPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.EAST);
        add(purchasePanel, BorderLayout.SOUTH);
    }


    public void actionPerformed(ActionEvent e){
        discount = (int) discountSpinner.getValue();
        flowerChoice = (String) flowerCombo.getSelectedItem();
        flowColourChoice = (String) flowColourCombo.getSelectedItem();
        flowQuantity = (int) flowSpinner.getValue();

        packChoice = (String) packagingCombo.getSelectedItem();
        packColourChoice = (String) packColourCombo.getSelectedItem();
        packSizeChoice = (String) packSizeCombo.getSelectedItem();
        packQuantity = (int) packSpinner.getValue();

        //------- When Add Buttons are Clicked On --------

        if(e.getSource() == addFlowButton){
            //Shows error message if no flower name or colour is chosen
            if (flowerCombo.getSelectedIndex() == -1 || flowColourCombo.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            }
            else{
                String quantity = Integer.toString(flowQuantity); //quantity of flowers to buy
                int quantityStored = flowers.addFlowerList(flowerChoice, flowColourChoice, flowQuantity);

                //Checks if there are enough flowers
                if(quantityStored >= Integer.valueOf(quantity)){
                    //get price of flowers
                    String price = flowers.findPrice(flowerChoice, flowColourChoice);

                    //add to list of purchases on the window
                    String[] newItem = {flowerChoice , flowColourChoice, "-", quantity, price};
                    model.addRow(newItem);

                    //update total price
                    totalLabel.setText("Total: $" + String.format("%.2f", calculateTotal()));

                    //reset JComboBoxes for flower
                    flowerCombo.setModel(new DefaultComboBoxModel<String>(flowerList));
                    flowerCombo.setSelectedIndex(-1);

                    flowColourCombo.setModel(new DefaultComboBoxModel<String>(flowColourList));
                    flowColourCombo.setSelectedIndex(-1);

                    fName = false;
                    fColour = false;
                }

                //If there are not enough packaging lets user know with JOptionPane
                else{
                    JOptionPane.showMessageDialog(this, quantityStored + " flowers of this kind are left.");
                }
            }

        }

        if(e.getSource() == addPackButton){
            //Shows error message if no packaging type, colour or size is chosen
            if (packagingCombo.getSelectedIndex() == -1 || packColourCombo.getSelectedIndex() == -1
            || packSizeCombo.getSelectedIndex() == -1){
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            }

            else{
                String quantity = Integer.toString(packQuantity);//quantity of packaging to buy
                int quantityStored = packaging.addPackList(packChoice, packColourChoice, packSizeChoice, packQuantity);

                //Checks if there are enough packaging
                if(quantityStored >= Integer.valueOf(quantity)){
                    //get price of packagings
                    String price = packaging.findPrice(packChoice, packColourChoice, packSizeChoice);

                    //add to list of purchases on the window
                    String[] newItem = {packChoice , packColourChoice, packSizeChoice, quantity, price};
                    model.addRow(newItem);

                    //update total prices
                    totalLabel.setText("Total: $" + String.format("%.2f", calculateTotal()));

                    //reset JComboBoxes for packaging
                    packagingCombo.setModel(new DefaultComboBoxModel<String>(packagingList));
                    packagingCombo.setSelectedIndex(-1);

                    packColourCombo.setModel(new DefaultComboBoxModel<String>(packColourList));
                    packColourCombo.setSelectedIndex(-1);

                    packSizeCombo.setModel(new DefaultComboBoxModel<String>(packSizeList));
                    packSizeCombo.setSelectedIndex(-1);

                    pType = false;
                    pColour = false;
                    pSize = false;

                }

                //If there are not enough packaging lets user know with JOptionPane
                else{
                    JOptionPane.showMessageDialog(this, quantityStored + " packaging of this kind are left.");
                }
            }
        }

         //------- When ComboBoxes Are Selected --------

        if(e.getSource().equals(flowerCombo) && !fColour){
            fName = true;

            //Set String[] filled with colours based on set name
            String[] containsName = flowers.filterByName(flowerChoice);

            //Set model for flowerColourCombo with flower colours based on the set name
            ComboBoxModel<String> flowColourModel = new DefaultComboBoxModel<>(containsName);
            flowColourCombo.setModel(flowColourModel);
        }

        if(e.getSource().equals(flowColourCombo) && !fName){
            fColour = true;

            //Set String[] filled with names based on set colour
            String[] containsColour = flowers.filterByColour(flowColourChoice);

            //Set model for flowerCombo with flower names based on the set colour
            ComboBoxModel<String> flowModel = new DefaultComboBoxModel<>(containsColour);
            flowerCombo.setModel(flowModel);
        }

        if(e.getSource().equals(packagingCombo) && packagingCombo.getSelectedItem() != null){
            pType = true;

            //containsType gets filled with packagings and their info based on set type
            ArrayList<String[]> containsType = packaging.filterByType(packChoice);

            String[] coloursByType = extractInfo(containsType, 1);
            String[] sizesByType = extractInfo(containsType, 2);

            //If colour field was previously filled in modify size model
            //And there is one empty field left
            if(pColour && !pSize){
                twoSelected(sizesByType, 1, 2);
            }
            //If size field was previously filled in modify colour model
            //And there is one empty field left
            else if(pSize && !pColour){
                twoSelected(coloursByType, 2, 1);
            }
            //If there are two empty fields, modify both colour and size model
            else if(!pSize && !pColour){
                modifyModel(coloursByType, 1);
                modifyModel(sizesByType, 2);

                packColourCombo.setSelectedIndex(-1);
                packSizeCombo.setSelectedIndex(-1);
            }
        }  
        
        if(e.getSource().equals(packColourCombo) && packColourCombo.getSelectedItem() != null){
            pColour = true;

            //containsColour gets filled with packagings and their info based on set colour
            ArrayList<String[]> containsColour = packaging.filterByColour(packColourChoice);

            String[] typesByColour = extractInfo(containsColour, 0);
            String[] sizesByColour = extractInfo(containsColour, 2);
            
            //If type field was previously filled in, modify size model
            //And there is one empty field left
            if(pType && !pSize){
                twoSelected(sizesByColour, 0, 2);
            }
            //If size field was previously filled in, modify type model
            //And there is one empty field left
            else if(pSize && !pType){
                twoSelected(typesByColour, 2, 0);
            }
            //If there are two empty fields, modify both colour and size model
            else if (!pSize && !pType){
                modifyModel(typesByColour, 0);
                modifyModel(sizesByColour, 2);

                packagingCombo.setSelectedIndex(-1);
                packSizeCombo.setSelectedIndex(-1);
            }
        }  

        if(e.getSource().equals(packSizeCombo) && packSizeCombo.getSelectedItem() != null){
            pSize = true;

            //containsSize gets filled with packagings and their info based on set size
            ArrayList<String[]> containsSize = packaging.filterBySize(packSizeChoice);

            String[] typeBySize = extractInfo(containsSize, 0);
            String[] coloursBySize = extractInfo(containsSize, 1);

            //If colour field was previously filled in modify type model
            //And there is one empty field left
            if(pColour && !pType){
                twoSelected(typeBySize, 1, 0);
            }
            //If type field was previously filled in modify colour model
            //And there is one empty field left
            else if(pType && !pColour){
                twoSelected(coloursBySize, 0, 1);
            }
            //If there are two empty fields, modify both colour and size model
            else if (!pType && !pColour){
                modifyModel(typeBySize, 0);
                modifyModel(coloursBySize, 1);

                packagingCombo.setSelectedIndex(-1);
                packColourCombo.setSelectedIndex(-1);
            }
        } 

         //------- When Purchase Button is Clicked On --------
        
        if(e.getSource().equals(purchaseButton)){
            //clear table of items being purchased
            model.setRowCount(0);

            //set total to $0
            totalLabel.setText("Total: $0.00");

            //update quantity in the database
            flowers.updateQuantity();
            packaging.updateQuantity();

            JOptionPane.showMessageDialog(this, "Thank you for your purchase. Have a nice day!");

        }
    }
    
    /**
     * Calculates the total price of items to be purchased
     * @return Price of items to be purchased
     */
    public double calculateTotal(){
        double total = 0;
        int items = model.getRowCount();

        //Goes through each item
        for(int i = 0; i < items; i++){

            //Calculates price
            int quantity = Integer.parseInt((String)model.getValueAt(i, 3));
            double price = Double.parseDouble((String)model.getValueAt(i, 4));
            total += quantity * price;
        }

        //Rounds total to 2 decimal places
        total = Double.parseDouble(String.format("%.2f", total));
        return total;
    }



    /**
     * Goes through two String[] to find Strings that appear in both 
     * @param arr1  First String[] to search for match
     * @param arr2  Second String[] to search for match
     * @return String[] with the duplicates
     */
    public String[] findDuplicates(String[] arr1, String[] arr2){
        ArrayList<String> duplicates = new ArrayList<>();
        
        //Goes through arr1
        for(int i = 0; i < arr1.length; i++){
            //Goes through arr2
            for(int j = 0; j < arr2.length; j++){
                //if there is a match between the two Strings then add to duplicates
                if(arr1[i].equals(arr2[j])){
                    duplicates.add(arr1[i]);
                }
            }
        }

        //Converting duplicates to String[]
        String[] result = new String[duplicates.size()];
        result = duplicates.toArray(result);
        return result;
    }

    /**
     * Modifies the models for various packaging combo box 
     * @param info String[] contains list of options for model
     * @param n n=0 for types, n=1 for colour, n=2 for sizes
     */
    public void modifyModel(String[] info, int n){
        //remove duplicates in info
        ArrayList<String> temp = new ArrayList<String>(Arrays.asList(info));

        for(int i = 0; i < temp.size(); i++){
            for(int j = i + 1; j < temp.size(); j++){

                if(temp.get(i).equals(temp.get(j))){
                    temp.remove(j);
                    j--;
                }
            }
        }
        
        //converting temp to String[]
        String[] finalInfo = new String[temp.size()];
        finalInfo = temp.toArray(finalInfo);

        if(n == 0){
            //Set model for packingCombo
            ComboBoxModel<String> packModel = new DefaultComboBoxModel<>(finalInfo);
            packagingCombo.setModel(packModel);
        }
        else if(n == 1){
            //Set model for packColourCombo
            ComboBoxModel<String> packColourModel = new DefaultComboBoxModel<>(finalInfo);
            packColourCombo.setModel(packColourModel);
        }
        else{
            //Set model for packSizeCombo
            ComboBoxModel<String> packSizeModel = new DefaultComboBoxModel<>(finalInfo);
            packSizeCombo.setModel(packSizeModel);
        }
    }

    /**
     * Extracts a list of wanted info about the given packagings
     * @param allInfo ArrayList<String[]> that contains info about valid packagings 
     * @param n n=0 for types, n=1 for colour, n=2 for sizes
     * @return String[] of either packaging types, colour or sizes 
     */
    public String[] extractInfo(ArrayList<String[]> allInfo, int n){
        String[] info = new String[allInfo.size()];

        for(int i = 0; i < allInfo.size(); i++){
            info[i] = allInfo.get(i)[n];
        }

        return info;
    }

    /**
     * Modifies the third final packaging field
     * @param arr1 String[] filled with potential options to put in the model 
     * based on second chosen field
     * @param firstField int value indicating which field was chosen first
     * 0 is type, 1 is colour, 2 is size
     * @param secondField int value indicating which field was chosen second
     * 0 is type, 1 is colour, 2 is size
     */
    public void twoSelected(String[]arr1, int firstField, int emptyField){
        //containsSetField gets filled with packagings and their info based on first set field
        ArrayList<String[]> containsSetField = new ArrayList<>();

        if(firstField == 0){
            //Collections.copy(containsSetField, packaging.filterByType(packChoice));
            containsSetField = packaging.filterByType(packChoice);
        }
        else if(firstField == 1){
            //Collections.copy(containsSetField, packaging.filterByColour(packColourChoice));
            containsSetField = packaging.filterByColour(packColourChoice);
        }
        else{
            containsSetField = packaging.filterBySize(packSizeChoice);
           
            //System.out.println(temp.size() + "and" + containsSetField.size());
            //Collections.copy(containsSetField, temp);
        }
        
        //arr2 gets filled with potential options to put in the model based on first chosen field
        String[] arr2 = extractInfo(containsSetField, emptyField);

        //validInfo gets filled with final options for empty field
        String[] validInfo = findDuplicates(arr1, arr2);

        modifyModel(validInfo, emptyField);
    }

}