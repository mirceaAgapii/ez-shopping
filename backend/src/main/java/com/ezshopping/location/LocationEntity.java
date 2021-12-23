package com.ezshopping.location;

import com.ezshopping.common.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "ez_location")
@Inheritance(strategy = InheritanceType.JOINED)
public class LocationEntity extends AbstractEntity {

    @Column(name = "location_type")
    private String locationType;
}
