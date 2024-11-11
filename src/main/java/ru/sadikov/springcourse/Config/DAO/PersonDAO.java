package ru.sadikov.springcourse.Config.DAO;

import org.springframework.stereotype.Component;
import ru.sadikov.springcourse.Config.Models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int count = 0;

    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    private static Connection connection;
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Person> index() throws SQLException {
        List<Person> people = new ArrayList<>();

        Statement statement = connection.createStatement();
        String SQL = "SELECT * FROM person";
        ResultSet resultSet = statement.executeQuery(SQL);
        while (resultSet.next()) {
            Person person = new Person();
            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));
            people.add(person);
        }
        return people;
    }

    public Person show(int id) {
        /*
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
        */
        return null;
    }

    public void save(Person person) throws SQLException {
        Statement statement = connection.createStatement();
        String SQL = "INSERT INTO person (id, name, age, email) VALUES (" + 1 + ", '" + person.getName() + "', "
                + person.getAge() + ", '" + person.getEmail() + "')";
        statement.executeUpdate(SQL);

    }

    /*public void update(int id, Person person) {
        Person personTobeUpdated = show(id);
        personTobeUpdated.setName(person.getName());
        personTobeUpdated.setAge(person.getAge());
        personTobeUpdated.setEmail(person.getEmail());
    }*/

    public void delete(int id) {
        //people.removeIf(person -> person.getId() == id);
    }
}
