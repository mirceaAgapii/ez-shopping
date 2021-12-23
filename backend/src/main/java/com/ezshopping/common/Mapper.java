package com.ezshopping.common;

public interface Mapper<E, D> {

    D map(E entity);
}
