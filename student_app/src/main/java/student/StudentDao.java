package student;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.*;
import com.mongodb.client.model.*;

public class StudentDao {
    private Scanner scanner;
    private MongoClient mongoClient;

    // Initialize scanner
    {
        scanner = new Scanner(System.in);
    }

    // Read student details from user input
    private Document readStudentDetails() {
        System.out.print("Enter Student ID: ");
        short id = 0;
        boolean validId = false;
        while (!validId) {
            try {
                id = scanner.nextShort();
                validId = true; // Valid input
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid numeric ID.");
                scanner.next(); // Clear invalid input
            }
        }

        System.out.print("Enter Student Name: ");
        String name = scanner.next();

        byte semester = 0;
        boolean validSemester = false;
        while (!validSemester) {
            try {
                System.out.print("Enter Semester: ");
                semester = scanner.nextByte();
                validSemester = true; // Valid input
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid numeric semester.");
                scanner.next(); // Clear invalid input
            }
        }

        float averageScore = 0;
        boolean validScore = false;
        while (!validScore) {
            try {
                System.out.print("Enter Average Score: ");
                averageScore = scanner.nextFloat();
                validScore = true; // Valid input
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid numeric score.");
                scanner.next(); // Clear invalid input
            }
        }

        System.out.print("Enter Branch: ");
        String branch = scanner.next();

        // Create and return a MongoDB document for the student
        return new Document("id", id)
                .append("name", name)
                .append("semester", semester)
                .append("averageScore", averageScore)
                .append("branch", branch);
    }

    // Connect to MongoDB
    private MongoDatabase connectDb() {
        String url = "mongodb://localhost:27017";
        try {
            mongoClient = MongoClients.create(url);
            MongoDatabase database = mongoClient.getDatabase("jagadeep_db");
            System.out.println("DB connection successful");
            return database;
        } catch (Exception e) {
            System.out.println("Error in connecting to DB");
        }
        return null;
    }

    // Disconnect from MongoDB
    private void disConnectDb() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("DB disconnected");
        }
    }

    // Insert student details into MongoDB
    public void insertStuDetails() {
        MongoDatabase db = connectDb();
        if (db == null) return;

        Document student = readStudentDetails();
        MongoCollection<Document> collection = db.getCollection("students");
        collection.insertOne(student);
        System.out.println("Student details inserted successfully.");
        disConnectDb();
    }

    // Delete student details from MongoDB
    public void deleteStuDetails() {
        MongoDatabase db = connectDb();
        if (db == null) return;

        System.out.print("Enter name of Student to be deleted: ");
        String name = scanner.next();
        MongoCollection<Document> collection = db.getCollection("students");
        Bson filter = Filters.eq("name", name);
        collection.deleteOne(filter);
        System.out.println("Student details deleted successfully.");
        disConnectDb();
    }

    // Update student details in MongoDB
    public void updateStuDetails() {
        MongoDatabase db = connectDb();
        if (db == null) return;

        System.out.print("Enter name of Student to be updated: ");
        String name = scanner.next();
        System.out.print("Enter new Average Score: ");
        float averageScore = scanner.nextFloat();

        MongoCollection<Document> collection = db.getCollection("students");
        Bson filter = Filters.eq("name", name);
        Bson update = Updates.set("averageScore", averageScore);
        collection.updateOne(filter, update);
        System.out.println("Student details updated successfully.");
        disConnectDb();
    }

    // Search for student details in MongoDB
    public void searchStuDetails() {
        MongoDatabase db = connectDb();
        if (db == null) return;

        System.out.print("Enter name of Student to be searched: ");
        String name = scanner.next();

        MongoCollection<Document> collection = db.getCollection("students");
        Bson filter = Filters.eq("name", name);
        Document result = collection.find(filter).first();

        if (result != null) {
            System.out.println("Student details: " + result.toJson());
        } else {
            System.out.println("Student not found.");
        }
        disConnectDb();
    }

    // List all student details from MongoDB
    public void listAllStudents() {
        MongoDatabase db = connectDb();
        if (db == null) return;

        MongoCollection<Document> collection = db.getCollection("students");

        for (Document student : collection.find()) {
            System.out.println(student.toJson());
        }
        disConnectDb();
    }
}
