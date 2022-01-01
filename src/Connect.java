package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Connect extends JFrame implements ActionListener{
    private String dburl;
    private String username;
    private String password;
    private Connection myConnect;

    private JTextField userField;
    private JTextField passField;
    private JTextField dburlField;

    private JButton connectButton;

    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            new Connect().setVisible(true); 
        });
    }

    public Connect(){
        super("Establish Database Connection");
        setLayout(new BorderLayout());
        setSize(400, 400);
        setupConnectionGUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setupConnectionGUI(){
        //components to add to the panel
        JLabel userLabel = new JLabel("Username: ");
        userLabel.setPreferredSize(new Dimension(70, 15));

        JLabel passLabel = new JLabel("Password: ");
        passLabel.setPreferredSize(new Dimension(70, 15));

        JLabel dburlLabel = new JLabel("Dburl: ");
        dburlLabel.setPreferredSize(new Dimension(70, 15));

        userField = new JTextField();
        userField.setPreferredSize(new Dimension(150, 25));

        passField = new JTextField();
        passField.setPreferredSize(new Dimension(150, 25));

        dburlField = new JTextField();
        dburlField.setPreferredSize(new Dimension(150, 25));

        connectButton = new JButton("Connect");
        connectButton.setPreferredSize(new Dimension (100, 30));
        connectButton.addActionListener(this);

        //Making the panel
        JPanel connectPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 30));
        connectPanel.setBackground(Color.white);
        connectPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 50, 50));

        //Adding components to the panel
        connectPanel.add(userLabel);
        connectPanel.add(userField);
        connectPanel.add(passLabel);
        connectPanel.add(passField);
        connectPanel.add(dburlLabel);
        connectPanel.add(dburlField);
        connectPanel.add(connectButton);

        //Adding panel to the frame
        this.add(connectPanel, BorderLayout.CENTER);
    }

    /**
        *Establishes a SQL connection utilizing users input of dburl, username and password
        *The users input was obtained via the constructor of class User
        *
        *@param void
    */
    public void initializeConnection(){
        try{
            myConnect = DriverManager.getConnection(dburl, username, password);
        }

        catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error connection to database. Please make sure correct information is provided");
            System.exit(1);
        }
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == connectButton){
            username = userField.getText();
            password = passField.getText();
            dburl = dburlField.getText();

            //Establish SQL Connecction
            initializeConnection();

            //Closes connection frame
            dispose();

            //Opens main frame
            EventQueue.invokeLater(() -> {
                new Main(dburl, username, password).setVisible(true); 
            });
        }
    }
}
