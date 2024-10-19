package com.example.demo.specification;

import com.example.demo.entity.History;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistorySpecification {

    public static Specification<History> hasCategories(List<Long> categories) {

        return (root, query, criteriaBuilder) -> {

            if (categories == null || categories.isEmpty()) {
                return null;
            }
            return root.get("category").get("id").in(categories);
        };
    }

    public static Specification<History> hasDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if(startDate != null) {

                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), startDate));
            }
            if(endDate != null) {

                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), endDate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    // ledgerId로 필터링
    public static Specification<History> hasLedgerId(Long ledgerId) {
        return (root, query, criteriaBuilder) -> {
            if (ledgerId == null) {
                return null; // ledgerId가 없으면 필터링하지 않음
            }
            return criteriaBuilder.equal(root.join("ledger", JoinType.INNER).get("id"), ledgerId);
        };
    }
}
