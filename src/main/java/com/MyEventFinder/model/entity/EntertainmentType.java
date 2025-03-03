package com.MyEventFinder.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "entertainmentType")
public class EntertainmentType extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "type", cascade = CascadeType.PERSIST)
    private List<Entertainment> entertainments;

}
