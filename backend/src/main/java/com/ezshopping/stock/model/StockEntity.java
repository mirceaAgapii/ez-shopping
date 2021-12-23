package com.ezshopping.stock.model;

import com.ezshopping.location.LocationEntity;
import com.ezshopping.common.AbstractEntity;
import com.ezshopping.product.model.Product;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "ez_stock")
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class StockEntity extends AbstractEntity {

    @Column(name = "location_type")
    private String locationType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private LocationEntity location;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "quantity")
    private Double quantity;
}
