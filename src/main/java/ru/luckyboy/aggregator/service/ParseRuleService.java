package ru.luckyboy.aggregator.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.luckyboy.aggregator.domain.ParseRule;
import ru.luckyboy.aggregator.repository.ParseRuleRepository;

@Service
public class ParseRuleService {

    private final ParseRuleRepository parseRuleRepository;

    public ParseRuleService(ParseRuleRepository parseRuleRepository) {
        this.parseRuleRepository = parseRuleRepository;
    }

    public ParseRule findById(Long ruleId) {
        return parseRuleRepository.findById(ruleId).get();
    }
}
