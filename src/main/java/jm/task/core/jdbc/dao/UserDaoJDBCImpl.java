package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }
    private final Connection connection = Util.getConnection();

    public void createUsersTable() {
        String sql = "CREATE TABLE Users" + "(ID INTEGER NOT NULL AUTO_INCREMENT," + "Name VARCHAR(255),"
                + "LastName VARCHAR(255)," + "Age TINYINT," + "PRIMARY KEY (ID))";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Table already exists");
        }
    }

    public void dropUsersTable() {
        try(Statement statement = connection.createStatement()) {
            String sql = "DROP TABLE mydbtest.`users`";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Can't delete table, table does not exist");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO mydbtest.`users` (Name, LastName, Age) VALUES (?, ?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM mydbtest.`users` WHERE ID=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT ID, Name, LastName, Age FROM mydbtest.`users`";
        try(Statement statement = connection.createStatement(); ResultSet set = statement.executeQuery(sql)) {
            while (set.next()) {
                User user = new User();
                user.setId(set.getLong("ID"));
                user.setName(set.getString("Name"));
                user.setLastName(set.getString("LastName"));
                user.setAge(set.getByte("Age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM mydbtest.`users`";
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
