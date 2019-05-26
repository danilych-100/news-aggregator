package ru.luckyboy.aggregator.domain;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Data
public class NewsItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(length = 1000)
    private String title;

    @Column
    private String author;

    @Column
    private String category;

    @Column(length = 1000, name = "img_src")
    private String imgSrc;

    @Lob
    @Type(type = "org.hibernate.type.StringType")
    @Column(length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "published_date")
    private String publishedDate;

    @Column(length = 1000)
    private String link;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "news_source_id")
    private NewsSource newsSource;

}
