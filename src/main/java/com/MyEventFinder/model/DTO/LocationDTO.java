package com.MyEventFinder.model.DTO;

import com.MyEventFinder.model.entity.Entertainment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {
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
}
