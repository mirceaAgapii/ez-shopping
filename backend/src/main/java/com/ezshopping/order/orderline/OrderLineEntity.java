package com.ezshopping.order.orderline;

import com.ezshopping.model.AbstractEntity;
import com.ezshopping.order.OrderEntity;
import com.ezshopping.product.ProductEntity;
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
public class OrderLineEntity extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable = false)
    private OrderEntity parent;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProductEntity product;

    @Column(name = "quantity")
    private Double quantity;
}