package com.ezshopping.user;

import com.ezshopping.model.AbstractEntity;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "ez_user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class UserEntity extends AbstractEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

}
