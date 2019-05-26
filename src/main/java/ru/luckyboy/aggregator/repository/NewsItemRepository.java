package ru.luckyboy.aggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.luckyboy.aggregator.domain.NewsItem;

public interface NewsItemRepository extends JpaRepository<NewsItem, Integer> {

    NewsItem findByLink(String link);
}
