package ru.sadikov.springcourse.Config.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sadikov.springcourse.Config.DAO.PersonDAO;
import ru.sadikov.springcourse.Config.Models.Person;

import java.sql.SQLException;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final PersonDAO personDAO;
    @Autowired
    public AdminController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String adminPage(Model model, @ModelAttribute("person") Person person) throws SQLException {
        model.addAttribute("people", personDAO.index());
        return "admin/adminPage";
    }

    @PostMapping("/add")
    public String addAdmin(@ModelAttribute("person") Person person) {
        System.out.println(person.getId());
        return "redirect:/admin";
    }
}
