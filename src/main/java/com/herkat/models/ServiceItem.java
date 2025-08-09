package com.herkat.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "service_items")
public class ServiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private ServiceItemType type;

    @Column(nullable = false)
    private String description;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(unique = true, nullable = false)
    private Image image;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @PrePersist
    public void prePersist() {
        registrationDate = LocalDateTime.now();
    }

    public static ServiceItem newServiceItem(String name, ServiceItemType type, String description,
                                             Image image, LocalDateTime registrationDate) {
        return new ServiceItem(
                null,
                name,
                type,
                description,
                image,
                registrationDate
        );
    }

}
