package com.example.sample1app;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.example.sample1app.repositories.PersonRepository;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class HelloController {

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonDAOPersonImpl dao;

    @GetMapping("/find")
    public ModelAndView index(ModelAndView mav) {
        mav.setViewName("find");
        mav.addObject("msg", "Personのサンプルです");
        Iterable<Person> list = dao.getAll();
        mav.addObject("data", list);
        return mav;
    }

    @PostMapping("/find")
    public ModelAndView search(HttpServletRequest request, ModelAndView mav) {
        mav.setViewName("find");
        String param = request.getParameter("find_str");

        if (param.isEmpty()) {
            mav = new ModelAndView("redirect:/find");
        } else {
            mav.addObject("title", "Find result");
            mav.addObject("msg", "[" + param + "]の検索結果");
            mav.addObject("value", param);

            List<Person> list = dao.find(param);
            mav.addObject("data", list);
        }
        return mav;
    }

    public String postMethodName(@RequestBody String entity) {
        return entity;
    }

    @GetMapping("/")
    public ModelAndView index(@ModelAttribute("formModel") Person person, ModelAndView mav) {
        mav.setViewName("index");
        mav.addObject("title", "Hello page");
        mav.addObject("msg", "This is JPA sample data");

        List<Person> list = repository.findAllOrderById();
        mav.addObject("data", list);
        return mav;
    }

    @PostMapping("/")
    @Transactional
    public ModelAndView form(@ModelAttribute("formModel") @Validated Person person, BindingResult result,
            ModelAndView mav) {
        ModelAndView res = null;
        if (!result.hasErrors()) {
            repository.saveAndFlush(person);
            res = new ModelAndView("redirect:/");
        } else {
            mav.setViewName("index");
            mav.addObject("title", "Hello page");
            mav.addObject("msg", "Sorry, error is occurred...");
            List<Person> list = repository.findAll();
            mav.addObject("data", list);
            res = mav;
        }
        return res;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@ModelAttribute Person person, @PathVariable int id, ModelAndView mav) {
        mav.setViewName("edit");
        mav.addObject("title", "Edit Person");
        Optional<Person> data = repository.findById((long) id);
        mav.addObject("formModel", data.get());
        return mav;
    }

    @PostMapping("/edit")
    @Transactional
    public ModelAndView update(@ModelAttribute Person person, ModelAndView mav) {
        repository.saveAndFlush(person);
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable int id, ModelAndView mav) {
        mav.setViewName("delete");
        mav.addObject("title", "Delete Person");
        mav.addObject("msg", "Can I delete this record?");
        Optional<Person> data = repository.findById((long) id);
        mav.addObject("formModel", data.get());
        return mav;
    }

    @PostMapping("/delete")
    @Transactional
    public ModelAndView remove(@RequestParam long id, ModelAndView mav) {
        repository.deleteById(id);
        return new ModelAndView("redirect:/");
    }

    @PostConstruct
    public void init() {
        Person p1 = new Person();
        p1.setName("taro");
        p1.setAge(39);
        p1.setMail("taro@yamada");
        p1.setMemo("ダミー１");
        repository.saveAndFlush(p1);

        Person p2 = new Person();
        p2.setName("hanako");
        p2.setAge(28);
        p2.setMail("hanako@flower");
        p2.setMemo("ダミー2");
        repository.saveAndFlush(p2);

        Person p3 = new Person();
        p3.setName("sachiko");
        p3.setAge(17);
        p3.setMail("sachiko@happy");
        p3.setMemo("ダミー3");
        repository.saveAndFlush(p3);
    }
}
