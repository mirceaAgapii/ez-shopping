package com.ezshopping.model;

import org.springframework.http.ResponseEntity;

import java.util.List;

public abstract class AbstractController<E extends AbstractEntity> {

    public abstract ResponseEntity<List<E>> getAllEntities();
}
