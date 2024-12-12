package service;

import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import com.fasterxml.jackson.databind.*;

import daoImplementation.CustomerDaoIplem;
import daoImplementation.OrderDaoImplem;
import databaseManager.DBConnection;
import model.Order;

public class OrderThread extends Thread  {
	
    private static final String INPUT_FILE = "src/Data/Input.json";
    private static final String OUTPUT_FILE = "src/Data/output.json";
    private static final String ERROR_FILE = "src/Data/error.json";
    OrderDaoImplem orderimpl = new OrderDaoImplem();
    CustomerDaoIplem customerimpl = new CustomerDaoIplem();

    private DBConnection dbconnection;

    public OrderThread(DBConnection dbconnection) {
        this.dbconnection = dbconnection;
    }

    @Override
    public void run() {
        try {
        	
            System.out.println("Thread secondaire - Début de la lecture des ordres depuis le fichier : " + INPUT_FILE);
            List<Order> orders = readOrdersFromFile(INPUT_FILE);
            System.out.println("Thread secondaire - Lecture des ordres terminée. Nombre d'ordres lus : " + orders.size());

            // Traiter chaque ordre
            List<Order> validOrders = new ArrayList<>();
            List<Order> invalidOrders = new ArrayList<>();

            for (Order order : orders) {
                System.out.println("Thread secondaire - Traitement de l'ordre ID: " + order.getId());

                if (customerimpl.CheckUser(order.getCustomerId())) {
                	// Vérifier si l'insertion a réussi
                    boolean insertionSuccessful = orderimpl.InsertOrder(order.getId(), order.getDate(), order.getAmount(), order.getCustomerId());
                    
                    if (insertionSuccessful) {
                        System.out.println("Thread secondaire - Ordre ID: " + order.getId() + " inséré avec succès dans la base de données.");
                        validOrders.add(order); 
                    } else {
                        System.out.println("Thread secondaire - Échec de l'insertion de l'ordre ID: " + order.getId());
                        invalidOrders.add(order); 
                    }
                } else {
                    invalidOrders.add(order);
                }
            }

            // Écrire les résultats dans les fichiers output et error
            writeOrdersToFile(OUTPUT_FILE, validOrders);
            writeOrdersToFile(ERROR_FILE, invalidOrders);

            // Nettoyer le fichier input
            Files.write(Paths.get(INPUT_FILE), "[]".getBytes());
            
            System.out.println("Thread secondaire - Traitement terminé.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Order> readOrdersFromFile(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.asList(mapper.readValue(new File(filePath), Order[].class));
    }

    private void writeOrdersToFile(String filePath, List<Order> orders) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), orders);
    }
    

}
