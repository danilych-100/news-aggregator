package ru.luckyboy.aggregator.utils;

import ru.luckyboy.aggregator.domain.enumeration.SourceType;
import ru.luckyboy.aggregator.loaders.HtmlNewsLoader;
import ru.luckyboy.aggregator.loaders.INewsLoader;
import ru.luckyboy.aggregator.loaders.RssNewsLoader;

public class NewsUtils {

    private NewsUtils(){}

    public static INewsLoader selectNewsLoaderBySourceType(final SourceType sourceType){
        switch (sourceType){
            case RSS:
                return new RssNewsLoader();
            case HTML:
                return new HtmlNewsLoader();
            default:
                return new HtmlNewsLoader();
        }
    }
}
