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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/startquiz")
    public ModelAndView quizView(HttpSession session) {

        if(session.getAttribute("user") != null) {



            QuizCollection quizCollection = new QuizCollection();
            session.setAttribute("qq", quizCollection);

            int max = quizRepository.getQuestionSize();
            Random rand = new Random();

            List<Integer> list = new ArrayList<>();
            while(list.size() < 5) {
                int number = rand.nextInt(max);
                if(!list.contains(number)) {
                    list.add(number);
                }
            }
            for(int i : list) {
                quizCollection.addQuestion(quizRepository.getQuestion(i));
            }
//                    QuizQuestion qq = quizRepository.getQuestion(1);
//            quizCollection.addQuestion(quizRepository.getQuestion(1));
//            quizCollection.addQuestion(quizRepository.getQuestion(2));
//            quizCollection.addQuestion(quizRepository.getQuestion(3));
//            quizCollection.addQuestion(quizRepository.getQuestion(4));


            return new ModelAndView("quiz")
                    .addObject("qq", quizCollection);

        }  else {
            return new ModelAndView("redirect:/login");
        }
    }
    @GetMapping("/quiz")
    public ModelAndView quiz(HttpSession session) {

        if(session.getAttribute("user") != null) {



            QuizCollection qq = (QuizCollection) session.getAttribute("qq");

            if(qq.getCurrentQuestion() == qq.getSize())
                return new ModelAndView("result")
                        .addObject("qq", qq);
            else
                return new ModelAndView("quiz")
                    .addObject("qq", qq);

        }  else {
            return new ModelAndView("redirect:/login");
        }
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
    public ModelAndView menu(HttpSession session) {

        if(session.getAttribute("user") != null)
            return new ModelAndView("menu");
        else
            return new ModelAndView("redirect:/login");
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
