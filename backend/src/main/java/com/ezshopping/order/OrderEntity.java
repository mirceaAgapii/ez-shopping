package com.ezshopping.order;

import com.ezshopping.common.AbstractEntity;
import com.ezshopping.user.model.User;
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
public class OrderEntity extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false, insertable = false)
    private User user;

    @Column(name = "total")
    private Double total;
}
