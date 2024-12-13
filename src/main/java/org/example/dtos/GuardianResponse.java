package org.example.dtos;


public record GuardianResponse(
        int id,
        String name,
        String email,
        String phone,
        String address
) {

}
