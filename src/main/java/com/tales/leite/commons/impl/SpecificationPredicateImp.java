package com.tales.leite.commons.impl;

import com.tales.leite.commons.abstracts.CustomAbstractList;
import com.tales.leite.commons.interfaces.SpecificationPredicate;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import jakarta.persistence.criteria.*;
import org.apache.commons.logging.Log;
import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;

@Getter
@Data
@Builder
public class SpecificationPredicateImp<I>
    extends CustomAbstractList<Predicate>
    implements SpecificationPredicate {

  private final Root<I> root;
  private final CriteriaQuery<?> query;
  private final CriteriaBuilder builder;
  private Log log;

  public SpecificationPredicateImp(Root<I> root,
      CriteriaQuery<?> query,
      CriteriaBuilder builder) {
    super(new ArrayList<>());
    this.root = root;
    this.query = query;
    this.builder = builder;
  }

  @Override
  public boolean addLike(boolean required,
      String attributeName,
      String value,
      boolean ignoreCase) {
    if (!verify(required, attributeName, value)) {
      return false;
    } else {
      return add(like(required, attributeName, value, ignoreCase));
    }
  }

  @Override
  public Predicate like(boolean required,
      String attributeName,
      String value,
      boolean ignoreCase) {
    if (!verify(required, attributeName, value)) {
      throw new IllegalArgumentException("Specification value for field " + attributeName + " is required");
    }
    Path<String> path = field(attributeName);
    if (ignoreCase) {
      Expression<String> expression;
        expression = getBuilder().lower(path);
        String lowerCase = value.toLowerCase();
      return like(expression, "%" + lowerCase + "%");
    } else {
      return like(path, "%" + value + "%");
    }
  }

  @Override
  public Predicate like(Expression<String> field, String value) {
    log.info("like specification for {} with value <{}>", field, value);
    return getBuilder().like(field, value);
  }

  @Override
  public boolean addEquals(boolean required,
      String attributeName,
      String value,
      boolean ignoreCase) {
    if (!verify(required, attributeName, value)) {
      return false;
    } else {
      return add(predicateEquals(required, attributeName, value, ignoreCase));
    }
  }

  @Override
  public Predicate predicateEquals(boolean required,
      String rootAttributeName,
      String value,
      boolean ignoreCase) {
    if (!verify(required, rootAttributeName, value)) {
      throw new IllegalArgumentException("Specification value for field " + rootAttributeName + " is required");
    }
    Path<String> path = field(rootAttributeName);
    if (ignoreCase) {
      log.info("Using ignore case-sensitive for comparing {} with {}", rootAttributeName, value);
      Expression<String> expression = getBuilder().lower(path);
      String lowerCase = value.toLowerCase();
      return predicateEqual(expression, lowerCase);
    } else {
      log.info("Using case-sensitive for comparing {} with {}", rootAttributeName, value);
      return predicateEqual(path, value);
    }
  }

  @Override
  public Predicate predicateEqual(Expression<?> field, Object value) {
    log.info("add equal specification for {} with value <{}>", field, value);
    return getBuilder().equal(field, value);
  }

  @Override
  public boolean addEqual(boolean required,
      String rootAttributeName,
      Object value) {
    if (!verify(required, rootAttributeName, value)) {
      return false;
    }
    Path<String> path = field(rootAttributeName);
    return add(predicateEqual(path, value));
  }

  @Override
  public boolean addEqual(Expression<?> field, Object value) {
    return add(predicateEqual(field, value));
  }

  @Override
  public boolean or(Predicate... restrictions) {
    return add(getBuilder().or(restrictions));
  }

  private boolean verify(boolean required,
      String attributeName,
      String value) {
    return verify(required, attributeName, value, o -> StringUtils.isEmpty(value));
  }

  private boolean verify(boolean required,
      String attributeName,
      Object value) {
    return verify(required, attributeName, value, Objects::nonNull);
  }

  private <T> boolean verify(boolean required,
      String attributeName,
      T value,
      Function<T, Boolean> function) {
    if (required && function.apply(value)) {
      throw new IllegalArgumentException("Specification value for field " + attributeName + " is required");
    } else {
      return Objects.nonNull(value);
    }
  }

  private <O> Path<O> field(String attributeName) {
    return getRoot().get(attributeName);
  }

}
