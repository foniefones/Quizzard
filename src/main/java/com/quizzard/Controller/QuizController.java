package com.quizzard.Controller;

import com.quizzard.Repository.QuizRepository;
import com.quizzard.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @GetMapping("/login")
    public ModelAndView loginView() {
        return new ModelAndView("login");
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register");
    }

    @PostMapping("/register")
    public ModelAndView newAccount(String name, String password, String email) {
        if(quizRepository.userExists(name, email) || name.isEmpty() || email.isEmpty() || password.isEmpty()){
            System.out.println("user already exists");
            return new ModelAndView("register").addObject("errormsg", "Fel vid registrering");
        } else {
            quizRepository.createNewUser(name, password, email);
            System.out.println("new user created");
            return new ModelAndView("redirect:/menu");
        }
    }

    @GetMapping("/menu")
    public ModelAndView menu() {
        return new ModelAndView("menu");
    }

    @PostMapping("/userlogin")
    public RedirectView login(HttpSession session, String name, String password) {
       User user =  quizRepository.login(name, password);
       if(user != null) {
           session.setAttribute("user", user);
           return new RedirectView("menu");
       } else {
           return new RedirectView("login");
       }
    }
}
