package com.ezshopping.location.store.model.entity;

import com.ezshopping.location.model.entity.Location;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ez_store")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Store extends Location {

    @Column(name = "store_id")
    private String storeName;

    @Column(name = "address")
    private String address;
}
