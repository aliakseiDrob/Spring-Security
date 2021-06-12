package com.epam.esm.entity;

import com.epam.esm.audit.AuditListener;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
@EqualsAndHashCode(exclude = {"orders"})
@ToString(exclude = {"orders"})
@EntityListeners(AuditListener.class)

public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user_id",nullable = false)
    private String userId;
    @Column(name = "user_name",nullable = false)
    private String userName;
    @JsonBackReference
    @OneToMany(mappedBy = "account")
    private Set<Order> orders;

    public Account(long id, String userId, String userName) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
    }
}
