package ru.luckyboy.aggregator.web;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.luckyboy.aggregator.service.YamlParser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Controller
public class WelcomeController {

/*    @Autowired
    private YamlParser<String> yamlParser;*/

    private List<String> tasks = Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    @GetMapping("/")
    public String main(Model model) throws IOException, FeedException {
        model.addAttribute("message", "Simple message");
        model.addAttribute("tasks", tasks);

        URL feedSource = new URL("https://snob.ru/rss/all");
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedSource));
        for (Object o : feed.getEntries()){
            SyndEntryImpl entry = (SyndEntryImpl) o;
            System.out.println("Title: " + entry.getTitle());
            System.out.println("Author: " + entry.getAuthor());
            System.out.println("img: " +  entry.getEnclosures());
            System.out.println("Cat: " + entry.getCategories());
            System.out.println("Date: " + entry.getPublishedDate());
            System.out.println("Uri: " + entry.getUri());
            System.out.println("Desc: " + entry.getDescription().getValue());
        }
        int a = 0;

        return "welcome"; //view
    }

    // /hello?name=kotlin
    @GetMapping("/hello")
    public String mainWithParam(
            @RequestParam(name = "name", required = false, defaultValue = "")
                    String name, Model model) {

        model.addAttribute("message", name);

        return "welcome"; //view
    }
}
