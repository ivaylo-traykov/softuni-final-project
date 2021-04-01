package bg.softuni.pethotel.web;

import bg.softuni.pethotel.model.binding.UserRegisterBindingModel;
import bg.softuni.pethotel.model.service.UserRegisterServiceModel;
import bg.softuni.pethotel.model.view.UserListViewModel;
import bg.softuni.pethotel.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("registerModel")
    public UserRegisterBindingModel registerMode() {
        return new UserRegisterBindingModel();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid UserRegisterBindingModel registerBindingModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerModel", registerBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerModel", bindingResult);
            return "redirect:register";
        }

        if (userService.emailExists(registerBindingModel.getEmail())) {
            redirectAttributes.addFlashAttribute("registerModel", registerBindingModel);
            redirectAttributes.addFlashAttribute("emailExists", true);
            return "redirect:register";
        }

        UserRegisterServiceModel user = modelMapper.map(registerBindingModel, UserRegisterServiceModel.class);

        userService.registerUser(user);

        return "redirect:/";
    }

    @PostMapping("/login-error")
    public String failedLogin(@ModelAttribute("email") String email,
                              RedirectAttributes attributes) {

        attributes.addFlashAttribute("bad_credentials", true);
        attributes.addFlashAttribute("email", email);

        return "redirect:/users/login";
    }

    @RequestMapping("/list")
    public String usersList(Model model, @Param("keyword") String keyword) {

        List<UserListViewModel> users = userService.getFilteredUsersList(keyword);

        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);

        return "users-list";
    }
}
