package com.ezshopping.location.model.entity;

import com.ezshopping.common.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ez_location")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class Location extends AbstractEntity {

    @Column(name = "location_type")
    private String locationType;
}
