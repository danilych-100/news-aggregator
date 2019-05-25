package ru.luckyboy.aggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.luckyboy.aggregator.domain.NewsSource;

public interface NewsSourceRepository extends JpaRepository<NewsSource, Integer> {
}
