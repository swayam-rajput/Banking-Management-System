package menus;
import connector.DbConnection;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.event.*;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicRadioButtonUI;

public class CustomerSigned extends JPanel implements ActionListener{
    
    private JTextField idField, nameField, phField;
    private JRadioButton savings, current;
    private ButtonGroup acctypeGroup;
    
    private JButton registerBtn;
    String acctype = "";
    Font f = new Font("Segoe UI",Font.PLAIN,15);
    
    public CustomerSigned() {
        UIManager.put("TextField.font", f);
        UIManager.put("Label.font", f);
        UIManager.put("PasswordField.font", f);
        UIManager.put("Label.foreground", Color.white);
        UIManager.put("TextField.foreground", Color.white);
        UIManager.put("PasswordField.foreground", Color.white);
        UIManager.put("TextField.caretForeground", Color.cyan);
        UIManager.put("PasswordField.caretForeground", Color.cyan);
        UIManager.put("TextField.border", BorderFactory.createLineBorder(new Color(124,166,178 )));
        UIManager.put("PasswordField.border", BorderFactory.createLineBorder(new Color(124,166,178 )));
        
        setLayout(null);
        
        idField = new JTextField();
        idField.setBounds(220, 100, 200, 30);
        idField.setOpaque(false);
        idField.setBorder(null);
        idField.setEditable(false);
        
        nameField = new JTextField();
        nameField.setBounds(220, 150, 200, 30);
        nameField.setOpaque(false);
        
        // emailField.setBounds(220, 130, 200, 30);
        
        savings = new JRadioButton("Savings");
        savings.setFont(f);
        savings.setUI(new BasicRadioButtonUI());
        savings.setForeground(Color.WHITE);
        savings.setOpaque(false);
        savings.setBounds(220, 200, 90, 30);
        savings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                acctype = savings.getText();
            }
        });

        current = new JRadioButton("Current");
        current.setFont(f);
        current.setUI(new BasicRadioButtonUI());
        current.setForeground(Color.WHITE);
        current.setOpaque(false);
        current.setBounds(310, 200, 80, 30);
        current.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                acctype = current.getText();
            }
        });
        
        acctypeGroup = new ButtonGroup();
        
        acctypeGroup.add(savings);
        acctypeGroup.add(current);
        

        phField = new JTextField();
        phField.setBounds(220, 250, 200, 30);
        phField.setOpaque(false);
                       
        registerBtn = new JButton("Create Account");
        registerBtn.setFont(f);
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setOpaque(false);
        registerBtn.setUI(new BasicButtonUI());
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerBtn.setBounds(220, 400, 150, 40);
        registerBtn.addActionListener(this);

        add(idField);
        add(nameField);
        add(phField);
        add(savings);
        add(current);
        // add(pwdField);
        // add(pwdField1);
        add(registerBtn);
        
        JLabel lbl = new JLabel("Account No");
        lbl.setBounds(115, 100, 90, 30);
        add(lbl);

        lbl = new JLabel("Name");
        lbl.setBounds(150, 150, 50, 30);
        add(lbl);


        lbl = new JLabel("Account Type");
        lbl.setBounds(100, 200, 100, 30);
        add(lbl);

        lbl = new JLabel("Ph. Number");
        lbl.setBounds(110, 250, 100, 30);
        add(lbl);

       
        setOpaque(false);
        setBorder(BorderFactory.createTitledBorder((BorderFactory.createLineBorder(Color.white)),"Customer Creation",TitledBorder.CENTER,TitledBorder.TOP,f,Color.white));
        setBounds(500,150,550,500);
        setVisible(true);
        
    }

    
    public int IdGenerator(){
        Random randInt = new Random();
        return randInt.nextInt(64600,64699); 
    }

    @Override
    public void actionPerformed(ActionEvent e){
        int valid = 0;
        
        try{
            String accnum = "A";
            String name = nameField.getText().trim();
            String phno = phField.getText().trim();
            String fields[] = {name,phno};
            String err[] = {
                "Invalid Name",
                "Invalid Phone No"
            };
            String regex[] = {"[a-zA-Z ]+","^\\d{10}$"};

            for (int i = 0; i < regex.length; i++) {
                String r = regex[i];
                Pattern pattern = Pattern.compile(r);
                Matcher matcher = pattern.matcher(fields[i]);
                if(matcher.matches())
                    valid++;
                else{
                    throw new Exception(err[i]);
                }
            }

            if(acctype.length()==0)
                throw new Exception("Select Type of Account");
            accnum += ""+IdGenerator();
            DbConnection.connect();
            if(valid==2){
                idField.setText(accnum);
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                Date datetime = new Date();
                String currenttime = timeFormat.format(datetime);
                String currentdate = dateFormat.format(datetime)+" "+currenttime;
                
                DbConnection.sql = "insert into customers(cname,accno,phno,acctype,doc) values('"+name+"','"+accnum+"','"+phno+"','"+acctype+"','"+currentdate+"')";
                DbConnection.ps = DbConnection.con.prepareStatement(DbConnection.sql);
                DbConnection.ps.executeUpdate();
                System.out.println("Executed successfully");
            }else{
                throw new Exception("Syntax might be wrong, try again");
            }

            DbConnection.disconnect();
            JOptionPane.showMessageDialog(this, "Inserted record successfully\nYour Account No: "+accnum,"",JOptionPane.INFORMATION_MESSAGE);
            nameField.setText("");
            phField.setText("");
	        idField.setText("");
            acctypeGroup.clearSelection();
        }catch(SQLIntegrityConstraintViolationException dupException){ // exception when the random number already exists
            if(dupException.getMessage().contains("PRIMARY")){
                JOptionPane.showMessageDialog(this, "Something went wrong, try again");
            }else if(dupException.getMessage().contains(".phno")){
                JOptionPane.showMessageDialog(this, "Phno already exists");
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}