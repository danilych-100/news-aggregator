package ru.luckyboy.aggregator.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class NewsFeedScheduler {

    Logger logger = LoggerFactory.getLogger(NewsFeedScheduler.class);

    @Scheduled(fixedDelayString = "${addItemsFromSources.fixedDelay}")
    public void addNewsItemsTask(){
        logger.debug("Start task for add news items from sources");
    }


    @ConditionalOnProperty(value = "clearOldNewsItems.enabled", matchIfMissing = true, havingValue = "true")
    @Scheduled(fixedDelayString = "${clearOldNewsItems.fixedDelay}")
    public void clearOldNewsItemsTask(){
        logger.debug("Start task for clear old news items from DB");
    }
}
