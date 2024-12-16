package org.example.repositories;

import org.example.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository <Appointment, Long> {

    @Query(value = "SELECT a FROM Appointment a WHERE a.pet.id = :petId AND a.date >= CURRENT_DATE ORDER BY a.date, a.time")
    List<Appointment> findNextAppointmentsByPetId(int petId);

    @Query("SELECT a FROM Appointment a WHERE a.pet.id = :petId AND a.date < CURRENT_DATE ORDER BY a.date DESC, a.time DESC")
    List<Appointment> findPastAppointmentsByPetId(@Param("petId") int petId);

}
