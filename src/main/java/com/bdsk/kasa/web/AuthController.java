package com.bdsk.kasa.web;

import com.bdsk.kasa.domain.User;
import com.bdsk.kasa.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public ModelAndView acceptRegistrationForm(@ModelAttribute("user") User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return new ModelAndView("registration", "error", "The user with that username already exists!");
        }
        userRepository.save(user);
        return new ModelAndView("home", "user", user);
    }
}
