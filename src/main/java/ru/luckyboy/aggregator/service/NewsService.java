package ru.luckyboy.aggregator.service;

import org.springframework.stereotype.Service;
import ru.luckyboy.aggregator.domain.NewsItem;
import ru.luckyboy.aggregator.domain.NewsSource;
import ru.luckyboy.aggregator.domain.ParseRule;
import ru.luckyboy.aggregator.domain.enumeration.SourceType;
import ru.luckyboy.aggregator.exceptions.BadRuleException;
import ru.luckyboy.aggregator.loaders.HtmlNewsLoader;
import ru.luckyboy.aggregator.loaders.INewsLoader;
import ru.luckyboy.aggregator.loaders.RssNewsLoader;
import ru.luckyboy.aggregator.service.helpers.YamlParserHelper;
import ru.luckyboy.aggregator.web.dto.NewsSourceDTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class NewsService {

    private final YamlParserHelper<ParseRule> yamlParserHelper;

    private final NewsSourceService newsSourceService;
    private final ParseRuleService parseRuleService;
    private final NewsItemsService newsItemsService;


    public NewsService(NewsSourceService newsSourceService,
                       ParseRuleService parseRuleService,
                       NewsItemsService newsItemsService){
        this.newsSourceService = newsSourceService;
        this.parseRuleService = parseRuleService;
        this.newsItemsService = newsItemsService;

        this.yamlParserHelper = new YamlParserHelper<>();
    }


    public void loadNewsSource(final NewsSourceDTO newsSourceDTO, final InputStream fileInputStream) throws IOException, BadRuleException {
        ParseRule parseRule = yamlParserHelper.parse(fileInputStream, ParseRule.class);

        NewsSource newsSource = new NewsSource();
        newsSource.setName(newsSourceDTO.getName());
        newsSource.setUrl(newsSourceDTO.getUrl());
        newsSource.setParseRule(parseRule);
        parseRule.setNewsSource(newsSource);

        INewsLoader newsLoader = selectNewsLoaderBySourceType(parseRule.getSource());

        List<NewsItem> foundNewsItems = newsLoader.loadNewsFeedFromUrlByRules(newsSource.getUrl(), parseRule);
        List<NewsItem> needToSaveItems = newsItemsService.getAllNonExistedNews(foundNewsItems);
        needToSaveItems.forEach(newsItem -> newsItem.setNewsSource(newsSource));
        newsSource.setNewsItems(needToSaveItems);

        newsSourceService.saveSource(newsSource);
    }

    private INewsLoader selectNewsLoaderBySourceType(final SourceType sourceType){
        switch (sourceType){
            case RSS:
                return new RssNewsLoader();
            case HTML:
                return new HtmlNewsLoader();
                default:
                    return new HtmlNewsLoader();
        }
    }
}
