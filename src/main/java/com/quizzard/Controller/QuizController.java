package com.quizzard.Controller;

import com.quizzard.Repository.QuizRepository;
import com.quizzard.domain.QuizCollection;
import com.quizzard.domain.QuizQuestion;
import com.quizzard.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping("/guess/{number}")
    public ModelAndView guess(HttpSession session, @PathVariable int number) {
        System.out.println("ans: " + number);
        QuizCollection qq = (QuizCollection) session.getAttribute("qq");



        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/startquiz")
    public ModelAndView quizView(HttpSession session) {
        System.out.println(quizRepository.getQuestionSize());
        QuizQuestion qq = quizRepository.getQuestion(1);
        System.out.println(qq.toString());
        QuizCollection quizCollection = new QuizCollection();
        session.setAttribute("qq", quizCollection);
        quizCollection.addQuestion(quizRepository.getQuestion(1));
        quizCollection.addQuestion(quizRepository.getQuestion(2));
        quizCollection.addQuestion(quizRepository.getQuestion(3));
        quizCollection.addQuestion(quizRepository.getQuestion(4));


        return new ModelAndView("quiz")
                .addObject("qq", quizCollection);
//                .addObject("question", qq.getText())
//                .addObject("alt1", qq.getOptionOne())
//                .addObject("alt2", qq.getOptionTwo())
//                .addObject("alt3", qq.getOptionThree())
//                .addObject("alt4", qq.getOptionFour());
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
