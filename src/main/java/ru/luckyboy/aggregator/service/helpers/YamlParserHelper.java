package ru.luckyboy.aggregator.service.helpers;

import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import ru.luckyboy.aggregator.domain.ParseRule;

import java.io.InputStream;


public class YamlParserHelper<T> {

    public T parse(InputStream inputStream, Class<T> classOfMappedObject){
        return new Yaml(new Constructor(classOfMappedObject)).load(inputStream);
    }
}
