package bg.softuni.pethotel.web;
import bg.softuni.pethotel.model.enums.AnimalTypeEnum;
import bg.softuni.pethotel.model.view.UserProfileViewModel;
import bg.softuni.pethotel.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserProfileViewModel user(@AuthenticationPrincipal UserDetails principal) {
        return userService.getUserProfile(principal.getUsername());
    }

    @GetMapping
    public String profile(Model model) {

        Map<String, String> types = new HashMap<>();

        for (AnimalTypeEnum type : AnimalTypeEnum.values()) {
            types.put(type.toString(), type.displayLabel);
        }

        model.addAttribute("types", types);

        return "profile";
    }
}
