package com.ezshopping.product.model.entity;

import com.ezshopping.location.cart.model.entity.Cart;
import com.ezshopping.common.AbstractEntity;
import com.ezshopping.order.orderline.model.entity.OrderLine;
import com.ezshopping.stock.model.entity.Stock;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ez_product")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class Product extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "status")
    private String status;

    @Column(name = "category")
    private String category;

    @Column(name = "brand")
    private String brand;
//TODO: add unique constraint
    @Column(name = "rf_id")
    private String rfId;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<Stock> stockEntities;

    @JsonIgnore
    @ManyToMany(mappedBy = "productList")
    private List<Cart> cartList;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<OrderLine> orderLines;
}
