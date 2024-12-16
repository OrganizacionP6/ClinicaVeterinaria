package org.example.services;

import org.example.dtos.AppointmentRequest;
import org.example.dtos.AppointmentResponse;
import org.example.entities.Appointment;
import org.example.entities.Pet;

import org.example.exeptions.AppointmentNotFoundException;
import org.example.mappers.AppointmentMapper;
import org.example.repositories.AppointmentRepository;
import org.example.repositories.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class AppointmentService {
    private AppointmentRepository appointmentRepository;
    private PetRepository petRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, PetRepository petRepository) {
        this.appointmentRepository = appointmentRepository;
        this.petRepository = petRepository;
    }

    public AppointmentResponse createAppointment(AppointmentRequest appointmentRequest) {
        Pet pet = petRepository.findPetEntityById(appointmentRequest.petId());

        LocalDateTime appointmentDateTime = LocalDateTime.of(appointmentRequest.date(), appointmentRequest.time());
        LocalDateTime now = LocalDateTime.now();

        if (appointmentDateTime.isBefore(now)) {
            throw new IllegalArgumentException("The appointment date and time must be in the future.");
        }
        Appointment appointment = AppointmentMapper.fromRequest(appointmentRequest, pet);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return AppointmentMapper.toResponse(savedAppointment);
    }

    public AppointmentResponse findById(Long id) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if (optionalAppointment.isEmpty()){
            throw new AppointmentNotFoundException("Appointment not found");
        }
        return AppointmentMapper.toResponse(optionalAppointment.get());
    }

    public List<AppointmentResponse> findAllAppointments() {
        List<Appointment> appointmentList = appointmentRepository.findAll();
        return appointmentList.stream().map(appointment -> AppointmentMapper.toResponse(appointment)).toList();
    }

    public void deleteById(Long id) {
        Optional<Appointment> appointmentToDelete = appointmentRepository.findById(id);
        if (appointmentToDelete.isEmpty()) {
            throw new AppointmentNotFoundException("Appointment not found");
        }
        appointmentRepository.deleteById(id);
    }

    public AppointmentResponse updateAppointment(@PathVariable Long id, @RequestBody AppointmentRequest appointmentRequest) {
        Appointment appointmentToUpdate = appointmentRepository.findById(id)
                .orElseThrow(()-> new AppointmentNotFoundException("Appointment not found"));

        Pet pet = appointmentToUpdate.getPet();
        if (appointmentRequest.petId() != pet.getId()){
            pet = petRepository.findPetEntityById(appointmentRequest.petId());
        }

        appointmentToUpdate.setDate(appointmentRequest.date());
        appointmentToUpdate.setTime(appointmentRequest.time());
        appointmentToUpdate.setReason(appointmentRequest.reason());
        appointmentToUpdate.setPet(pet);

        Appointment appointment = appointmentRepository.save(appointmentToUpdate);

        return AppointmentMapper.toResponse(appointment);
    }

    public List<AppointmentResponse> getNextAppointmentsByPetId(int petId) {
        List<Appointment> appointments = appointmentRepository.findNextAppointmentsByPetId(petId);

        return appointments.stream().map(appointment -> AppointmentMapper.toResponse(appointment)).toList();
    }

    public List<AppointmentResponse> getPastAppointmentsByPetId(int petId) {
        List<Appointment> appointments = appointmentRepository.findPastAppointmentsByPetId(petId);

        return appointments.stream().map(appointment -> AppointmentMapper.toResponse(appointment)).toList();
    }

    public long countAppointments() {
        return appointmentRepository.count();
    }
}
