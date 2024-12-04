package org.example.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_appointment", nullable = false)
    private Long id;
    @Column(name = "date")
    private String date;
    @Column(name = "time")
    private String time;

    @Column(name = "reason")
    private String reason;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    @JsonIgnoreProperties(value = "appointment")
    private Pet pet;

    public Appointment() {
    }

    public Appointment(Long id, String date, String time, String reason, Pet pet) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.reason = reason;
        this.pet = pet;
    }

}
