import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;
import com.sun.net.httpserver.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class Main {
    public static void main(String[] args) {
        try {
            int port = 5000; // Server port
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/career", new CareerHandler()); // API endpoint
            server.setExecutor(null); // Default executor
            server.start();
            System.out.println("✅ AI Career Guidance System Started on port " + port);
        } catch (IOException e) {
            System.out.println("⚠️ Error starting server: " + e.getMessage());
        }
    }
}

// Handles incoming API requests
class CareerHandler implements HttpHandler {
    private final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("📩 Request received: " + exchange.getRequestMethod());

        // Set CORS headers for frontend access
        Headers headers = exchange.getResponseHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        headers.set("Access-Control-Allow-Headers", "Content-Type");

        // Handle preflight (OPTIONS) request
        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(200, -1);
            return;
        }

        // Allow only POST requests
        if (!"POST".equals(exchange.getRequestMethod())) {
            sendErrorResponse(exchange, 405, "Only POST method is allowed");
            return;
        }

        // Read and validate request body
        String requestBody = readRequestBody(exchange);
        if (requestBody.isEmpty()) {
            sendErrorResponse(exchange, 400, "Request body is empty");
            return;
        }

        // Parse JSON request
        Type type = new TypeToken<Map<String, List<String>>>() {}.getType();
        Map<String, List<String>> requestMap;
        try {
            requestMap = gson.fromJson(requestBody, type);
        } catch (Exception e) {
            sendErrorResponse(exchange, 400, "Invalid JSON format");
            return;
        }

        // Extract skills
        if (requestMap == null || !requestMap.containsKey("skills")) {
            sendErrorResponse(exchange, 400, "Missing 'skills' field in JSON");
            return;
        }

        List<String> userSkills = requestMap.get("skills");
        List<String> careers = getCareerRecommendations(userSkills); // Get career recommendations

        // Send response
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("careers", careers);
        sendJsonResponse(exchange, 200, responseMap);
    }

    // Reads the request body
    private String readRequestBody(HttpExchange exchange) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }
        return requestBody.toString().trim();
    }

    // Fetch careers from the database based on user skills
    private List<String> getCareerRecommendations(List<String> userSkills) {
        Set<String> careers = new HashSet<>(); // Store unique careers
        String dbUrl = "jdbc:mysql://localhost:3306/career_db";
        String dbUser = "root";
        String dbPassword = "root";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String sql = "SELECT career, skills_required FROM careers";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    String career = rs.getString("career");
                    String[] requiredSkills = rs.getString("skills_required").toLowerCase().split(", ");
                    
                    // Check if any required skill matches user skills
                    for (String skill : userSkills) {
                        for (String requiredSkill : requiredSkills) {
                            if (requiredSkill.equalsIgnoreCase(skill.toLowerCase())) {
                                careers.add(career);
                                break;
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("⚠️ Database error: " + e.getMessage());
        }
        return new ArrayList<>(careers); // Convert to list before returning
    }

    // Sends JSON response
    private void sendJsonResponse(HttpExchange exchange, int statusCode, Map<String, Object> responseMap) throws IOException {
        String response = gson.toJson(responseMap);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }

    // Sends error response in JSON format
    private void sendErrorResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", message);
        sendJsonResponse(exchange, statusCode, errorResponse);
    }
}
