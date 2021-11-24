package com.ezshopping.model;

import java.util.List;

public abstract class AbstractService<E extends AbstractEntity> {

    public abstract List<E> getAll();
}
