package ru.luckyboy.aggregator.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.luckyboy.aggregator.web.dto.NewsSourceDTO;

@Controller
public class NewsController {

    Logger logger = LoggerFactory.getLogger(NewsController.class);

/*    @Autowired
    private YamlParser<String> yamlParser;*/

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("newsSource", new NewsSourceDTO());

        return "addNewsSource";
    }

    @PostMapping("/addNewsSource")
    public String addNewsSourceSubmit(@RequestParam("file") MultipartFile file,
                                      @ModelAttribute NewsSourceDTO newsSource) {
        return "redirect:/";
    }

    @GetMapping("/news-list")
    public String newsList(Model model) {
        model.addAttribute("greeting", new NewsSourceDTO());

        return "newsList";
    }
}
