package ru.luckyboy.aggregator.repository;

import org.hibernate.validator.constraints.URL;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.luckyboy.aggregator.domain.NewsSource;

import javax.validation.constraints.Size;
import java.util.Optional;

public interface NewsSourceRepository extends JpaRepository<NewsSource, Long> {

    Optional<NewsSource> findByUrl(String url);
}
