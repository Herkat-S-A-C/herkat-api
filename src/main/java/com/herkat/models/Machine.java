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
@Table(name = "machines")
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private MachineType type;

    @Column(nullable = false)
    private String description;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(unique = true, nullable = false)
    private Image image;

    @Column(name = "is_featured", nullable = false)
    private Boolean isFeatured = false;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @PrePersist
    public void prePersist() {
        registrationDate = LocalDateTime.now();
    }

    public static Machine newMachine(String name, MachineType type, String description,
                                     Image image, Boolean isFeatured, LocalDateTime registrationDate) {
        return new Machine(
                null,
                name,
                type,
                description,
                image,
                isFeatured,
                registrationDate
        );
    }

}
