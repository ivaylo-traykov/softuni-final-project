package bg.softuni.pethotel.web;

import bg.softuni.pethotel.model.binding.ReservationBindingModel;
import bg.softuni.pethotel.model.enums.AnimalTypeEnum;
import bg.softuni.pethotel.model.enums.SuiteTypeEnum;
import bg.softuni.pethotel.model.enums.WalkTypeEnum;
import bg.softuni.pethotel.model.view.AnimalViewModel;
import bg.softuni.pethotel.model.view.ReservationViewModel;
import bg.softuni.pethotel.service.AnimalService;
import bg.softuni.pethotel.service.ReservationService;
import bg.softuni.pethotel.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    private final UserService userService;
    private final AnimalService animalService;
    private final ReservationService reservationService;

    public ReservationController(UserService userService, AnimalService animalService, ReservationService reservationService) {
        this.userService = userService;
        this.animalService = animalService;
        this.reservationService = reservationService;
    }

    @GetMapping
    public String makeReservation(@AuthenticationPrincipal UserDetails principal, Model model) {

        Set<AnimalViewModel> animals = userService.getListOfAnimals(principal.getUsername());
        Map<String, String> types = new HashMap<>();
        for (AnimalTypeEnum type : AnimalTypeEnum.values()) {
            types.put(type.toString(), type.displayLabel);
        }

        model.addAttribute("types", types);
        model.addAttribute("animals", animals);

        return "make-reservation";
    }

    @GetMapping("/new/{id}")
    public String newReservation(@PathVariable Long id, Model model) {

        AnimalTypeEnum animalType = animalService.findAnimalType(id);

        model.addAttribute("animalType", animalType.name());

        if (animalType.equals(AnimalTypeEnum.DOG)) {
            model.addAttribute("suiteTypes", SuiteTypeEnum.values());
            model.addAttribute("walkTypes", WalkTypeEnum.values());
        }

        if (!model.containsAttribute("reservation")) {
            model.addAttribute("reservation", new ReservationBindingModel());
        }

        return "new-reservation";
    }

    @PostMapping("/new/{id}")
    public String addReservation(@PathVariable Long id,
                                 @Valid @ModelAttribute("reservation") ReservationBindingModel reservationBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @AuthenticationPrincipal UserDetails principal) {

        AnimalTypeEnum animalType = animalService.findAnimalType(id);
        reservationBindingModel.setAnimalId(id);

        if (!(reservationBindingModel.getStartDate() == null || reservationBindingModel.getEndDate() == null)) {
            if (reservationBindingModel.getStartDate().isAfter(reservationBindingModel.getEndDate())) {
                bindingResult.rejectValue("endDate", "date_error", "Крайната дата не може да бъде преди началната");
            }
        }

        if (animalType.equals(AnimalTypeEnum.DOG)) {
            if (reservationBindingModel.getSuiteType() == null) {
                bindingResult.rejectValue("suiteType", "suite_error", "Моля, избере");
            }
            if (reservationBindingModel.getWalkType() == null) {
                bindingResult.rejectValue("walkType", "walk_type_error", "Моля, избере");
            }
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("reservation", reservationBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reservation", bindingResult);
            return "redirect:" + id;
        }

        reservationService.bookReservation(reservationBindingModel, id, principal.getUsername());

        return "redirect:/";
    }

    @GetMapping("/all")
    public String allReservations(Model model,
                                  @AuthenticationPrincipal UserDetails principal) {

        List<ReservationViewModel> reservations = userService.findAllReservations(principal.getUsername());

        model.addAttribute("reservations", reservations);

        return "view-reservations";
    }

    @RequestMapping("/delete/{id}")
    public String deleteReservation(@PathVariable Long id) {

        reservationService.deleteReservation(id);

        return "redirect:/";
    }
}
