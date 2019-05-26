package ru.luckyboy.aggregator.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.luckyboy.aggregator.domain.NewsSource;
import ru.luckyboy.aggregator.repository.NewsSourceRepository;

import java.util.Optional;

@Service
public class NewsSourceService {

    private final NewsSourceRepository newsSourceRepository;

    public NewsSourceService(NewsSourceRepository newsSourceRepository) {
        this.newsSourceRepository = newsSourceRepository;
    }

    @Transactional
    public void saveSource(NewsSource newsSource){
        Optional<NewsSource> existedNewsSource = newsSourceRepository.findByUrl(newsSource.getUrl());
        if(existedNewsSource.isPresent()){
            newsSource = existedNewsSource.get();
            newsSource.setParseRule(newsSource.getParseRule());
        }
        newsSourceRepository.save(newsSource);
    }
}
