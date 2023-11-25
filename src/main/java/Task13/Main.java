package Task13;

import java.io.IOException;

import static Task13.JsonPlaceholderApi.*;

public class Main {


    public static void main(String[] args) throws IOException {

        System.out.println("TASK 1");

        // Task 1.1 Create new user
        String newUserData = "{\"ExanpleName\": \"John Examole\", \"username\": \"example\", \"email\": \"john@example.com\"}";
        String createdUserResponse = createUser(newUserData);
        System.out.println("Created user response: " + createdUserResponse);

        // Task 1.2 Update user by id.
        int userIdToUpdate = 1;
        String updatedUserJson = "{\"name\": \"Updated Name\", \"username\": \"updatedusername\", \"email\": \"updated@example.com\"}";
        String updatedUser = updateUser(userIdToUpdate, updatedUserJson);
        System.out.println("Updated user: " + updatedUser);

        // Task 1.3 Delete user by id
        int userIdToDelete = 3;
        int deletionResponse = deleteUser(userIdToDelete);
        System.out.println("Deletion response code: " + deletionResponse);

        // Task 1.4  Getting all users information
        String allUsersInfo = getAllUsers();
        System.out.println("All users: " + allUsersInfo);

        // Task 1.5 Getting information about user by id
        int userIdInfo = 1;
        String userInfoById = getUserById(userIdInfo);
        System.out.println("User info by id " + userIdInfo + ": " + userInfoById);

        // Task 1.6 Getting information about user by username
        String username = "Bret";
        String userInfoByUsername = getUserByUsername(username);
        System.out.println("User info by username " + username + ": " + userInfoByUsername);


        // Task 2 Getting comments and writing to a file
        System.out.println("\nTASK 2");
        getCommentsForLatestPostOfUser(3);


        // Task 3: Getting open tasks for a user
        System.out.println("\nTASK 3");
        int userId = 1;
        String openTasksForUser = getOpenTasksForUser(userId);
        System.out.println("Open tasks for user " + userId + ": " + openTasksForUser);
    }

}