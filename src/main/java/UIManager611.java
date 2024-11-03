// PlayerTeamUI.java

import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UIManager611 extends JFrame {
    private AssignmentManager611 assignmentManager;
    private JTextArea displayArea;

    public UIManager611(AssignmentManager611 assignmentManager) {
        this.assignmentManager = assignmentManager;
        initializeUI();
        displayAssignments();
    }

    private void initializeUI() {
        setTitle("Player-Team Assignment");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh Assignments");
        refreshButton.addActionListener(e -> displayAssignments());
        add(refreshButton, BorderLayout.SOUTH);
    }

    private void displayAssignments() {
        displayArea.setText("");  // Clear existing text
        assignmentManager.assignPlayers();  // Perform assignment

        List<Document> teams = assignmentManager.getTeamsWithPlayers();

        StringBuilder sb = new StringBuilder();
        int totalPoints = 0;

        for (Document team : teams) {
            sb.append("Team: ").append(team.getString("name")).append("\n");
            sb.append("Manager: ").append(team.getString("managerFirstName"))
                    .append(" ").append(team.getString("managerLastName")).append("\n");

            List<Document> players = team.getList("players", Document.class);
            int teamPoints = 0;

            for (Document player : players) {
                String playerName = player.getString("firstName") + " " + player.getString("lastName");
                List<String> preferences = player.getList("teamPreferences", String.class);
                int points = calculatePoints(team.getString("name"), preferences);

                sb.append("  - Player: ").append(playerName).append(" | Points: ").append(points).append("\n");
                teamPoints += points;
            }
            sb.append("Team Points: ").append(teamPoints).append("\n\n");
            totalPoints += teamPoints;
        }

        sb.append("Total Points Across All Teams: ").append(totalPoints).append("\n");
        displayArea.setText(sb.toString());
    }

    private int calculatePoints(String teamName, List<String> preferences) {
        if (preferences.isEmpty()) return -1;
        if (preferences.get(0).equals(teamName)) return 2;
        if (preferences.size() > 1 && preferences.get(1).equals(teamName)) return 1;
        return -1;
    }

    public static void main(String[] args) {
        MongoDatabase database = DatabaseConnection611.getDatabase();
        AssignmentManager611 assignmentManager = new AssignmentManager611(database);

        SwingUtilities.invokeLater(() -> {
            new UIManager611(assignmentManager).setVisible(true);
        });
    }
}
