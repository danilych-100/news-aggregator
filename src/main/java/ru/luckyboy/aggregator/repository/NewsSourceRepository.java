package ru.luckyboy.aggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.luckyboy.aggregator.domain.NewsSource;

import java.util.Optional;

public interface NewsSourceRepository extends JpaRepository<NewsSource, Long> {

    Optional<NewsSource> findByUrl(String url);
}
