package ru.luckyboy.aggregator.service;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

@Service
public class RssParserService {


    public void getNewsFeedFromUrl(URL url) throws IOException, FeedException {
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
    }
}
