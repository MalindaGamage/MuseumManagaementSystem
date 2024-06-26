package service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import dao.UserDAO;
import model.User;
import java.util.UUID;

public class UserService {
    /**
     * Registers a new user with hashed password.
     * @param username Username of the new user.
     * @param password Plain text password to be hashed.
     * @param role The role of the new user (e.g., "admin", "visitor").
     * @param email Email address of the new user.
     */
    public static void registerUser(String username, String password, String role, String email, boolean isVisitor) {
        UserDAO userDAO = new UserDAO();
        try {
            String hashedPassword = hashPassword(password);
            UUID userId = UUID.randomUUID();
            User newUser = new User(userId, username, hashedPassword, role, email, isVisitor);
            userDAO.addUser(newUser);
        } catch (Exception e) {
            System.err.println("Error registering user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Hashes a plain text password using BCrypt.
     * @param password Plain text password.
     * @return A BCrypt hashed password.
     */
    private static String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    /**
     * Verifies a given plain text password against a hashed password.
     * @param password Plain text password.
     * @param hashedPassword BCrypt hashed password.
     * @return true if the password matches, false otherwise.
     */
    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword.toCharArray()).verified;
    }

    /**
     * Authenticates a user by verifying the username and password.
     * @param username Username.
     * @param password Plain text password.
     * @return User object if authentication is successful, null otherwise.
     */
    public static User authenticateUser(String username, String password) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByUsername(username);
        if (user != null && checkPassword(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    /**
     * Updates an existing user's details.
     * @param username Username of the user to update.
     * @param password Plain text password to be hashed.
     * @param role The role of the user (e.g., "admin", "visitor").
     * @param email Email address of the user.
     * @param isVisitor Whether the user is a visitor.
     */
    public static void updateUser(String username, String password, String role, String email, boolean isVisitor) throws Exception {
        UserDAO userDAO = new UserDAO();
        User existingUser = userDAO.getUserByUsername(username);
        if (existingUser != null) {
            String hashedPassword = hashPassword(password);
            existingUser.setPassword(hashedPassword);
            existingUser.setRole(role);
            existingUser.setEmail(email);
            existingUser.setVisitor(isVisitor);
            userDAO.updateUser(existingUser);
        } else {
            throw new Exception("User not found.");
        }
    }
}
