package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnect {
	private Connection connection;
    
    public void openConnect() throws SQLException{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/mavin_groupdb";
            String user = "root";
            String password = "03091994";
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception ex){
        }
    }
    
    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException ex){
        }
    }
    
    public Connection getConnection() {
        return connection;
    }
    
}
