package com.ezshopping.user.model.entity;

import com.ezshopping.common.AbstractEntity;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "ez_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Builder
public class User extends AbstractEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

}
