package edu.ku.comp306.ecommerce.controller;

import edu.ku.comp306.ecommerce.dto.LoginDTO;
import edu.ku.comp306.ecommerce.entity.User;
import edu.ku.comp306.ecommerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LoginSignupController {

    private final UserService userService;

    @GetMapping("/loginSignup")
    public String loginSignupPage(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        model.addAttribute("user", new User());
        model.addAttribute("errorMessage", null);
        return "loginSignup";
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute LoginDTO loginDTO, Model model, HttpSession session) {
        User user = userService.authenticateUser(loginDTO.getUsername(), loginDTO.getPassword());
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/homepage?userID=" + user.getUserId();
        } else {
            model.addAttribute("loginDTO", loginDTO); // Re-add loginDTO to the model
            model.addAttribute("user", new User());   // Ensure user object is present for the signup form
            model.addAttribute("errorMessage", "Invalid username or password.");
            return "loginSignup"; // Return the login/signup page with the error message
        }
    }


    @PostMapping("/signup")
    public String handleSignup(@ModelAttribute User user, Model model) {
        if (userService.isEmailTaken(user.getEmail())) {
            model.addAttribute("loginDTO", new LoginDTO()); // Ensure loginDTO is present
            model.addAttribute("user", user);              // Retain the user data in the form
            model.addAttribute("errorMessage", "Email is already registered.");
            return "loginSignup"; // Return the login/signup page with the error message
        }
        userService.registerUser(user);
        return "redirect:/homepage?userID=" + user.getUserId();
    }


    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginSignup";
    }
}
