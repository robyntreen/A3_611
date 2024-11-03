import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import static com.mongodb.client.model.Filters.eq;

public class AssignmentManager611 {
    private final MongoCollection<Document> playerCollection;
    private final MongoCollection<Document> teamCollection;

    public AssignmentManager611(MongoDatabase database) {
        playerCollection = database.getCollection("players611");
        teamCollection = database.getCollection("teams611");
    }

    public void assignPlayers() {
        List<Document> players = playerCollection.find().into(new ArrayList<>());
        List<Document> teams = teamCollection.find().into(new ArrayList<>());

        int totalPoints = 0;
        int teamSizeLimit = 3;  // Each team should have exactly 3 players

        for (Document player : players) {
            List<String> preferences = player.getList("teamPreferences", String.class);
            String assignedTeam = null;
            int points = 0;

            // Assign based on preference
            if (!preferences.isEmpty()) {
                String preferredTeam = preferences.get(0); // First preference
                assignedTeam = preferredTeam;
                points = 2;  // 2 points for first preference
            } else {
                // If no preference, randomly assign
                assignedTeam = teams.get((int) (Math.random() * teams.size())).getString("name");
                points = -1;  // -1 point for random team assignment
            }

            // Add player to the assigned team
            Document team = teamCollection.find(eq("name", assignedTeam)).first();
            if (team != null) {
                List<Document> teamPlayers = team.getList("players", Document.class);
                if (teamPlayers.size() < teamSizeLimit) {
                    teamPlayers.add(player);
                    team.put("players", teamPlayers);
                    teamCollection.replaceOne(eq("name", assignedTeam), team);
                    totalPoints += points;
                }
            }
        }

        System.out.println("Total points: " + totalPoints);
    }

    // Method to get assigned data for UI
    public List<Document> getTeamsWithPlayers() {
        return teamCollection.find().into(new ArrayList<>());
    }
}


