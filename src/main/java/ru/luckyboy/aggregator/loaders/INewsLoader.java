package ru.luckyboy.aggregator.loaders;

import ru.luckyboy.aggregator.domain.NewsItem;
import ru.luckyboy.aggregator.domain.ParseRule;
import ru.luckyboy.aggregator.exceptions.BadRuleException;

import java.io.IOException;
import java.util.List;

public interface INewsLoader {
    List<NewsItem> loadNewsFeedFromUrlByRules(final String url, final ParseRule rule) throws IOException, BadRuleException;
}
