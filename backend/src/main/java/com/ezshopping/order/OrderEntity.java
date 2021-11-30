package com.ezshopping.order;

import com.ezshopping.model.AbstractEntity;
import com.ezshopping.order.orderline.OrderLineEntity;
import com.ezshopping.product.ProductEntity;
import com.ezshopping.user.UserEntity;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ez_order")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Builder
public class OrderEntity extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false, insertable = false)
    private UserEntity user;

    @Column(name = "total")
    private Double total;
}
