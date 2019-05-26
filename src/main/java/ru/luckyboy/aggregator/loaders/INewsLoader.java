package ru.luckyboy.aggregator.loaders;

import ru.luckyboy.aggregator.domain.NewsItem;
import ru.luckyboy.aggregator.domain.ParseRule;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface INewsLoader {
    List<NewsItem> loadNewsFeedFromUrlByRules(final String url, final ParseRule rule) throws Exception;
}
