package daoImplementation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.CustomerDao;
import databaseManager.DBConnection;

public class CustomerDaoIplem implements CustomerDao{
	
	DBConnection dbconnection = new DBConnection();
	Connection conn = dbconnection.getConnection();
	
	@Override
	//verifier que l'utilisateur existe dans la base de données
    public boolean CheckUser(int id) {
        String query = "select * from customer where id = " + id;
        try {
            ResultSet result = conn.createStatement().executeQuery(query);
            if(result.next()) {
                return true;
            } else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
