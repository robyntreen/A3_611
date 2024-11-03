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
        displayInitialState();  // Display initial state of players and teams
    }

    private void initializeUI() {
        setTitle("Player-Team Assignment");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton displayInitialStateButton = new JButton("Show Initial State");
        displayInitialStateButton.addActionListener(e -> displayInitialState());

        JButton displayAssignmentsButton = new JButton("Show Assignments");
        displayAssignmentsButton.addActionListener(e -> displayAssignments());

        JButton refreshButton = new JButton("Refresh Assignments");
        refreshButton.addActionListener(e -> {
            assignmentManager.assignPlayers();  // Perform the assignment
            displayAssignments();  // Show updated assignment
        });

        buttonPanel.add(displayInitialStateButton);
        buttonPanel.add(displayAssignmentsButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void displayInitialState() {
        displayArea.setText("");  // Clear existing text

        List<Document> players = assignmentManager.getPlayers();
        List<Document> teams = assignmentManager.getTeams();

        StringBuilder sb = new StringBuilder();
        sb.append("Initial State of Players and Teams:\n\n");

        sb.append("Teams:\n");
        for (Document team : teams) {
            sb.append("Team: ").append(team.getString("name")).append("\n");
            sb.append("Manager: ").append(team.getString("managerFirstName"))
                    .append(" ").append(team.getString("managerLastName")).append("\n");
            sb.append("Players Assigned: None\n\n");  // No players assigned initially
        }

        sb.append("Players:\n");
        for (Document player : players) {
            sb.append("Player: ").append(player.getString("firstName"))
                    .append(" ").append(player.getString("lastName")).append("\n");
            sb.append("Positions: ").append(player.getList("positions", String.class)).append("\n");
            sb.append("Team Preferences: ").append(player.getList("teamPreferences", String.class)).append("\n\n");
        }

        displayArea.setText(sb.toString());
    }

    private void displayAssignments() {
        displayArea.setText("");  // Clear existing text

        List<Document> teams = assignmentManager.getTeamsWithPlayers();

        StringBuilder sb = new StringBuilder();
        sb.append("Assigned Teams and Players:\n\n");

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

                sb.append("  - Player: ").append(playerName)
                        .append(" | Points: ").append(points).append("\n");
                sb.append("    Preferences: ").append(preferences).append("\n");

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
