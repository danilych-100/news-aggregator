package ru.luckyboy.aggregator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.luckyboy.aggregator.domain.NewsItem;

import java.util.List;

public interface NewsItemRepository extends JpaRepository<NewsItem, Long> {

    NewsItem findByLink(String link);

    Page<NewsItem> findByTitleContainsAllIgnoreCase(String title, Pageable pageable);

    long countByTitleContainsAllIgnoreCase(String title);
}
