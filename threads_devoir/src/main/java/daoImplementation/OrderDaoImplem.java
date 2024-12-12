package daoImplementation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dao.OrderDao;
import databaseManager.DBConnection;
import model.Order;

public class OrderDaoImplem implements OrderDao {
	
	DBConnection dbconnection = new DBConnection();
	Connection conn = dbconnection.getConnection();
	
	@Override

	//Inserer l'ordre dans la base de données
    public boolean InsertOrder(int id ,long date, double amount, int customer_id) {
        String query = "insert into `order`(id, date, amount, customer_id) values (?, ?, ?, ?)";

        try(PreparedStatement statement= conn.prepareStatement(query)){
            statement.setInt(1, id);
            statement.setTimestamp(2, new Timestamp(date));
            statement.setDouble(3, amount);
            statement.setInt(4, customer_id);
            int result = statement.executeUpdate();
            return (result >0);
            
        } catch (SQLException e) {
            e.printStackTrace();
        	return false;
        }
    }
	
	//visualiser les ordre à partir de la BD
	public List<Order> getOrders() {
	    List<Order> orders = new ArrayList<>();
	    String query = "SELECT id, date, amount, customer_id FROM orders";

	    try (Statement statement = conn.createStatement();
	         ResultSet resultSet = statement.executeQuery(query)) {

	        while (resultSet.next()) {
	            int id = resultSet.getInt("id");
	            long timestamp = resultSet.getTimestamp("date").getTime(); 
	            double amount = resultSet.getDouble("amount");
	            int customerId = resultSet.getInt("customer_id");

	            orders.add(new Order(id, timestamp, amount, customerId));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return orders;
	}

}
