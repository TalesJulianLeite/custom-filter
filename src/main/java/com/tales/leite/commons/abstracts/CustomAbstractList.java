package com.tales.leite.commons.abstracts;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;


import java.util.AbstractList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public abstract class CustomAbstractList<E> extends AbstractList<E> {

    @NotNull
    private final List<E> values;

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public boolean addAll(Collection<? extends E> o) {
        return values.addAll(o);
    }

    @Override
    public E get(int index) {
        return values.get(index);
    }

    @Override
    public void add(int index,
                    E element) {
        values.add(index, element);
    }

    @Override
    public E set(int index,
                 E element) {
        return values.set(index, element);
    }

    @Override
    public boolean remove(Object o) {
        return values.remove(o);
    }

}
