package org.example.controllers;

import jakarta.validation.Valid;
import org.example.dtos.AppointmentRequest;
import org.example.dtos.AppointmentResponse;
import org.example.entities.Appointment;
import org.example.entities.Guardian;
import org.example.entities.Pet;
import org.example.repositories.AppointmentRepository;
import org.example.repositories.PetRepository;
import org.example.services.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody AppointmentRequest appointmentRequest){

        AppointmentResponse appointment = appointmentService.createAppointment(appointmentRequest);
        return new ResponseEntity<>(appointment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public AppointmentResponse getAppointmentById (@PathVariable Long id){
        return appointmentService.findById(id);
    }

    @GetMapping
    public List<AppointmentResponse> showAllAppointments() {
        return appointmentService.findAllAppointments();
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> updateAppointment(@PathVariable Long id, @RequestBody AppointmentRequest appointmentRequest) {
        AppointmentResponse appointment  = appointmentService.updateAppointment(id, appointmentRequest);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @GetMapping("/next")
    public List<AppointmentResponse> getNextAppointments(@RequestParam int petId) {
        return appointmentService.getNextAppointmentsByPetId(petId);
    }

    @GetMapping("/past")
    public List<AppointmentResponse> getPastAppointments(@RequestParam int petId) {
        return appointmentService.getPastAppointmentsByPetId(petId);
    }

}
