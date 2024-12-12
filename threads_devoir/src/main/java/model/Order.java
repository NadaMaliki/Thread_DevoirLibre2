package model;

import java.time.LocalDate;

public class Order {
    private int id;
    private long date;
    private double amount;
    private int customerId;
    
    public Order() {
    }

    public Order(int id, long date, double amount, int customerId) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.customerId = customerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", amount=" + amount +
                ", customerId=" + customerId +
                '}';
    }
}
