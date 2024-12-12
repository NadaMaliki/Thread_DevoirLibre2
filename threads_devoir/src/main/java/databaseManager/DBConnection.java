package databaseManager;

import java.sql.*;

public class DBConnection {
	    private Connection dbconnection;


	    public DBConnection() {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            this.dbconnection = DriverManager.getConnection("jdbc:mysql://localhost:3366/customer_order", "root", "");
	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }

	    }

	    public Connection getConnection() {
	        return dbconnection;
	    }
	    
	    public void closeConnection() {
	        try {
	        	dbconnection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    
	    
}
