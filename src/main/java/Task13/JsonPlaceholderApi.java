package Task13;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonPlaceholderApi {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    private static String sendRequest(String endpoint, String requestMethod, String requestBody) {
        try {
            URL url = new URL(BASE_URL + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            if (requestBody != null && !requestBody.isEmpty()) {
                try (OutputStream os = connection.getOutputStream();
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"))) {
                    writer.write(requestBody);
                    writer.flush();
                }
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
            }

            connection.disconnect();
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ---Task 1---
    public static String createUser(String requestBody) {
        return sendRequest("/users", "POST", requestBody);
    }

    public static String updateUser(int userId, String updatedUserJson) throws IOException {
        return sendRequest("/users/" + userId, "PUT", updatedUserJson);
    }

    public static int deleteUser(int userId) throws IOException {
        URL url = new URL(BASE_URL + "/users/" + userId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        return connection.getResponseCode();
    }

    public static String getAllUsers() {
        return sendRequest("/users", "GET", null);
    }

    public static String getUserById(int userId) {
        return sendRequest("/users/" + userId, "GET", null);
    }

    public static String getUserByUsername(String username) {
        return sendRequest("/users?username=" + username, "GET", null);
    }

    // ---Task 2---
    public static void getCommentsForLatestPostOfUser(int userId) {
        String postsEndpoint = "/users/" + userId + "/posts";
        String postsResponse = sendRequest(postsEndpoint, "GET", null);

        int latestPostId = -1;
        if (postsResponse != null) {
            JSONArray postsArray = new JSONArray(postsResponse);
            if (postsArray.length() > 0) {
                JSONObject latestPost = postsArray.getJSONObject(postsArray.length() - 1);
                latestPostId = latestPost.getInt("id");
            }
        }
        if (latestPostId != -1) {
            String commentsEndpoint = "/posts/" + latestPostId + "/comments";
            String commentsResponse = sendRequest(commentsEndpoint, "GET", null);


            if (commentsResponse != null) {
                String fileName = "user-" + userId + "-post-" + latestPostId + "-comments.json";
                try (PrintWriter writer = new PrintWriter(fileName)) {
                    writer.write(commentsResponse);
                    System.out.println("Comments saved to file: " + fileName);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ---Task 3---
    public static String getOpenTasksForUser(int userId) {
        StringBuilder result = new StringBuilder();
        String tasksEndpoint = "/users/" + userId + "/todos";
        String tasksResponse = sendRequest(tasksEndpoint, "GET", null);

        if (tasksResponse != null) {
            JSONArray tasksArray = new JSONArray(tasksResponse);
            result.append("Open tasks for user ").append(userId).append(" (not completed):\n");

            for (int i = 0; i < tasksArray.length(); i++) {
                JSONObject task = tasksArray.getJSONObject(i);
                if (!task.getBoolean("completed")) {
                    result.append("Task ID: ").append(task.getInt("id")).append(", Title: ").append(task.getString("title")).append("\n");
                }
            }
        }

        return result.toString();
    }


}