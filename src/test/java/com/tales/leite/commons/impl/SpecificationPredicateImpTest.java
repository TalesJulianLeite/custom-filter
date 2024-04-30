package com.tales.leite.commons.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import jakarta.persistence.criteria.*;

import org.junit.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.*;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SpecificationPredicateImpTest {

    @Mock
    private Root<?> root;

    @Mock
    private CriteriaQuery<?> query;

    @Mock
    private CriteriaBuilder builder;

    private SpecificationPredicateImp<Object> specPredicate;

    private static final Logger log = LoggerFactory.getLogger(SpecificationPredicateImpTest.class);

    @BeforeEach
    public void setUp() {
        specPredicate = (SpecificationPredicateImp<Object>) new SpecificationPredicateImp<>(root, query, builder);
    }

    @Test
    public void testAddLike() {
        String attributeName = "name";
        String value = "John";
        boolean ignoreCase = false;

        Predicate expectedPredicate = mock(Predicate.class);
        when(builder.like(any(Expression.class), any(String.class))).thenReturn(expectedPredicate);

        assertTrue(specPredicate.addLike(true, attributeName, value, ignoreCase));
        verify(builder).like(any(Expression.class), eq("%" + value + "%"));
    }

    @Test
    public void testLike() {
        String attributeName = "name";
        String value = "John";
        boolean ignoreCase = false;

        Path<String> path = mock(Path.class);
        when(root.get(attributeName)).thenReturn(path.get(attributeName));

        specPredicate.like(true, attributeName, value, ignoreCase);

        verify(builder).like(path, "%" + value + "%");
    }

    // Add more test methods for other functionalities
}
