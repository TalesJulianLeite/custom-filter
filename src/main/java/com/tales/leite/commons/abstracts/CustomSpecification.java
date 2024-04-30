package com.tales.leite.commons.abstracts;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomSpecification<T> implements Specification<T> {

    @Override
    public final Predicate toPredicate(Root<T> root,
                                       CriteriaQuery<?> query,
                                       CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>(predicates(root, query, builder));
        return builder.and(predicates.toArray(new Predicate[]{}));
    }

    protected abstract List<Predicate> predicates(Root<T> root,
                                                  CriteriaQuery<?> query,
                                                  CriteriaBuilder builder);
}
