package dao;

import java.sql.Date;

public interface OrderDao {

	public boolean InsertOrder(int id ,long date, double amount, int customer_id);
}
