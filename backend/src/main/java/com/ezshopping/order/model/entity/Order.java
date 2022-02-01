package com.ezshopping.order.model.entity;

import com.ezshopping.common.AbstractEntity;
import com.ezshopping.user.model.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ez_order")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Builder
public class Order extends AbstractEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "total")
    private Double total;

    @Column(name = "finished")
    private boolean finished;
}
