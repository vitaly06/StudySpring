package ru.sadikov.springcourse.Config.Mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.sadikov.springcourse.Config.Models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(resultSet.getInt("userId"));
        person.setName(resultSet.getString("name"));
        person.setAge(resultSet.getInt("age"));
        person.setEmail(resultSet.getString("email"));
        person.setAddress(resultSet.getString("address"));
        return person;
    }
}
