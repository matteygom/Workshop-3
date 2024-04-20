package pl.coderslab.utils;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDao {


    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";

    private static final String READ_USER_QUERY =
            "SELECT * FROM users WHERE id = (?)";

    private static final String UPDATE_USER_QUERY =
            "UPDATE users SET userName = (?), email = (?), password = (?) WHERE id = (?);";

    private static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE id = (?);";

    private static final String READ_ALL_USERS =
            "SELECT * FROM users";


    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    public User create(User user) {

        try (Connection connection = DbUtil.getConnection()) {

            PreparedStatement statement =
                    connection.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;

        } catch (SQLException exception) {

            exception.printStackTrace();
            return null;
        }
    }

    public User read(int userId) {

        try (Connection connection = DbUtil.getConnection()) {

            User user = new User();
            PreparedStatement statement = connection.prepareStatement(READ_USER_QUERY);

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));

                return user;
            }

        } catch (SQLException exception) {

            exception.printStackTrace();
        }
        return null;
    }

    public void update(User user) {

        try (Connection connection = DbUtil.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(UPDATE_USER_QUERY);

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.setInt(4, user.getId());

            statement.executeUpdate();


        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void delete(int userId) {

        try (Connection connection = DbUtil.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(DELETE_USER_QUERY);

            statement.setInt(1, userId);

            statement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }


        public List<User> findAll() {
            List<User> userList = new ArrayList<>();
            try(Connection connection = DbUtil.getConnection()){
                PreparedStatement statement = connection.prepareStatement(READ_ALL_USERS);
                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()){
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setUserName(resultSet.getString("username"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    userList.add(user);
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
            return userList;
        }

    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1); // Tworzymy kopię tablicy powiększoną o 1.
        tmpUsers[users.length] = u; // Dodajemy obiekt na ostatniej pozycji.
        return tmpUsers;

    }
}
