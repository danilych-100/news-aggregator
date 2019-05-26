package ru.luckyboy.aggregator.service;

import org.springframework.stereotype.Service;
import ru.luckyboy.aggregator.domain.NewsItem;
import ru.luckyboy.aggregator.domain.NewsSource;
import ru.luckyboy.aggregator.domain.ParseRule;
import ru.luckyboy.aggregator.exceptions.BadRuleException;
import ru.luckyboy.aggregator.loaders.INewsLoader;
import ru.luckyboy.aggregator.service.helpers.YamlParserHelper;
import ru.luckyboy.aggregator.service.mapper.NewsSourceMapper;
import ru.luckyboy.aggregator.utils.NewsUtils;
import ru.luckyboy.aggregator.web.dto.NewsSourceDTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

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
        this.newsItemsService = newsItemsService;
        this.parseRuleService = parseRuleService;

        this.yamlParserHelper = new YamlParserHelper<>();
    }


    public void loadNewsSource(final NewsSourceDTO newsSourceDTO, final InputStream fileInputStream) throws IOException, BadRuleException {
        ParseRule parseRule = yamlParserHelper.parse(fileInputStream, ParseRule.class);

        NewsSource newsSource = new NewsSource();
        newsSource.setName(newsSourceDTO.getName());
        newsSource.setUrl(newsSourceDTO.getUrl());
        newsSource.setParseRule(parseRule);
        parseRule.setNewsSource(newsSource);

        INewsLoader newsLoader = NewsUtils.selectNewsLoaderBySourceType(parseRule.getSource());

        List<NewsItem> foundNewsItems = newsLoader.loadNewsFeedFromUrlByRules(newsSource.getUrl(), parseRule);
        foundNewsItems.forEach(newsItem -> newsItem.setNewsSource(newsSource));
        newsSource.setNewsItems(foundNewsItems);

        newsSourceService.createSource(newsSource);
    }

    public List<NewsSourceDTO> getExistedNewsSources() {
        return newsSourceService.findAll().stream().map(NewsSourceMapper::toDTO).collect(Collectors.toList());
    }

    public String downloadRule(Long ruleId) {
        ParseRule rule = parseRuleService.findById(ruleId);
        rule.setNewsSource(null);
        return yamlParserHelper.getYmlFromObject(rule);
    }
}
