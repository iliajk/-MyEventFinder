package com.MyEventFinder.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String city;

    private String state;

    private String country;

    private String phone;

    private String email;

    private String mapLink;

    private Integer capacity;

    @OneToMany(mappedBy = "location", cascade = CascadeType.PERSIST)
    private List<Entertainment> entertainments;

}
