package com.ezshopping.model;

import org.springframework.http.ResponseEntity;

import java.util.List;

public abstract class AbstractController<E extends AbstractEntity> {

    //TODO: remove
    public abstract ResponseEntity<List<E>> getAllEntities();
}
