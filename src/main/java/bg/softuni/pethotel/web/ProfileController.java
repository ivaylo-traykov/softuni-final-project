package bg.softuni.pethotel.web;
import bg.softuni.pethotel.model.enums.AnimalTypeEnum;
import bg.softuni.pethotel.model.enums.RoleNameEnum;
import bg.softuni.pethotel.model.service.UserEditServiceModel;
import bg.softuni.pethotel.model.view.UserProfileViewModel;
import bg.softuni.pethotel.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
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

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id,
                           Model model,
                           @AuthenticationPrincipal UserDetails principal) throws IllegalAccessException {

        if (!userService.isOwnerOrModerator(id, principal)) {
            throw new IllegalAccessException("Тази страница не е достъпна");
        }

        if (!model.containsAttribute("editUser")) {
            UserEditServiceModel user = userService.getUserInformation(id);
            model.addAttribute("editUser", user);
        }

        if (userService.isModerator(principal.getAuthorities())) {
            model.addAttribute("roles", RoleNameEnum.values());
        }

        return "edit-user";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@Valid UserEditServiceModel userEditServiceModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              @PathVariable Long id,
                              @AuthenticationPrincipal UserDetails principal) throws IOException, IllegalAccessException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editUser", userEditServiceModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editUser", bindingResult);
            return "redirect:/profile/edit/" + id.toString();
        }

        if (!userService.isOwnerOrModerator(id, principal)) {
            throw new IllegalAccessException("Тази страница не е достъпна");
        }

        userService.updateUser(userEditServiceModel, id);

        return "redirect:/profile";
    }
}
