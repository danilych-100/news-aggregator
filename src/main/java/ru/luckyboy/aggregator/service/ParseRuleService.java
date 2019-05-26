package ru.luckyboy.aggregator.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.luckyboy.aggregator.domain.ParseRule;
import ru.luckyboy.aggregator.repository.ParseRuleRepository;

import java.util.Optional;

@Service
public class ParseRuleService {

    private final ParseRuleRepository parseRuleRepository;

    public ParseRuleService(ParseRuleRepository parseRuleRepository) {
        this.parseRuleRepository = parseRuleRepository;
    }

    @Transactional
    public void saveParseRule(ParseRule rule){
        parseRuleRepository.save(rule);
    }
}
