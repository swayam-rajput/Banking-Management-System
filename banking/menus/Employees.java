package menus;

import connector.DbConnection;
import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class Employees extends JPanel{
    public Employees(){
        String sqlQuery = "select empid,name,username,gender,phno from emprecord";
        ResultSet rs;
        JTable table = null;
        try {
            DbConnection.connect();
            DbConnection.ps = DbConnection.con.prepareStatement("select count(empid) from emprecord");
            rs = DbConnection.ps.executeQuery();
            rs.next();
            int rowcount = Integer.parseInt(rs.getString(1));
            String rows[][] = new String[rowcount][5];
            DbConnection.ps = DbConnection.con.prepareStatement(sqlQuery);
            rs = DbConnection.ps.executeQuery();
            // understand metadata first
            String columns[] = new String[rs.getMetaData().getColumnCount()];
            
            for (int i = 1; i <= columns.length; i++) {
                columns[i-1] = (rs.getMetaData().getColumnName(i));
            }
            int row = 0;
            while(rs.next()){
                for (int j = 0; j < columns.length; j++) {
                    rows[row][j] = rs.getString(j+1);
                }
                row++;
            }
            table = new JTable(rows,columns);
            table.setRowHeight(30);
            table.setShowGrid(false);
            table.setOpaque(false);
            
            DbConnection.disconnect();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            
        }
        
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(30, 120, 500, 300);
        jsp.setOpaque(false);
        this.add(jsp);

        setLayout(null);
        setBounds(500,150,560,500);
        setBorder(BorderFactory.createTitledBorder((BorderFactory.createLineBorder(Color.white)),"Current Employees",TitledBorder.CENTER,TitledBorder.TOP,new Font("Segoe UI",Font.PLAIN,14),Color.white));
        setOpaque(false);
        
        setVisible(true);
    }
}