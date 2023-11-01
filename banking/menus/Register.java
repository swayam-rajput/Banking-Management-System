package menus;
import connector.DbConnection;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.event.*;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicCheckBoxUI;
import javax.swing.plaf.basic.BasicRadioButtonUI;

public class Register extends JPanel implements ActionListener, KeyListener{

    private JTextField idField, userField, emailField, phField, nameField;
    private JPasswordField pwdField, pwdField1;
    private JCheckBox showpass;
    private JRadioButton female, male;
    private ButtonGroup genderGroup;
    private JButton registerBtn;
    String gender = "";
    Font f = new Font("Futura Md BT",Font.PLAIN,16);
    
    public Register() {
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
        idField.setBounds(220, 50, 200, 30);
        idField.setOpaque(false);
        idField.setBorder(null);
        idField.setEditable(false);

        nameField = new JTextField();
        nameField.setBounds(220, 85, 200, 30);
        nameField.setOpaque(false);
        
        userField = new JTextField();
        userField.setBounds(220, 120, 200, 30);
        userField.setOpaque(false);
        
        emailField = new JTextField("@gmail.com");
        emailField.setFont(f.deriveFont(14f));
        emailField.setBounds(220, 160, 200, 30);
        emailField.setOpaque(false);

        phField = new JTextField();
        phField.setBounds(220, 240, 200, 30);
        phField.setOpaque(false);
        
        pwdField = new JPasswordField();
        pwdField.setBounds(220, 280, 200, 30);
        pwdField.setOpaque(false);
        pwdField.addKeyListener(this);
        
        pwdField1 = new JPasswordField();
        pwdField1.setBounds(220, 320, 200, 30);
        pwdField1.setForeground(Color.WHITE);
        pwdField1.setOpaque(false);
        pwdField1.setEnabled(false);
        
        female = new JRadioButton("Female");
        female.setFont(f);
        female.setUI(new BasicRadioButtonUI());
        female.setForeground(Color.WHITE);
        female.setOpaque(false);
        female.setBounds(300, 200, 80, 30);
        female.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                gender = female.getText();
            }
        });
        
        male = new JRadioButton("Male");
        male.setFont(f);
        male.setUI(new BasicRadioButtonUI());
        male.setForeground(Color.WHITE);
        male.setOpaque(false);
        male.setBounds(220, 200, 70, 30);
        male.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                gender = male.getText();
            }
        });
        
        genderGroup = new ButtonGroup();
        genderGroup.add(female);
        genderGroup.add(male);
        
        showpass = new JCheckBox("Show Password",false);
        showpass.setBounds(220,360,140,20);
        showpass.setOpaque(false);
        showpass.setFont(f.deriveFont(14f));
        showpass.setForeground(Color.WHITE);
        showpass.setUI(new BasicCheckBoxUI());
        showpass.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie){
                pwdField.setEchoChar(showpass.isSelected()?(char)0:'•');
            }
        }); 
                
        registerBtn = new JButton("Register");
        registerBtn.setFont(f);
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setOpaque(false);
        registerBtn.setUI(new BasicButtonUI());
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerBtn.setBounds(220, 420, 100, 40);
        registerBtn.addActionListener(this);

        add(idField);
        add(nameField);
        add(userField);
        add(emailField);
        add(male);
        add(female);
        add(phField);
        add(pwdField);
        add(pwdField1);
        add(showpass);
        add(registerBtn);
        
        JLabel lbl = new JLabel("Employee ID");
        lbl.setBounds(100, 50, 110, 30);
        add(lbl);
                
        lbl = new JLabel("Name");
        lbl.setBounds(140, 85, 50, 30); 
        add(lbl);
        
        lbl = new JLabel("Username");
        lbl.setBounds(120, 120, 90, 30); 
        add(lbl);

        lbl = new JLabel("Email");
        lbl.setBounds(150, 160, 50, 30); 
        add(lbl);
        
        lbl = new JLabel("Gender");
        lbl.setBounds(140, 200, 70, 30); 
        add(lbl);
        
        lbl = new JLabel("Ph. Number");
        lbl.setBounds(110, 240, 100, 30); 
        add(lbl);
        
        lbl = new JLabel("Create Password");
        lbl.setBounds(80, 280, 130, 30); 
        add(lbl);
        
        lbl = new JLabel("Confirm Password");
        lbl.setBounds(70, 320, 140, 30); 
        add(lbl);
        
        setOpaque(false);
        setBorder(BorderFactory.createTitledBorder((BorderFactory.createLineBorder(Color.white)),"Sign up",TitledBorder.CENTER,TitledBorder.TOP,f,Color.white));
        setBounds(500,120,550,500);
        setVisible(true);
        
    }

    @Override
    public void keyReleased(KeyEvent k){
        pwdField1.setEnabled(true);
    }

    @Override
    public void keyPressed(KeyEvent k){}
    @Override
    public void keyTyped(KeyEvent k){}
    
    public void clearAllTextFields() {
        idField.setText("");
        nameField.setText("");
        userField.setText("");
        emailField.setText("");
        phField.setText("");
        pwdField.setText("");
        pwdField1.setText("");
    }
    
    public int IdGenerator(){
        Random randInt = new Random();
        return randInt.nextInt(12000,12099); 
    }

    @Override
    public void actionPerformed(ActionEvent e){
        int valid = 0;
        try{
            String empid = "E";
            String email = emailField.getText().trim().toLowerCase();
            String pswd = String.valueOf(pwdField.getPassword()).trim();
            String pswd1 = String.valueOf(pwdField1.getPassword()).trim();
            String username = userField.getText().trim();
            String name = nameField.getText().trim();
            String phno = phField.getText().trim();
            String fields[] = {username,email,pswd,phno};
            String err[] = {
                "Username must be in the range of 5-20 characters",
                "Invalid Email",
                "Invalid Password",
                // "Password must be alphanumeric and in the range of 8-20 characters",
                "Invalid Phone No"
            };
            String regex[] = {"[a-z0-9_]{5,20}","^[a-z0-9_]{4,15}@gmail\\.com$","[\\w#_@]{4,20}","^\\d{10}$"};

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

            if(gender.length()==0)
                throw new Exception("Select Gender");
            if(!(pswd.equals(pswd1)))
                throw new Exception("Passwords do not match");
            empid += ""+IdGenerator();
            DbConnection.connect();
            if(valid==4){
                idField.setText(empid);
                DbConnection.sql = "insert into emprecord values('"+empid+"','"+name+"','"+username+"','"+email+"','"+gender+"',"+phno+",'"+pswd+"')";
                DbConnection.ps = DbConnection.con.prepareStatement(DbConnection.sql);
                DbConnection.ps.executeUpdate();
            }else{
                throw new Exception("Syntax might be wrong, try again");
            }

            DbConnection.disconnect();
            JOptionPane.showMessageDialog(this, "Inserted record successfully\nYour Employee ID: "+empid,"",JOptionPane.INFORMATION_MESSAGE);
            clearAllTextFields();
            // this.dispose();
            // new Login();
        }catch(SQLIntegrityConstraintViolationException dupException){ // exception when the random number already exists
            if(dupException.getMessage().contains("PRIMARY")){
                JOptionPane.showMessageDialog(this, "Something went wrong, try again");

            }else if(dupException.getMessage().contains(".email")){
                JOptionPane.showMessageDialog(this, "Email already exists");
            }else if(dupException.getMessage().contains(".username")){
                JOptionPane.showMessageDialog(this, "Username already exists");
            }
            clearAllTextFields();
        }catch(Exception ex){
            if(ex.getMessage().contains("Email"))
            emailField.setText("@gmail.com");
            else if(ex.getMessage().contains("Password")){
                pwdField.setText("");
                pwdField1.setText("");
            }
            // userField.setText(userField.getText());
            JOptionPane.showMessageDialog(this, ex.getMessage());
            clearAllTextFields();
        }
    }
}