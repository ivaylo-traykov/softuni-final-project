package bg.softuni.pethotel.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gallery")
public class GalleryController {

    @GetMapping
    public String gallery() {
        // TODO: Implement gallery
        throw new UnsupportedOperationException("Галерията все още не е разработена. Очаквайте я скоро :-)");
    }
}
