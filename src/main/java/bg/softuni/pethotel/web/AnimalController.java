package bg.softuni.pethotel.web;

import bg.softuni.pethotel.model.binding.AnimalRegisterBindingModel;
import bg.softuni.pethotel.model.enums.AnimalTypeEnum;
import bg.softuni.pethotel.model.enums.DogSizeEnum;
import bg.softuni.pethotel.model.enums.GenderEnum;
import bg.softuni.pethotel.service.AnimalService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/animals")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @ModelAttribute("animal")
    public AnimalRegisterBindingModel animal() {
        return new AnimalRegisterBindingModel();
    }

    @GetMapping("/register")
    public String register(Model model) {

        List<AnimalTypeEnum> types = Arrays.asList(AnimalTypeEnum.values());
        List<GenderEnum> genders = Arrays.asList(GenderEnum.values());
        List<DogSizeEnum> sizes = Arrays.asList(DogSizeEnum.values());

        model.addAttribute("animalTypes", types);
        model.addAttribute("genders", genders);
        model.addAttribute("sizes", sizes);

        return "register-animal";
    }

    @PostMapping("/register")
    public String registerAnimal(@Valid @ModelAttribute("animal") AnimalRegisterBindingModel animalRegisterBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @AuthenticationPrincipal UserDetails principal) throws IOException {

        if (animalRegisterBindingModel.getType() == AnimalTypeEnum.DOG && animalRegisterBindingModel.getSize() == null) {
            bindingResult.rejectValue("size", "Моля, посочете размера на кучето");
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("animal", animalRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.animal", bindingResult);
            return "redirect:register";
        }

        animalService.registerAnimal(animalRegisterBindingModel, principal.getUsername());

        return "redirect:/profile";
    }
}
