package ru.sadikov.springcourse.Config.DAO;

import org.springframework.stereotype.Component;
import ru.sadikov.springcourse.Config.Models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private List<Person> people;
    private static int count = 0;

    {
        people = new ArrayList<Person>();
        people.add(new Person(++count, "Vitaly", 24, "vitaly@ya.ru"));
        people.add(new Person(++count, "Katya", 18, "fedulova@mail.ru"));
        people.add(new Person(++count, "Andrey", 14, "nagibator2006@mail.ru"));
        people.add(new Person(++count, "Michael", 45, "dedok@yandex.ru"));
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id) {
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person) {
        person.setId(++count);
        people.add(person);
    }

    public void update(int id, Person person) {
        Person personTobeUpdated = show(id);
        personTobeUpdated.setName(person.getName());
        personTobeUpdated.setAge(person.getAge());
        personTobeUpdated.setEmail(person.getEmail());
    }

    public void delete(int id) {
        people.removeIf(person -> person.getId() == id);
    }
}
