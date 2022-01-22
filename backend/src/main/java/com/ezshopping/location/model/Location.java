package com.ezshopping.location.model;

import com.ezshopping.common.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "ez_location")
@Inheritance(strategy = InheritanceType.JOINED)
public class Location extends AbstractEntity {

    @Column(name = "location_type")
    private String locationType;
}
