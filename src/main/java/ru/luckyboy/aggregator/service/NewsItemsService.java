package ru.luckyboy.aggregator.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.luckyboy.aggregator.domain.NewsItem;
import ru.luckyboy.aggregator.domain.NewsSource;
import ru.luckyboy.aggregator.repository.NewsItemRepository;
import ru.luckyboy.aggregator.repository.NewsSourceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NewsItemsService {

    private final NewsItemRepository newsItemRepository;

    public NewsItemsService(NewsItemRepository newsItemRepository) {
        this.newsItemRepository = newsItemRepository;
    }

    @Transactional
    public void saveItems(List<NewsItem> newsItems){
        newsItemRepository.saveAll(getAllNonExistedNews(newsItems));
    }

    public long countNewsItems(){
        return newsItemRepository.count();
    }

    public long countNewsItemsBySearch(final String search){
        return newsItemRepository.countByTitleContainsAllIgnoreCase(search);
    }

    public List<NewsItem> findAllPageable(final int page, final int size){
        return newsItemRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public List<NewsItem> findAllPageableBySearch(final int page, final int size, final String search){
        return newsItemRepository.findByTitleContainsAllIgnoreCase(search, PageRequest.of(page, size)).getContent();
    }

    public List<NewsItem> getAllNonExistedNews(List<NewsItem> newsItems){
        List<NewsItem> needToSaveItems = new ArrayList<>();
        for(NewsItem newsItem : newsItems){
            NewsItem savedItem = findByLink(newsItem.getLink());
            if(savedItem == null) {
                needToSaveItems.add(newsItem);
            }
        }
        return needToSaveItems;
    }

    private NewsItem findByLink(final String link){
        return newsItemRepository.findByLink(link);
    }
}
