package ru.sadikov.springcourse.Config.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sadikov.springcourse.Config.DAO.PersonDAO;
import ru.sadikov.springcourse.Config.Models.Person;
import ru.sadikov.springcourse.Config.utli.PersonValidator;

import java.sql.SQLException;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final PersonValidator personValidator;
    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }
    // получение всех людей из DAO


    @GetMapping()
    public String index(Model model) throws SQLException {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }
    // получение человека по Id
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model) throws SQLException {
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "people/new";
    }

    // create person
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) throws SQLException {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) throws SQLException {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid  Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) throws SQLException {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) throws SQLException {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
