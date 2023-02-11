@Controller
public class Controller {
    @GetMapping("/example")
    public String example(Model model) {
        model.addAttribute("message", "Hello from the backend!");
        return "example";
    }
}

