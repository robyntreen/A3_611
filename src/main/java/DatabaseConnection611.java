import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DatabaseConnection611 {
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create("mongodb+srv://rb299754:Rct!2003@a3.nmsmi.mongodb.net/?retryWrites=true&w=majority&appName=A3");
            database = mongoClient.getDatabase("A3_611");
        }
        return database;
    }
}
