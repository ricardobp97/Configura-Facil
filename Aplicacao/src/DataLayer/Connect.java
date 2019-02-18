package DataLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Kelly13$"; //a colocar a vossa password
    private static final String URL = "localhost";
    private static final String SCHEMA = "ConfiguraFacil";
    
    private static Connection connection = null;
    
    public static Connection connect() throws SQLException {
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://"+URL+"/"+SCHEMA+"?user="+USERNAME+"&password="+PASSWORD);
 
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}