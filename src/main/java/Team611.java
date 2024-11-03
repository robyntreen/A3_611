import org.bson.Document;
import java.util.ArrayList;

public class Team611 {
    private String name611;
    private String managerFirstName;
    private String managerLastName;

    public Team611(String name611, String managerFirstName, String managerLastName) {
        this.name611 = name611;
        this.managerFirstName = managerFirstName;
        this.managerLastName = managerLastName;
    }

    public Document toDocument() {
        return new Document("name", name611)
                .append("managerFirstName", managerFirstName)
                .append("managerLastName", managerLastName)
                .append("players", new ArrayList<>());
    }
}
