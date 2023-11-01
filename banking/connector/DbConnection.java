package connector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbConnection {
    public static String sql;
    public static Connection con;
    public static PreparedStatement ps;
    
    public static void connect() throws Exception{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "admin", "password");
        }
		catch(Exception ex)
		{
			throw new Exception("Database not connected");
		}
    }

    public static void disconnect() throws Exception{
        try{
            if(!(con.isClosed()))
                con.close();           
        }
		catch(Exception ex)
		{
			throw new Exception("Database not disconnected");
		}
    }
}