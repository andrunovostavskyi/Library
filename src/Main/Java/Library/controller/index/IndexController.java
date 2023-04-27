package Library.controller.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {

    @GetMapping({"/", "index"})
    public String redirectToSwagger() {
        return "redirect:/swagger-ui/index.html";
    }
}
