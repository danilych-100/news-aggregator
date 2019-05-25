package ru.luckyboy.aggregator.service;

import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

@Service
public class YamlParser<T> {

    public T parse(InputStream inputStream){
        return new Yaml().load(inputStream);
    }
}
