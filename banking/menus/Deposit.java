package menus;

import connector.DbConnection;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;
import javax.swing.plaf.basic.BasicButtonUI;
import java.sql.ResultSet;

public class Deposit extends JPanel implements ActionListener{
    private JTextField accfield, namefield, balancefield, depositfield, phfield;
    private JButton depbtn, cnclbtn;
    boolean isValidAcc = false;

    public Deposit(){
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
        UIManager.put("TextField.border", BorderFactory.createLineBorder(border));
        
        lbl = new JLabel("Account Number");
        lbl.setBounds(80, 90, 120, 25);
        add(lbl);
        
        lbl = new JLabel("Name");
        lbl.setBounds(150, 175, 50, 25);
        add(lbl);
        
        lbl = new JLabel("Balance");
        lbl.setFont(new Font("Futura Md BT", Font.PLAIN, 15));
        lbl.setBounds(380, 220, 60, 25);
        add(lbl);
        
        lbl = new JLabel("Deposit");
        lbl.setFont(new Font("Futura Md BT", Font.PLAIN, 15));
        lbl.setBounds(150, 220, 60, 25);
        add(lbl);
        
        lbl = new JLabel("Phone");
        lbl.setBounds(150, 130, 50, 25);
        add(lbl);
        
        accfield = new JTextField();
        accfield.setBounds(220, 90, 120, 25);
        accfield.setOpaque(false);
        accfield.setBorder(BorderFactory.createLineBorder(border));
        add(accfield);
        
        namefield = new JTextField();
        namefield.setBounds(220, 175, 120, 25);
        namefield.setOpaque(false);
        namefield.setBorder(null);
        namefield.setEditable(false);
        add(namefield);
        
        balancefield = new JTextField();
        balancefield.setFont(new Font("Futura Hv BT", Font.PLAIN, 16));
        balancefield.setBounds(445, 220, 90, 30);
        balancefield.setBorder(null);
        balancefield.setEditable(false);
        balancefield.setOpaque(false);
        add(balancefield);
        
        depositfield = new JTextField();
        depositfield.setBounds(220, 220, 120, 25);
        depositfield.setBorder(BorderFactory.createLineBorder(border));
        depositfield.setOpaque(false);
        depositfield.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me){
                validateAcc();
            }
        });
        add(depositfield);
           
        phfield = new JTextField();
        phfield.setBounds(220, 130, 120, 25);
        phfield.setBorder(BorderFactory.createLineBorder(border));
        phfield.setOpaque(false);
        add(phfield);
        
        depbtn = new JButton("Deposit");
        depbtn.setUI(new BasicButtonUI());
        depbtn.setForeground(tcol);
        depbtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        depbtn.setOpaque(false);
        depbtn.setBounds(180, 330, 90, 30);
        depbtn.addActionListener(this);
        add(depbtn);
        
        cnclbtn = new JButton("Cancel");
        cnclbtn.setOpaque(false);
        cnclbtn.setForeground(tcol);
        cnclbtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cnclbtn.setUI(new BasicButtonUI());
        cnclbtn.setBounds(280, 330, 90, 30);
        cnclbtn.addActionListener(this);
        add(cnclbtn);
        
        setOpaque(false);
        setBackground(new Color(68,70,84));
        setBorder(BorderFactory.createTitledBorder((BorderFactory.createLineBorder(Color.white)),"Deposit",TitledBorder.CENTER,TitledBorder.TOP,f,Color.white));
        setBounds(500,150,550,450);
        setLayout(null);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        try {
            if(e.getActionCommand().equals("Deposit") && isValidAcc==true){
                try {
                    String acc = "A"+Integer.parseInt(accfield.getText().trim());
                    Long phone = Long.parseLong(phfield.getText().trim());
                    String name = namefield.getText().trim();
                    Double dep = Double.valueOf(depositfield.getText());
                    Double bal = Double.valueOf(balancefield.getText());
                    
                    // DbConnection.sql = "insert into customers values('"+name+"','"+acc+"',"+phone+","+(dep+bal)+")";
                    DbConnection.sql = "update customers set balance = "+(dep+bal)+" where accno = '"+acc+"'";
                    DbConnection.connect();
                    DbConnection.ps = DbConnection.con.prepareStatement(DbConnection.sql);
                    DbConnection.ps.executeUpdate();
                    DbConnection.disconnect();
                    JOptionPane.showMessageDialog(this,"Deposited amount successfully");
                    
                    namefield.setText("");
                    balancefield.setText("");
                    accfield.setText("");
                    phfield.setText("");
                    depositfield.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,ex.getMessage());
                }finally{
                    accfield.setEditable(true);
                    phfield.setEditable(true);
                }
                
            }else if(e.getActionCommand().equals("Cancel")){
                this.setVisible(false);
            }else{
                JOptionPane.showMessageDialog(this, "Enter data first");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Fields must only contain digits");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        }
    }
    public void validateAcc(){
        try{
            String acc = "A"+Integer.parseInt(accfield.getText().trim());
            Long phone = Long.parseLong(phfield.getText().trim());
            // Integer deposit = Integer.parseInt(depositfield.getText().trim());
            String phno = "",cname="",balance="",accdb="";
            
            DbConnection.sql = "select cname,accno,phno,balance from customers where accno = '"+acc+"' and phno = "+phone;
            DbConnection.connect();
            DbConnection.ps = DbConnection.con.prepareStatement(DbConnection.sql);
            
            ResultSet rs = DbConnection.ps.executeQuery();
            if(rs.next()){
                phno = Long.toString(rs.getLong("phno"));
                cname = rs.getString("cname");
                accdb = rs.getString("accno");
                balance = String.valueOf(rs.getDouble("balance"));
            }else 
                throw new Exception("Invalid Credentials");   
            
            accfield.setEditable(false);
            phfield.setEditable(false);
            balancefield.setText(balance);
            namefield.setText(cname);
            isValidAcc=true;
        
            DbConnection.disconnect();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Fields must only contain digits_");
        } catch(Exception ex){
            JOptionPane.showMessageDialog(this, ex.getMessage());
            namefield.setText("");
            balancefield.setText("");
            accfield.setText("");
            phfield.setText("");
        }

    }
}



