package ru.luckyboy.aggregator.loaders;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.springframework.util.CollectionUtils;
import ru.luckyboy.aggregator.domain.NewsItem;
import ru.luckyboy.aggregator.domain.ParseRule;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class RssNewsLoader implements INewsLoader{

    @Override
    public List<NewsItem> loadNewsFeedFromUrlByRules(final String url, final ParseRule rule) throws Exception {
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(new URL(url)));

        List<NewsItem> newsItems = new ArrayList<>();
        if(feed.getEntries() != null){
            for (Object o : feed.getEntries()){
                SyndEntryImpl entry = (SyndEntryImpl) o;
                NewsItem newsItem = new NewsItem();
                newsItem.setTitle(entry.getTitle());
                newsItem.setLink(entry.getLink());
                newsItem.setDescription(entry.getDescription().getValue());
                newsItem.setPublishedDate(entry.getPublishedDate().toString());
                newsItem.setAuthor(entry.getAuthor());
                List categories = entry.getCategories();
                if(!CollectionUtils.isEmpty(categories)){
                    SyndCategory syndCategory = (SyndCategory) categories.get(0);
                    newsItem.setCategory(syndCategory.getName());
                }
                List enclosures = entry.getEnclosures();
                if(!CollectionUtils.isEmpty(enclosures)){
                    SyndEnclosure syndEnclosure = (SyndEnclosure) enclosures.get(0);
                    newsItem.setImgSrc(syndEnclosure.getUrl());
                }

                newsItems.add(newsItem);
            }
        }

        return newsItems;
    }
}