package GroupAssignment.Interface;

import GroupAssignment.FilePaths.FilePaths;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public interface Account {
    public static final String accountFilePath = FilePaths.accountFilePath;
    public static boolean authenticateUser(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(accountFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 3) {
                    String storedUsername = parts[0];
                    String storedPassword = parts[1];

                    if (storedUsername.equals(username) && storedPassword.equals(password)) {
                        // Match found, return true
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // No match found, return false
        return false;
    }
}
