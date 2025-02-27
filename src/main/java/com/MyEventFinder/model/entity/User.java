package com.MyEventFinder.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private Boolean company;

    private Boolean agency;

    private Boolean deleted;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Entertainment> customerEntertainments;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "executors")
    private List<Entertainment> executorEntertainments;

}
