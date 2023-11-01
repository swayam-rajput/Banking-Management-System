// package banking;
import menus.*;
import java.awt.*;
import javax.swing.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Portal extends JFrame implements ActionListener{
    JTextArea datefield;
    JLayeredPane layers;
    public static String employeeID = Login.emplid;
    public static boolean admin = Login.isAdmin;
    JPanel activePanel = new JPanel(); // for switchin between panels
    Portal(){
        super("Bank");
        layers = new JLayeredPane();
        layers.setBounds(0,0,1920,1080);
        // layers.setPreferredSize(new Dimension(1920,1080));
        
        JLabel bglbl = new JLabel();
        bglbl.setBounds(0,0,1920,1080);
        bglbl.setOpaque(true);
    	bglbl.setIcon(new ImageIcon("bankbg.jpg"));
        layers.add(bglbl,JLayeredPane.DEFAULT_LAYER);
        
        JMenuBar mnBar = new JMenuBar();
        JMenu customer = new JMenu("Customer");
        
        JMenuItem createcusts = new JMenuItem("Create Customer");
        createcusts.addActionListener(this);
        JMenuItem custinfo = new JMenuItem("Customer Info");
        custinfo.addActionListener(this);

        JMenuItem updatecusts = new JMenuItem("Update Customer");
        updatecusts.addActionListener(this);
        updatecusts.setEnabled(admin);
        customer.add(updatecusts); 
        
        customer.add(createcusts); 
        customer.add(custinfo); 
        
        JMenu transactions = new JMenu("Transactions");
        JMenuItem deposit = new JMenuItem("Deposit");
        deposit.addActionListener(this); 
        
        JMenuItem withdraw = new JMenuItem("Withdraw");
        withdraw.addActionListener(this);
        transactions.add(deposit);
        transactions.add(withdraw);
        
        JMenu employee = new JMenu("Employee");
        JMenuItem empdetails = new JMenuItem("Employees");
        empdetails.setEnabled(admin);
        empdetails.addActionListener(this);
        
        JMenuItem details = new JMenuItem("Details");
        details.addActionListener(this);
        
        JMenuItem register = new JMenuItem("Register Employee");
        register.addActionListener(this);
        register.setEnabled(admin);

        JMenuItem deactivateAcc = new JMenuItem("Account Deactivation");
        deactivateAcc.addActionListener(this);
        deactivateAcc.setEnabled(admin);
        customer.add(deactivateAcc);
        
        employee.add(details);
        employee.add(register);
        employee.add(empdetails);
        
        mnBar.add(transactions);
        mnBar.add(customer);
        mnBar.add(employee);
        
        datefield = new JTextArea("");
        datefield.setEditable(false);
        datefield.setFont(new Font("Futura Md BT", Font.PLAIN, 15));
        datefield.setForeground(Color.white);
        datefield.setBorder(null);
        datefield.setOpaque(false);
        datefield.setBounds(20, 720, 100, 45);
        
        layers.add(datefield,JLayeredPane.DRAG_LAYER);
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e){
                updateTime();
            }
        });
        timer.start();

        this.setJMenuBar(mnBar);
        this.add(layers);
        this.setLayout(null);
        this.setSize(getMaximumSize());
        this.setVisible(true);
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e) {
        String panelClicked = e.getActionCommand();
        switch (panelClicked) {
            case "Customer Info":
                createAndDisplayPanel(new CustomerInfo());
                break;
            case "Update Customer":
                createAndDisplayPanel(new CustomerUpdate());
                break;
            case "Deposit":
                createAndDisplayPanel(new Deposit());
                break;
            case "Withdraw":
                createAndDisplayPanel(new Withdraw());
                break;
            case "Employees":
                createAndDisplayPanel(new Employees());
                break;
            case "Register Employee":
                createAndDisplayPanel(new Register());
                break;
            case "Details":
                createAndDisplayPanel(new EmployeeDetails(Portal.employeeID));
                break;
            case "Create Customer":
                createAndDisplayPanel(new CustomerSigned());
                break;
            case "Account Deactivation":
                createAndDisplayPanel(new CustomerDeactivation());
                break;
        }
    }

    private void createAndDisplayPanel(JPanel panel) {
        if (activePanel != null) {
            activePanel.setVisible(false);
            layers.remove(activePanel);
        }
        activePanel = panel;
        layers.add(panel, JLayeredPane.DRAG_LAYER);
    }
    
    public SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    public void updateTime(){
        Date datetime = new Date();
        String currentdate = dateFormat.format(datetime);
        String currenttime = timeFormat.format(datetime);
        datefield.setText(currenttime+"\n"+currentdate);
    }
    public static void main(String[] args) {
        Portal p = new Portal();
        
    }
}



