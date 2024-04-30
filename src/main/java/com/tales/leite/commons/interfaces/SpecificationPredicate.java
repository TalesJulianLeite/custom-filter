package com.tales.leite.commons.interfaces;


import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

import java.util.List;

public interface SpecificationPredicate extends List<Predicate> {

  Predicate like(boolean b,
      String string,
      String trim,
      boolean c);

  boolean addLike(boolean required,
      String field,
      String value,
      boolean ignoreCase);

  boolean addEquals(boolean required,
      String field,
      String value,
      boolean ignoreCase);

  Predicate predicateEquals(boolean required,
      String field,
      String value,
      boolean ignoreCase);

  boolean addEqual(Expression<?> expression, Object value);

  boolean or(Predicate... restrictions);

  Predicate like(Expression<String> field, String value);

  Predicate predicateEqual(Expression<?> field, Object value);

  boolean addEqual(boolean required,
      String rootAttributeName,
      Object value);
}
