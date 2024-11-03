import org.bson.Document;
import java.util.List;

public class Player611 {
    private String lastName611;
    private String firstName;
    private String phone;
    private List<String> positions;
    private List<String> teamPreferences;

    public Player611(String lastName611, String firstName, String phone, List<String> positions, List<String> teamPreferences) {
        this.lastName611 = lastName611;
        this.firstName = firstName;
        this.phone = phone;
        this.positions = positions;
        this.teamPreferences = teamPreferences;
    }

    public Document toDocument() {
        return new Document("lastName", lastName611)
                .append("firstName", firstName)
                .append("phone", phone)
                .append("positions", positions)
                .append("teamPreferences", teamPreferences);
    }
}

