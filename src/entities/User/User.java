package src.entities.User;

// Define an abstract class for users
public abstract class User {
    // Private member variables for user details
    private String userID;
    private String username;
    private String password;
    private String role;

    // Constructor to initialize user details
    public User(String userID, String username, String password, String role) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getter method to retrieve the username
    public String getUsername() {
        return username;
    }

    // Setter method to set the username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter method to retrieve the password
    public String getPassword() {
        return password;
    }

    // Setter method to set the password
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter method to retrieve the user's role
    public String getRole() {
        return role;
    }

    // Setter method to set the user's role
    public void setRole(String role) {
        this.role = role;
    }

    // Getter method to retrieve the user's unique ID
    public String getUserID() {
        return userID;
    }

    // Setter method to set the user's unique ID
    public void setUserID(String userID) {
        this.userID = userID;
    }

    // Override the toString method to provide a string representation of the user
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
