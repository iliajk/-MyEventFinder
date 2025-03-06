package com.MyEventFinder.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@Table(name = "entertainmentType")
@AllArgsConstructor
@NoArgsConstructor
public class EntertainmentType extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @OneToMany(mappedBy = "type", cascade = CascadeType.PERSIST)
    private List<Entertainment> entertainments;

}
