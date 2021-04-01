package bg.softuni.pethotel.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({UnsupportedOperationException.class})
    public ModelAndView unsupported(RuntimeException e) {
        ModelAndView mv = new ModelAndView("error-unsupported");
        mv.addObject("message", e.getMessage());
        return mv;
    }
}
