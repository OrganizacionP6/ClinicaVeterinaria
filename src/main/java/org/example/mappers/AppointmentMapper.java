package org.example.mappers;

import org.example.dtos.AppointmentRequest;
import org.example.dtos.AppointmentResponse;
import org.example.entities.Appointment;
import org.example.entities.Pet;

public class AppointmentMapper {

    public static Appointment fromRequest(AppointmentRequest appointmentRequest, Pet pet){
        return new Appointment(appointmentRequest.date(), appointmentRequest.time(), appointmentRequest.reason(), pet);
    }
    public static AppointmentResponse toResponse(Appointment appointment){
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getDate(),
                appointment.getTime(),
                appointment.getReason(),
                PetMapper.toResponse(appointment.getPet())
        );
    }

}
