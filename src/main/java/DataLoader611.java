// DataLoader.java

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;

public class DataLoader611 {
    private final MongoCollection<Document> playerCollection;
    private final MongoCollection<Document> teamCollection;

    public DataLoader611(MongoDatabase database) {
        playerCollection = database.getCollection("players611");
        teamCollection = database.getCollection("teams611");
    }

    public void initializeData() {
        // Drop collections if they already exist
        playerCollection.drop();
        teamCollection.drop();

        // Sample players
        Player611 player1 = new Player611("Doe611", "John", "123-456-7890", Arrays.asList("defender"), Arrays.asList("TeamA611", "TeamB611"));
        Player611 player2 = new Player611("Smith611", "Jane", "098-765-4321", Arrays.asList("midfielder"), Arrays.asList("TeamB611", "TeamC611"));
        Player611 player3 = new Player611("Brown611", "Chris", "555-123-4567", Arrays.asList("attacker"), Arrays.asList("TeamC611", "TeamA611", "TeamB611"));
        Player611 player4 = new Player611("Johnson611", "Pat", "555-987-6543", Arrays.asList("defender", "midfielder"), Arrays.asList("TeamA611", "TeamC611"));
        Player611 player5 = new Player611("Lee611", "Taylor", "555-234-5678", Arrays.asList("attacker", "midfielder"), Arrays.asList("TeamB611", "TeamA611"));
        Player611 player6 = new Player611("Miller611", "Alex", "555-345-6789", Arrays.asList("defender", "midfielder"), Arrays.asList("TeamB611", "TeamC611"));
        Player611 player7 = new Player611("Garcia611", "Jordan", "555-456-7890", Arrays.asList("midfielder"), Arrays.asList("TeamC611"));
        Player611 player8 = new Player611("Martinez611", "Sam", "555-567-8901", Arrays.asList("defender", "attacker", "midfielder"), Arrays.asList("TeamA611", "TeamB611", "TeamC611"));
        Player611 player9 = new Player611("Lopez611", "Casey", "555-678-9012", Arrays.asList("defender", "attacker", "midfielder"), Arrays.asList("TeamC611", "TeamA611"));
        Player611 player10 = new Player611("Gonzalez611", "Alexis", "555-789-0123", Arrays.asList("attacker"), Arrays.asList("TeamC611"));

        // Insert players
        playerCollection.insertOne(player1.toDocument());
        playerCollection.insertOne(player2.toDocument());
        playerCollection.insertOne(player3.toDocument());
        playerCollection.insertOne(player4.toDocument());
        playerCollection.insertOne(player5.toDocument());
        playerCollection.insertOne(player6.toDocument());
        playerCollection.insertOne(player7.toDocument());
        playerCollection.insertOne(player8.toDocument());
        playerCollection.insertOne(player9.toDocument());
        playerCollection.insertOne(player10.toDocument());

        // Sample teams
        Team611 teamA = new Team611("TeamA611", "Alice", "Smith");
        Team611 teamB = new Team611("TeamB611", "Bob", "Jones");
        Team611 teamC = new Team611("TeamC611", "Carol", "Taylor");

        // Insert teams
        teamCollection.insertOne(teamA.toDocument());
        teamCollection.insertOne(teamB.toDocument());
        teamCollection.insertOne(teamC.toDocument());

        System.out.println("Data initialized successfully.");
    }


    public static void main(String[] args) {
        MongoDatabase database = DatabaseConnection611.getDatabase();  // Connect to the database
        DataLoader611 dataLoader = new DataLoader611(database);           // Create DataLoader instance
        dataLoader.initializeData();                                // Insert sample data

        System.out.println("Data initialized successfully.");
    }
}
