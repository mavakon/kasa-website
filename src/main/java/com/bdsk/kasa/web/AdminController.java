package com.bdsk.kasa.web;

import com.bdsk.kasa.domain.User;
import com.bdsk.kasa.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository){
        this.userRepository = userRepository;

    }

    @GetMapping("/")
    public String adminPanel(){
        return "admin";
    }

    @PostMapping("/user-delete/{id}")
    public String userDelete(@PathVariable int id, Model model) {
        if (!userRepository.deleteById(id)) {
            model.addAttribute("error", "Не знайдено користувача з ID" + id);
            return "redirect:/admin";
        }
        model.addAttribute("success", "Видалено користувача з ID: " + id);
        return "redirect:/admin";
    }

    @PostMapping("/user-to-seller/{id}")
    public String userToSeller(@PathVariable int id, Model model) {
        Optional<User> possiblyUser = userRepository.findById(id);
        if (possiblyUser.isEmpty()) {
            model.addAttribute("error", "Не знайдено користувача з ID" + id);
            return "redirect:/admin";
        }
        User user = possiblyUser.get();
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        roles.add("ROLE_SELLER");
        user.setRoles(roles);
        userRepository.save(user);
        model.addAttribute("success", "Змінені права користувача з ID: " + id);
        return "redirect:/admin";
    }

    @PostMapping("/seller-to-user/{id}")
    public String sellerToUser(@PathVariable int id, Model model) {
        Optional<User> possiblyUser = userRepository.findById(id);
        if (possiblyUser.isEmpty()) {
            model.addAttribute("error", "Не знайдено користувача з ID" + id);
            return "redirect:/admin";
        }
        User user = possiblyUser.get();
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        user.setRoles(roles);
        userRepository.save(user);
        model.addAttribute("success", "Змінені права користувача з ID: " + id);
        return "redirect:/admin";
    }

}
