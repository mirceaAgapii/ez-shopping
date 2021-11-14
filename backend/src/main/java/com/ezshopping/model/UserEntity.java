package com.ezshopping.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ez-user")
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

    public UserEntity(String name, String role) {
        this.name = name;
        this.role = role;
    }

}
