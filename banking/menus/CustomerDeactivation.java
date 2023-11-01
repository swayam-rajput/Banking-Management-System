package menus;

import connector.DbConnection;
import java.awt.*;
import javax.swing.*;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicButtonUI;

public class CustomerDeactivation extends JPanel implements ActionListener{
    private JButton clrbtn, updtbtn;
    private JTextField accfield, namefield, balancefield, accTypefield, accCreationDatefield, phfield;
    
    boolean isValidAcc = false;
    Color border = new Color(124,166,178 );

    public CustomerDeactivation(){
        // replace the font face with the font of your own choice
        Font f = new Font("Futura Md BT", Font.PLAIN, 15);
        Color tcol = Color.white;
        JLabel lbl = new JLabel();

        UIManager.put("Label.font", f);
        UIManager.put("Button.font", f);
        UIManager.put("TextField.font", f);
        UIManager.put("Label.foreground", tcol);
        UIManager.put("TextField.foreground", tcol);
        UIManager.put("TextField.caretForeground", Color.cyan);
        UIManager.put("PasswordField.caretForeground", Color.cyan);
        UIManager.put("TextField.border", BorderFactory.createLineBorder(border));
        
        lbl = new JLabel("Account Number");
        lbl.setBounds(150, 90, 120, 25);
        add(lbl);
        
        lbl = new JLabel("Phone");
        lbl.setBounds(220, 130, 50, 25);
        add(lbl);
        
        lbl = new JLabel("Name");
        lbl.setBounds(220, 170, 50, 25);
        add(lbl);
        
        lbl = new JLabel("Account Type");
        lbl.setBounds(170, 210, 120, 25);
        add(lbl);
        
        lbl = new JLabel("Balance");
        lbl.setBounds(205, 250, 60, 25);
        add(lbl);
        
        lbl = new JLabel("Date of Account Creation");
        lbl.setBounds(85, 290, 180, 25);
        add(lbl);

        accfield = new JTextField();
        accfield.setBounds(290, 90, 120, 25);
        accfield.setOpaque(false);
        add(accfield);

        phfield = new JTextField();
        phfield.setBounds(290, 130, 120, 25);
        phfield.setOpaque(false);
        add(phfield);

        namefield = new JTextField();
        namefield.setBounds(290, 170, 120, 25);
        namefield.setOpaque(false);
        namefield.setBorder(null);
        namefield.setEditable(false);
        add(namefield);

        accTypefield = new JTextField();
        accTypefield.setBounds(290, 210, 120, 25);
        accTypefield.setOpaque(false);
        accTypefield.setBorder(null);
        accTypefield.setEditable(false);
        add(accTypefield);
        
        balancefield = new JTextField();
        balancefield.setBounds(290, 250, 120, 25);
        balancefield.setOpaque(false);
        balancefield.setEditable(false);
        balancefield.setBorder(null);
        add(balancefield);

        accCreationDatefield = new JTextField();
        accCreationDatefield.setBounds(290, 290, 160, 25);
        accCreationDatefield.setOpaque(false);
        accCreationDatefield.setEditable(false);
        accCreationDatefield.setBorder(null);
        add(accCreationDatefield);
        
        clrbtn = new JButton("Clear");
        clrbtn.setOpaque(false);
        clrbtn.setForeground(tcol);
        clrbtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clrbtn.setUI(new BasicButtonUI());
        clrbtn.setBounds(280,360,80,25);
        clrbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                updtbtn.setText("Search");
                phfield.setText("");           
                accfield.setText("");
                namefield.setText("");
                balancefield.setText("");
                accTypefield.setText("");
                accCreationDatefield.setText("");
                phfield.setEditable(true);    
                accfield.setEditable(true);
                namefield.setEditable(false);    
                balancefield.setEditable(false);
                namefield.setBorder(null);    
                balancefield.setBorder(null);
                
                accfield.setBorder(BorderFactory.createLineBorder(border));
                phfield.setBorder(BorderFactory.createLineBorder(border));    
            }
        });
        add(clrbtn);
        
        updtbtn = new JButton("Search");
        updtbtn.setOpaque(false);
        updtbtn.setForeground(tcol);
        updtbtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        updtbtn.setUI(new BasicButtonUI());
        updtbtn.setBounds(180,360,90,25);
        updtbtn.addActionListener(this);
        add(updtbtn);

        setOpaque(false);
        setBackground(new Color(68,70,84));
        setBorder(BorderFactory.createTitledBorder((BorderFactory.createLineBorder(Color.white)),"Deactivate Customer Account",TitledBorder.CENTER,TitledBorder.TOP,f,Color.white));
        setBounds(500,150,550,440);
        setLayout(null);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        try {
            double amount = Double.parseDouble(balancefield.getText());
            String acc = "A"+Integer.parseInt(accfield.getText().trim());
            Long phone = Long.parseLong(phfield.getText().trim());
            String phno = "",cname="",balance="",accdb="",doc,acctype="";
            if(e.getActionCommand().equals("Search")){
                
                DbConnection.sql = "select *,DATE_FORMAT(doc,'%d-%m-%Y, %H:%i') as cd from customers where accno = '"+acc+"' and phno = "+phone;
                DbConnection.connect();
                DbConnection.ps = DbConnection.con.prepareStatement(DbConnection.sql);
                ResultSet rs = DbConnection.ps.executeQuery();
                
                if(rs.next()){
                    phno = Long.toString(rs.getLong("phno"));
                    cname = rs.getString("cname");
                    accdb = rs.getString("accno");
                    balance = String.valueOf(rs.getDouble("balance"));
                    doc = rs.getString("cd");
                    acctype = rs.getString("acctype");
                }else 
                throw new Exception("Invalid Credentials");   
                
                updtbtn.setText("Deactivate");
                accfield.setEditable(false);    
                phfield.setEditable(false);    
                accfield.setBorder(null);
                phfield.setBorder(null);    
                balancefield.setText(balance);
                namefield.setText(cname);
                phfield.setText(phno);
                accTypefield.setText(acctype);
                accCreationDatefield.setText(doc);    
            }else if(e.getActionCommand().equals("Deactivate")){
                if(amount != 0.0){
                    JOptionPane.showMessageDialog(this,"Your account still has "+amount+" Rs");
                    clrbtn.doClick();
                    updtbtn.setText("Search");
                }else{
                    updtbtn.setText("Confirm");
                }
            }else if(e.getActionCommand().equals("Confirm")){
                if(JOptionPane.showConfirmDialog(this, "Are you sure?","Confirm Deactivation",JOptionPane.CANCEL_OPTION)!=2){
                    DbConnection.sql = "delete from customers where accno = '"+acc+"'";
                    DbConnection.connect();
                    DbConnection.ps = DbConnection.con.prepareStatement(DbConnection.sql);
                    DbConnection.ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Deactivated record successfully");
                }
                clrbtn.doClick();
            }    
        } 
        catch (NumberFormatException nex) {
            JOptionPane.showMessageDialog(this,"Enter Info\n"+nex);
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }

    }
}
