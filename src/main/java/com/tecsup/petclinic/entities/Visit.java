package com.tecsup.petclinic.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Visit Entity
 */
@Entity(name = "visits")
@Data
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pet_id", nullable = false)
    private Integer petId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "visit_date")
    private Date visitDate;

    private String description;

    public Visit() {
    }

    public Visit(Integer id, Integer petId, Date visitDate, String description) {
        this.id = id;
        this.petId = petId;
        this.visitDate = visitDate;
        this.description = description;
    }

    public Visit(Integer petId, Date visitDate, String description) {
        this.petId = petId;
        this.visitDate = visitDate;
        this.description = description;
    }
}
