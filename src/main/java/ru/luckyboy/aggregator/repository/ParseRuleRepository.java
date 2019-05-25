package ru.luckyboy.aggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.luckyboy.aggregator.domain.ParseRule;

public interface ParseRuleRepository extends JpaRepository<ParseRule, Integer> {
}
