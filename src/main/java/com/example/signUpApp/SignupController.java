package com.example.signUpApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SignupController {

    @Autowired
    private UserRepository userRepository;

    // Signup form dikhane ke liye
    @GetMapping("/signup")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    // Form submit karne ke baad
    @PostMapping("/signup")
    public String submitForm(@ModelAttribute("user") User user, Model model) {
        // Pehle check karo ki email already registered hai ya nahi
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            model.addAttribute("errorMessage", "This email is already registerd . please try another email");
            return "signup"; // Wahi signup page dikhao with error
        }

        try {
            // Save karne ki koshish karo
            userRepository.save(user);
        } catch (Exception e) {
            // Agar database level pe bhi duplicate aaya (UNIQUE constraint violation)
            model.addAttribute("errorMessage", "This email is already registerd . please try another email");
            return "signup";
        }

        return "success";
    }
}
