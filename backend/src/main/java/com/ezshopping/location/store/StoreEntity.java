package com.ezshopping.location.store;

import com.ezshopping.location.LocationEntity;
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
public class StoreEntity extends LocationEntity {

    @Column(name = "store_id")
    private String storeName;

    @Column(name = "address")
    private String address;
}
