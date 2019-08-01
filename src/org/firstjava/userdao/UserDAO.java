package org.firstjava.userdao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.firstjava.bean.User;


public class UserDAO {
    
    private String jdbcURL = "jdbc:mysql://localhost:3306/demo";
    private String jdbcUsername = "root";
    private String jdbcPassword = "mysql";

    private static final String INSERT_USERS_SQL = "INSERT INTO users" + "  (name, email, country) VALUES " + " (?, ?, ?);";
    private static final String SELECT_ALL_USERS = "select * from users";

    public UserDAO() {}

    protected Connection getConnection() {
        
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return conn;
    }
    
    public void insertUser(User user) throws SQLException {
        System.out.println(INSERT_USERS_SQL);
        // try-with-resource statement will auto close the connection.
        // Statement stmt = conn.createStatement();

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);

        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getEmail());
        preparedStatement.setString(3, user.getCountry());
        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();
    }
    
    public List<User> selectAllUsers() {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List<User> users = new ArrayList<User>();
        // Step 1: Establishing a Connection
        Connection connection = getConnection();

                        // Step 2:Create a statement using connection object
                PreparedStatement preparedStatement;
                try {
                    preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
                    System.out.println(preparedStatement);
                    // Step 3: Execute the query or update query
                    ResultSet rs = preparedStatement.executeQuery();

                    // Step 4: Process the ResultSet object.
                    while (rs.next()) {
                            int id = rs.getInt("id");
                            String name = rs.getString("name");
                            String email = rs.getString("email");
                            String country = rs.getString("country");
                            users.add(new User(id, name, email, country));}
                    
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
               
        return users;
}

    
}
