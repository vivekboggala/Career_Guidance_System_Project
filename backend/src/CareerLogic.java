import java.sql.*;
import java.util.*;

public class CareerLogic {
    public static List<String> getCareerList(String skills) {
        List<String> careers = new ArrayList<>();
        
        // Get database connection
        Connection conn = DBHelper.getConnection();
        if (conn == null) {
            System.out.println("⚠️ Failed to connect to the database.");
            return careers;
        }

        // Split skills into an array
        String[] skillArray = skills.split(",\\s*");

        // Build dynamic SQL query for searching careers
        StringBuilder queryBuilder = new StringBuilder("SELECT career FROM careers WHERE ");
        for (int i = 0; i < skillArray.length; i++) {
            if (i > 0) queryBuilder.append(" OR ");
            queryBuilder.append("skills_required LIKE ?");
        }

        String query = queryBuilder.toString();
        System.out.println("🔍 Executing Query: " + query); // Log the generated query

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set query parameters dynamically
            for (int i = 0; i < skillArray.length; i++) {
                stmt.setString(i + 1, "%" + skillArray[i] + "%");
            }

            System.out.println("📜 Skills received: " + skills);
            ResultSet rs = stmt.executeQuery();

            // Store career results
            while (rs.next()) {
                String career = rs.getString("career");
                System.out.println("✅ Career found: " + career);
                careers.add(career);
            }

            // Handle case where no careers are found
            if (careers.isEmpty()) {
                System.out.println("⚠️ No careers found for skills: " + skills);
            }

        } catch (SQLException e) {
            System.out.println("❌ SQL Error: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close(); // Ensure the connection is closed
            } catch (SQLException e) {
                System.out.println("⚠️ Error closing connection: " + e.getMessage());
            }
        }
        return careers;
    }
}
