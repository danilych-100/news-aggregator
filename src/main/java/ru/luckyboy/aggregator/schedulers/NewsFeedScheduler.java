package ru.luckyboy.aggregator.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.luckyboy.aggregator.domain.NewsItem;
import ru.luckyboy.aggregator.domain.NewsSource;
import ru.luckyboy.aggregator.domain.ParseRule;
import ru.luckyboy.aggregator.exceptions.BadRuleException;
import ru.luckyboy.aggregator.loaders.INewsLoader;
import ru.luckyboy.aggregator.service.NewsItemsService;
import ru.luckyboy.aggregator.service.NewsSourceService;
import ru.luckyboy.aggregator.utils.NewsUtils;

import java.io.IOException;
import java.util.List;

@Service
@EnableScheduling
public class NewsFeedScheduler {

    private final Logger logger = LoggerFactory.getLogger(NewsFeedScheduler.class);

    private final NewsSourceService newsSourceService;
    private final NewsItemsService newsItemsService;

    public NewsFeedScheduler(NewsSourceService newsSourceService, NewsItemsService newsItemsService) {
        this.newsSourceService = newsSourceService;
        this.newsItemsService = newsItemsService;
    }

    @Scheduled(fixedDelayString = "${addItemsFromSources.fixedDelay}")
    public void addNewsItemsTask(){
        logger.debug("Start task for add news items from sources");

        List<NewsSource> newsSources = newsSourceService.findAll();
        if(CollectionUtils.isEmpty(newsSources)){
            logger.debug("Finish task for add news items from sources");
            return;
        }

        for(NewsSource source : newsSources){
            processNewsSource(source);
        }

        logger.debug("Finish task for add news items from sources");
    }

    private void processNewsSource(final NewsSource source){
        ParseRule parseRule = source.getParseRule();
        INewsLoader newsLoader = NewsUtils.selectNewsLoaderBySourceType(parseRule.getSource());

        try {
            List<NewsItem> foundNewsItems = newsLoader.loadNewsFeedFromUrlByRules(source.getUrl(), parseRule);
            List<NewsItem> needToSaveItems = newsItemsService.getAllNonExistedNews(foundNewsItems);
            if(CollectionUtils.isEmpty(needToSaveItems)){
                return;
            }

            needToSaveItems.forEach(newsItem -> newsItem.setNewsSource(source));
            if(source.getNewsItems() == null){
                source.setNewsItems(needToSaveItems);
            } else {
                source.getNewsItems().addAll(needToSaveItems);
            }

            newsSourceService.updateSource(source);
        } catch (IOException | BadRuleException e) {
            logger.error("Error scheduled add new NewsItems", e);
        }
    }
}
