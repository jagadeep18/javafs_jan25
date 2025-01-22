package pizza_project;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.*;
import com.mongodb.client.model.*;

public class pizza_projectDao {
    private Scanner scanner;
    private MongoClient mongoClient;

    {
        scanner = new Scanner(System.in);
    }

    private Document readPizzaDetails() {
        System.out.print("Enter Pizza name: ");
        String name = scanner.next();
        
        // Validate Pizza size input
        byte size = 0;
        boolean validSize = false;
        while (!validSize) {
            try {
                System.out.print("Enter Pizza size in inches: ");
                size = scanner.nextByte();
                validSize = true; // valid input
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid numeric size (e.g., 8, 12, 16).");
                scanner.next(); // Clear the invalid input
            }
        }

        // Validate Pizza price input
        float price = 0;
        boolean validPrice = false;
        while (!validPrice) {
            try {
                System.out.print("Enter Pizza price: ");
                price = scanner.nextFloat();
                validPrice = true; // valid input
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid price (e.g., 10.5, 15.99).");
                scanner.next(); // Clear the invalid input
            }
        }

        Document pizza = new Document("name", name).append("size", size).append("price", price);
        return pizza;
    }

    private MongoDatabase connectDb() {
        String url = "mongodb://localhost:27017";
        MongoDatabase database = null;
        try {
            mongoClient = MongoClients.create(url);
            database = mongoClient.getDatabase("jagadeep_db");
            System.out.println("DB connection successful");
            return database;
        } catch (Exception e) {
            System.out.println("Error in connecting to DB");
        }
        return database;
    }

    private void disConnectDb(MongoDatabase db) {
        mongoClient.close();
        System.out.println("DB disconnected");
    }

    public void insertPizza() {
        MongoDatabase db = connectDb();
        Document pizza = readPizzaDetails();
        MongoCollection<Document> collection = db.getCollection("pizzas");
        collection.insertOne(pizza);
        disConnectDb(db);
    }

    public void deletePizza() {
        MongoDatabase db = connectDb();
        System.out.print("Enter name of Pizza to be deleted: ");
        String name = scanner.next();
        MongoCollection<Document> collection = db.getCollection("pizzas");
        Bson filter = Filters.eq("name", name);
        collection.deleteOne(filter);
        System.out.println("Document deleted");
        disConnectDb(db);
    }

    public void updatePizza() {
        MongoDatabase db = connectDb();
        System.out.print("Enter name of Pizza to be updated: ");
        String name = scanner.next();
        System.out.print("Enter new price of the Pizza: ");
        float price = scanner.nextFloat();
        MongoCollection<Document> collection = db.getCollection("pizzas");
        Bson filter = Filters.eq("name", name);
        Bson update = Updates.set("price", price);
        collection.updateOne(filter, update);
        System.out.println("Document updated");
        disConnectDb(db);
    }

    public void searchPizza() {
        MongoDatabase db = connectDb();
        System.out.print("Enter name of Pizza to be searched: ");
        String name = scanner.next();

        MongoCollection<Document> collection = db.getCollection("pizzas");
        Bson filter = Filters.eq("name", name);
        Document result = collection.find(filter).first();

        if (result != null) {
            System.out.println("Pizza is: " + result.toJson());
        } else {
            System.out.println("Pizza not found.");
        }
        disConnectDb(db);
    }

    public void listAllPizza() {
        MongoDatabase db = connectDb();
        MongoCollection<Document> collection = db.getCollection("pizzas");

        for (Document pizza : collection.find()) {
            System.out.println(pizza.toJson());
        }
        disConnectDb(db);
    }
}
