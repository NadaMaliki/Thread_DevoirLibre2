package Main;

import databaseManager.DBConnection;
import service.OrderThread;

public class Main {

    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection();

        OrderThread orderThread = new OrderThread(dbConnection);

        System.out.println("Le thread principal démarre.");
        orderThread.start();

        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread principal (" + Thread.currentThread().getId() + ") - Tâche en cours : " + (i + 1));
                Thread.sleep(300); 
            }

            orderThread.join(); 

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Le thread principal a terminé.");
    }
}
