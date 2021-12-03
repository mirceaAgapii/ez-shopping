package com.ezshopping.model;

public interface Mapper<E, D> {

    D map(E entity);
}
