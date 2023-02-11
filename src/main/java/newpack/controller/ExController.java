package newpack.controller;//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestParam;
//
//
//@Controller
//public class ExController {
//
////    @GetMapping("/")
////    public String example(Model model) {
////        model.addAttribute("message", "Hello from the backend!");
////        return "index";
////    }
//
//    @GetMapping({"/","/hello"})
//    public String hello(@RequestParam(value="name",defaultValue="World",required=true) String name,Model model)
//    {
//        model.addAttribute("name",name);
//        return "hello";
//    }
//
//}


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExController {

    @GetMapping({
            "/",
            "/hello"
    })
    public String hello(Model model) {
        model.addAttribute("message", "worldd");
        return "index";
    }
}
