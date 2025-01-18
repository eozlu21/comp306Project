package edu.ku.comp306.ecommerce.controller;

import edu.ku.comp306.ecommerce.dto.UserReviewDTO;
import edu.ku.comp306.ecommerce.entity.User;
import edu.ku.comp306.ecommerce.repository.ReviewedRepository;
import edu.ku.comp306.ecommerce.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final ReviewedRepository reviewedRepository;

    @GetMapping("/profile")
    public String getProfile(@RequestParam("userID") Integer userId, Model model) {
        User user = profileService.getUserById(userId);
        model.addAttribute("user", user);
        model.addAttribute("orders", profileService.getUserOrdersWithProducts(userId));

        List<UserReviewDTO> reviews = reviewedRepository.findReviewsByUser(userId);
        model.addAttribute("reviews", reviews);
        return "profile";
    }

    @GetMapping("/editProfile")
    public String editProfile(@RequestParam("userID") Integer userId, Model model) {
        User user = profileService.getUserById(userId);
        model.addAttribute("user", user);
        return "editProfile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(User user) {
        profileService.updateUserProfile(user);
        return "redirect:/profile?userID=" + user.getUserId();
    }

}
