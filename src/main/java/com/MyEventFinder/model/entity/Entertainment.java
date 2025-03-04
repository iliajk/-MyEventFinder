package com.MyEventFinder.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

@Entity
@Data
@Table(name = "entertainment")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Entertainment extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private EntertainmentType type;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    private LocalDateTime date;

    private BigDecimal price;

    private Currency currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "executors_entertainment",
            joinColumns = @JoinColumn(name = "entertainment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> executors;

}
