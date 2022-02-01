package com.ezshopping.order.orderline.model.entity;

import com.ezshopping.common.AbstractEntity;
import com.ezshopping.order.model.entity.Order;
import com.ezshopping.product.model.entity.Product;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ez_order_line")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Builder
public class OrderLine extends AbstractEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order parent;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "quantity")
    private Double quantity;

    public void incrementQty() {
        this.quantity = this.quantity + 1.00;
    }

}
