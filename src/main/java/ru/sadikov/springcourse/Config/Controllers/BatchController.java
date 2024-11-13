package ru.sadikov.springcourse.Config.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sadikov.springcourse.Config.DAO.PersonDAO;

@Controller
@RequestMapping("/test-batch")
public class BatchController {
    private final PersonDAO personDAO;

    public BatchController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(){
        return "batch/index";
    }

    @GetMapping("/without")
    public String withoutBatch(){
        personDAO.testMultipleUpdate();
        return "redirct:/people";
    }

    @GetMapping("/with")
    public String withBatch(){
        personDAO.testBatchUpdate();
        return "redirct:/people";
    }
}
