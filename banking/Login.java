// package banking;

// import banking.Portal;
import connector.DbConnection;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicCheckBoxUI;

public class Login extends JFrame implements ActionListener{
    // add logo of the bank

    public static boolean isAdmin = false; 
    public static String emplid = null; 
    private JButton logbtn,emailbtn;
    private JLabel userLbl;
    private JTextField userField, empNumField;
    private JPanel panel;
    private JPasswordField pwdField;
    private JCheckBox showpass;
    Font f = new Font("Futura Md BT",Font.PLAIN,16);
    
    public Login() {
        JLabel bglbl = new JLabel();
        bglbl.setBounds(0,0,1920,1080);
        bglbl.setOpaque(true);
        bglbl.setIcon(new ImageIcon("bankbg.jpg"));
        
        Color fg = Color.white;
        // Color fg = Color.black;
        JLabel lbl;
        UIManager.put("Label.font",f);
        UIManager.put("Button.font", f);
        UIManager.put("TextField.font",f);
        UIManager.put("Label.foreground",fg);
        UIManager.put("Button.foreground", fg);
        UIManager.put("TextField.foreground",fg);
        UIManager.put("PasswordField.foreground",fg);
        UIManager.put("TextField.caretForeground", Color.cyan);
        UIManager.put("PasswordField.caretForeground", Color.cyan);
        
        panel = new JPanel();
        panel.setLayout(null); 
        panel.setBounds(520,150,460,470);
        
        userLbl = new JLabel("Username");
        userLbl.setBounds(100, 150, 80, 30);
        
        lbl = new JLabel("Password");
        lbl.setBounds(105, 190, 70, 30);
        panel.add(lbl);
        
        lbl = new JLabel("Employee ID");
        lbl.setBounds(80, 110, 100, 30);
        panel.add(lbl);

        userField = new JTextField(20);
        userField.setBounds(190, 150, 180, 30);
        userField.setOpaque(false);
        userField.setBorder(BorderFactory.createLineBorder(new Color(124,166,178)));
        userField.setFont(f.deriveFont(15f));
                
        pwdField = new JPasswordField(20);
        pwdField.setEchoChar('•');
        pwdField.setBounds(190, 190, 180, 30);
        pwdField.setOpaque(false);
        pwdField.setBorder(BorderFactory.createLineBorder(new Color(124,166,178)));
        pwdField.setFont(f.deriveFont(15f));

        empNumField = new JTextField(0);
        empNumField.setBounds(190, 110, 180, 30);
        empNumField.setOpaque(false);
        empNumField.setBorder(null);
        // empNumField.setBorder(BorderFactory.createLineBorder(new Color(124,166,178)));
        empNumField.setEditable(false);
        
        logbtn = new JButton("Login");
        logbtn.setBounds(195, 340, 80, 40);
        logbtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logbtn.setOpaque(false);
        logbtn.setUI(new BasicButtonUI());
        logbtn.addActionListener(this);
        
        emailbtn = new JButton("Log in with Email instead?");
        emailbtn.setBounds(190, 255, 190, 25);
        emailbtn.setVerticalAlignment(SwingConstants.NORTH);
        // emailbtn.setBackground(new Color(86, 115, 181));
        emailbtn.setFont(f.deriveFont(Font.BOLD, 12f));
        emailbtn.setBorderPainted(false);
        emailbtn.setOpaque(false);
        emailbtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        emailbtn.setUI(new BasicButtonUI());
        emailbtn.addActionListener(this);
        
        showpass = new JCheckBox("Show Password",false);
        showpass.setBounds(260,230,110,15);
        showpass.setOpaque(false);
        showpass.setFont(f.deriveFont(12f));
        showpass.setCursor(new Cursor(Cursor.HAND_CURSOR));
        showpass.setForeground(fg);
        showpass.setUI(new BasicCheckBoxUI());
        showpass.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie){
                pwdField.setEchoChar(showpass.isSelected()?(char)0:'•');
            }
        });

        panel.add(userLbl);
        panel.add(userField);
        panel.add(pwdField);
        panel.add(logbtn);
        panel.add(emailbtn);
        panel.add(empNumField);
        panel.add(showpass);
        panel.setOpaque(false);
        add(panel);
        add(bglbl);
        pack();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(getMaximumSize());
        setResizable(false);
        setVisible(true);
        setTitle("Login");
        
    }

    
    @Override
    public void actionPerformed(ActionEvent e){
        try {
            if(e.getActionCommand().equals("Login")){
                String uname = userField.getText().trim();
                String pwd = String.valueOf(pwdField.getPassword()).trim();
                String useremail = userLbl.getText().trim().toLowerCase();
                String userdb = "", pwdb = "",empid="";
                String regex[] = {"^[a-z0-9_]{5,20}$","^[a-zA-Z0-9#_@]{4,20}$"};
                String fields[] = {uname,pwd};
                int valid = 0;

                if(useremail.equals("email"))
                    regex[0] = "^[a-z0-9_]{4,15}@gmail\\.com$";
                
                for (int i = 0; i < regex.length; i++) {
                    Pattern pattern = Pattern.compile(regex[i]);
                    Matcher matcher = pattern.matcher(fields[i]);
                    if(matcher.matches())
                        valid++;
                }
                
                if(valid==2){
                    DbConnection.sql = "select "+useremail+",empid,password from emprecord where "+useremail+" = '"+uname+"'";
                    DbConnection.connect();
                    DbConnection.ps = DbConnection.con.prepareStatement(DbConnection.sql);
                    ResultSet rs = DbConnection.ps.executeQuery();
                    
                    if(rs.next()){
                        userdb = rs.getString(useremail);
                        pwdb = rs.getString("password");
                        empid = rs.getString("empid");
                    }
                    
                    if(uname.equals(userdb)){
                        if(pwd.equals(pwdb)){
                            String loginmsg = "Successfully logged in";
                            empNumField.setText(empid);
                            Login.emplid = empid;
                            if(empid.contains("000")){
                                isAdmin = true;
                                loginmsg += " as Admin";
                            }
                            JOptionPane.showMessageDialog(this, loginmsg);
                        }else
                            throw new Exception("Password does not match");
                    }else{
                        throw new Exception(userLbl.getText()+" does not exist");
                    }
                    DbConnection.disconnect();
                    this.dispose();
                    new Portal();
                }else{
                    throw new Exception(userLbl.getText().trim()+" or Password are invalid");
                }
            }else if(e.getActionCommand().contains("with")){
                userField.setText("");
                pwdField.setText("");
                if(userLbl.getText().equals("Username")){
                    userLbl.setText("      Email");
                    userField.setText("@gmail.com");
                    emailbtn.setBounds(160, 255, 220, 25);
                    emailbtn.setText(emailbtn.getText().replace("Email","Username"));
                }else{
                    userLbl.setText("Username");
                    emailbtn.setText(emailbtn.getText().replace("Username","Email"));
                    emailbtn.setBounds(180, 255, 200, 25);
                }
            }else{
                this.dispose();
                // new Register();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}