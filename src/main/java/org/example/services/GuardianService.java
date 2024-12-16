
package org.example.services;

import org.example.dtos.GuardianRequest;
import org.example.dtos.GuardianResponse;
import org.example.entities.Guardian;
import org.example.exeptions.GuardianNotFoundException;
import org.example.mappers.GuardianMapper;
import org.example.repositories.GuardianRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuardianService {
    private GuardianRepository guardianRepository;

    public GuardianService(GuardianRepository guardianRepository) {
        this.guardianRepository = guardianRepository;
    }

    private void validateGuardian(String guardian) {
        if (guardian == null || guardian.trim().isEmpty()) {
            throw new IllegalArgumentException("Guardian name cannot be empty or contain only spaces.");
        }
    }

    public GuardianResponse createGuardian(GuardianRequest guardianRequest) {
        Guardian guardian = GuardianMapper.fromRequest(guardianRequest);
        Guardian savedGuardian = guardianRepository.save(guardian);
        return GuardianMapper.toResponse(savedGuardian);
    }

    public GuardianResponse findGuardianById(int id) {

        Optional<Guardian> optionalGuardian = guardianRepository.findById(id);

        if (optionalGuardian.isEmpty()){
            throw new GuardianNotFoundException("Guardian not found");
        }

        return GuardianMapper.toResponse(optionalGuardian.get());
    }


    public List<GuardianResponse> getAllGuardians() {

        List<Guardian> guardianList = guardianRepository.findAll();
       return guardianList.stream()
               .map(guardian -> GuardianMapper.toResponse(guardian)).toList();
    }

    public List<GuardianResponse> searchByName(String name) {

        List<Guardian> guardianList = guardianRepository.findLikeName(name);

        return guardianList.stream()
                .map(guardian -> GuardianMapper.toResponse(guardian)).toList();
    }

    public void deleteById(int id) {
        Optional<Guardian> optionalGuardian = guardianRepository.findById(id);

        if (!optionalGuardian.isPresent()) {
            throw new GuardianNotFoundException("Guardian not found");
        }

        Guardian guardian = optionalGuardian.get();
        if (!guardian.getPets().isEmpty()) {
            throw new IllegalStateException("Cannot delete Guardian with pets");
        }

        guardianRepository.deleteById(id);
    }

    public GuardianResponse updateGuardian(int id, GuardianRequest guardianRequest) {
        Optional<Guardian> guardianToUpdate = guardianRepository.findById(id);

        if (guardianToUpdate.isEmpty()) {
            throw new GuardianNotFoundException("Guardian Not Found");
        }

        Guardian guardian = guardianToUpdate.get();
        guardian.setName(guardianRequest.name());
        guardian.setPhone(guardianRequest.phone());
        guardian.setEmail(guardianRequest.email());
        guardian.setAddress(guardianRequest.address());

        Guardian updatedGuardian = guardianRepository.save(guardian);

        return GuardianMapper.toResponse(updatedGuardian);
    }

    public Guardian findGuardianEntityById(int id) {
        return guardianRepository.findById(id)
                .orElseThrow(() -> new GuardianNotFoundException("Guardian not found"));
    }


    public long countGuardians() {
        return guardianRepository.count();
    }
}
