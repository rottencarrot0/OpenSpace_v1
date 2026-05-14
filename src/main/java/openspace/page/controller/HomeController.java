package openspace.page.controller;

import openspace.page.dto.space.SpaceList;
import openspace.page.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private SpaceService spaceService;

    @GetMapping("/")
    public String home(Model model) {
        List<SpaceList> latestSpace = spaceService.getLatestSpace(9);
        model.addAttribute("space", latestSpace);
        return "home";
    }
}
