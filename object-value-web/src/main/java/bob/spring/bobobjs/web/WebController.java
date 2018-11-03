package bob.spring.bobobjs.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
	
	@RequestMapping("/")
    String home(){
        return "home";
    }
	
	@RequestMapping("/login")
    String login(){
        return "login";
    }

	@RequestMapping("/menu")
    String menu(){
        return "menu";
    }
	
}
