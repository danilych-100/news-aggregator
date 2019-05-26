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
import ru.luckyboy.aggregator.exceptions.BadRuleException;
import ru.luckyboy.aggregator.service.NewsService;
import ru.luckyboy.aggregator.web.dto.NewsSourceDTO;

import javax.persistence.NonUniqueResultException;
import java.io.IOException;

@Controller
public class NewsController {

    private final Logger logger = LoggerFactory.getLogger(NewsController.class);

    private final NewsService newsService;

    public NewsController(NewsService newsService){
        this.newsService = newsService;
    }

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("newsSource", new NewsSourceDTO());

        return "addNewsSource";
    }

    @PostMapping("/addNewsSource")
    public String addNewsSourceSubmit(@RequestParam("file") MultipartFile file,
                                      @ModelAttribute NewsSourceDTO newsSource) {
        try {
            newsService.loadNewsSource(newsSource, file.getInputStream());
        } catch (NonUniqueResultException e){
            logger.error("This source url been already added", e);
        } catch (IOException e){
            logger.error("There is a problem with loading news feed (maybe internet is down)", e);
        } catch (BadRuleException e) {
            logger.error("There is error in rule", e);
        }

        return "redirect:/";
    }

    @GetMapping("/news-list")
    public String newsList(Model model) {
        model.addAttribute("greeting", new NewsSourceDTO());

        return "newsList";
    }
}
