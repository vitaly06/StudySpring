package ru.sadikov.springcourse.Config.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.sadikov.springcourse.Config.Mappers.PersonMapper;
import ru.sadikov.springcourse.Config.Models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    };


    public List<Person> index() throws SQLException {
        return jdbcTemplate.query("SELECT * FROM Person", new PersonMapper());
    }

    public Person show(int id) throws SQLException {
        return jdbcTemplate.query("SELECT * FROM Person WHERE userId = ?", new Object[]{id},
                new PersonMapper()).stream().findAny().orElse(null);
    }

    public Optional<Person> show(String email){
        return jdbcTemplate.query("SELECT * FROM Person WHERE email = ?",
                new Object[]{email}, new PersonMapper()).stream().findAny();
    }

    public void save(Person person) throws SQLException {
        jdbcTemplate.update("INSERT INTO Person(name, age, email, address) VALUES( ?, ?, ?, ?)",
                person.getName(), person.getAge(), person.getEmail(), person.getAddress());

    }

    public void update(int id, Person person) throws SQLException {
        jdbcTemplate.update("UPDATE Person SET name = ?, age = ?, email = ?, address = ? WHERE id = ?",
                person.getName(), person.getAge(), person.getEmail(), person.getAddress(), id);
    }

    public void delete(int id) throws SQLException {
        jdbcTemplate.update("DELETE FROM Person WHERE id = ?", id);
    }

    /////////////////////////////////////////////////
    //Тестируем производительность пакетной вставки//
    /////////////////////////////////////////////////

    public void testMultipleUpdate(){
        List<Person> people = create1000People();
        long before = System.currentTimeMillis();
        for (Person person : people) {
            jdbcTemplate.update("INSERT INTO Person VALUES(?, ?, ?, ?)",
                    person.getId(), person.getName(), person.getAge(), person.getEmail());
        }
        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - before));
    }

    public void testBatchUpdate(){
        List<Person> people = create1000People();
        long before = System.currentTimeMillis();
        jdbcTemplate.batchUpdate("INSERT INTO person VALUES(?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, people.get(i).getId());
                        ps.setString(2, people.get(i).getName());
                        ps.setInt(3, people.get(i).getAge());
                        ps.setString(4, people.get(i).getEmail());
                    }

                    @Override
                    public int getBatchSize() {
                        return people.size();
                    }
                });
        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - before));
    }

    private List<Person> create1000People() {
        List<Person> people = new ArrayList<Person>();
        for (int i = 0; i < 1000; i++) {
            people.add(new Person(i, "Name" + i, 30, "test"+i+"@mail.ru", "fdfdfsd"));
        }
        return people;
    }
}
