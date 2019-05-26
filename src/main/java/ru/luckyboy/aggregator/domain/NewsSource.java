package ru.luckyboy.aggregator.domain;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
public class NewsSource {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(min = 1, message = "Invalid URL")
    @URL(message = "Invalid URL")
    @Column(length = 1000)
    private String url;

    @Size(min = 1, message = "Name must be at least 1 character")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parse_rule_id")
    private ParseRule parseRule;

    @OneToMany(mappedBy = "newsSource", cascade = CascadeType.ALL)
    private List<NewsItem> newsItems;
}
