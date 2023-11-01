package menus;

import connector.DbConnection;
import java.awt.*;
import javax.swing.*;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicButtonUI;

public class CustomerUpdate extends JPanel implements ActionListener{
    private JTextField accfield, namefield, balancefield, accTypefield,accCreationDatefield, phfield;
    private JButton clrbtn, updtbtn;
    
    boolean isValidAcc = false;
    Color border = new Color(124,166,178 );

    public CustomerUpdate(){
        Font f = new Font("Segoe UI", Font.PLAIN, 15);
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
        setBorder(BorderFactory.createTitledBorder((BorderFactory.createLineBorder(Color.white)),"Update Customer Information",TitledBorder.CENTER,TitledBorder.TOP,f,Color.white));
        setBounds(500,150,550,440);
        setLayout(null);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        try {
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
                    // doc += ", "+rs.getDate("doc").toString();
                    acctype = rs.getString("acctype");
                }else 
                throw new Exception("Invalid Credentials");   
                
                updtbtn.setText("Update");
                accfield.setEditable(false);    
                phfield.setEditable(false);    
                accfield.setBorder(null);
                phfield.setBorder(null);    
                balancefield.setText(balance);
                namefield.setText(cname);
                phfield.setText(phno);
                // accfield.setText(accdb);
                accTypefield.setText(acctype);
                accCreationDatefield.setText(doc);    

            }else if(e.getActionCommand().equals("Update")){
                
                phfield.setBorder(BorderFactory.createLineBorder(border));    
                namefield.setBorder(BorderFactory.createLineBorder(border));    
                balancefield.setBorder(BorderFactory.createLineBorder(border));    
                phfield.setEditable(true);    
                namefield.setEditable(true);    
                balancefield.setEditable(true);
                updtbtn.setText("Save");

            }else if(e.getActionCommand().equals("Save")){
                if(JOptionPane.showConfirmDialog(this, "Are you sure?","Confirm Updation",JOptionPane.CANCEL_OPTION)!=2){
                    DbConnection.sql = "update customers set cname = '"+namefield.getText().trim()+"', phno = "+phone+""+", balance = "+Double.parseDouble(balancefield.getText())+""+" where accno = '"+acc+"'";
                    DbConnection.connect();
                    DbConnection.ps = DbConnection.con.prepareStatement(DbConnection.sql);
                    DbConnection.ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Updated record successfully");

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
