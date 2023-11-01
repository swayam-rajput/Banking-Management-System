package menus;

import connector.DbConnection;
import java.awt.*;
import javax.swing.*;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

// import banking.Portal;

public class EmployeeDetails extends JPanel{
    String empid,name,username,phone,mail;
    private JTextField empfield, namefield, unamefield, phfield, mailfield;
    boolean isValidAcc = false;
    
    public EmployeeDetails(String employeeidentity){
        empid = employeeidentity;
        Font f = new Font("Futura Md BT", Font.PLAIN, 15);
        Color tcol = Color.white;
        Color border = new Color(124,166,178 );
        JLabel lbl = new JLabel();
        
        UIManager.put("Label.font", f);
        UIManager.put("Button.font", f);
        UIManager.put("TextField.font", f);
        UIManager.put("Label.foreground", tcol);
        UIManager.put("TextField.foreground", tcol);
        UIManager.put("TextField.caretForeground", Color.cyan);
        UIManager.put("PasswordField.caretForeground", Color.cyan);
        UIManager.put("TextField.border", BorderFactory.createEmptyBorder());
        
        lbl = new JLabel("Employee Number");
        lbl.setBounds(110, 90, 140, 25);
        add(lbl);
        
        lbl = new JLabel("Name");
        lbl.setBounds(110, 130, 50, 25);
        add(lbl);
        
        lbl = new JLabel("Username");
        lbl.setBounds(110, 170, 80, 25);
        add(lbl);
        
        lbl = new JLabel("Phone");
        lbl.setBounds(110, 210, 50, 25);
        add(lbl);
        
        lbl = new JLabel("E-mail");
        lbl.setBounds(110, 250, 120, 25);
        add(lbl);

        empfield = new JTextField();
        empfield.setBounds(290, 90, 120, 25);
        empfield.setOpaque(false);
        empfield.setEditable(false);
        add(empfield);
        
        phfield = new JTextField();
        phfield.setBounds(290, 210, 120, 25);
        phfield.setOpaque(false);
        phfield.setEditable(false);
        add(phfield);
        
        namefield = new JTextField();
        namefield.setBounds(290, 130, 120, 25);
        namefield.setOpaque(false);
        namefield.setEditable(false);
        add(namefield);
        
        unamefield = new JTextField();
        unamefield.setBounds(290, 170, 120, 25);
        unamefield.setOpaque(false);
        unamefield.setEditable(false);
        add(unamefield);
        
        mailfield = new JTextField();
        mailfield.setBounds(290, 250, 220, 25);
        mailfield.setOpaque(false);
        mailfield.setEditable(false);
        add(mailfield);
        
        setOpaque(false);
        setBackground(new Color(68,70,84));
        setBorder(BorderFactory.createTitledBorder((BorderFactory.createLineBorder(Color.white)),"Your Details",TitledBorder.CENTER,TitledBorder.TOP,f,Color.white));
        setBounds(500,150,550,440);
        setLayout(null);
        setVisible(true);
        
        try {
            getDetails();
        } 
        catch (NumberFormatException nex) {
            JOptionPane.showMessageDialog(this,"Enter Info");
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }

    }
    
    public boolean getDetails() throws Exception{    
        DbConnection.sql = "select * from emprecord where empid = '"+empid+"'";
        DbConnection.connect();
        DbConnection.ps = DbConnection.con.prepareStatement(DbConnection.sql);
        
        ResultSet rs = DbConnection.ps.executeQuery();
        if(rs.next()){
            name = rs.getString("name");
            username = rs.getString("username");
            phone = rs.getString("phno");
            mail = rs.getString("email");
        }else 
            throw new Exception("Invalid Credentials");   
              
        namefield.setText(name);
        unamefield.setText(username);
        mailfield.setText(mail);
        phfield.setText(phone);
        empfield.setText(empid);
        return true;  
    }
}
