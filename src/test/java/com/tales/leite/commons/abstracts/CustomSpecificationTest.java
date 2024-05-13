package com.tales.leite.commons.abstracts;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.powermock.api.mockito.PowerMockito.mock;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomSpecificationTest {


    @Mock
    private Root<String> root;
    @Mock
    private CriteriaQuery<?> query;
    @Mock
    private CriteriaBuilder builder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void toPredicate() {
        Predicate mockPredicate01 = Mockito.mock(Predicate.class);
        Predicate mockPredicate02 = Mockito.mock(Predicate.class);
        List<Predicate> values = List.of(mockPredicate01, mockPredicate02);
        new CustomSpecificationImpl(values).toPredicate(root, query, builder);
        Mockito.verify(builder).and(new Predicate[] { mockPredicate01, mockPredicate02});
        Mockito.verifyNoMoreInteractions(root, query, builder);
    }

    @AllArgsConstructor
    private class CustomSpecificationImpl extends CustomSpecification<String> {

        private List<Predicate> values;

        @Override
        protected List<Predicate> predicates(Root<String> root,
                                             CriteriaQuery<?> query,
                                             CriteriaBuilder builder) {
            return values;
        }

    }


}
