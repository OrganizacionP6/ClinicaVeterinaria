
package org.example.services;

import org.example.dtos.GuardianRequest;
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



    public Guardian createGuardian(GuardianRequest guardianRequest) {
        Guardian guardian = GuardianMapper.fromRequest(guardianRequest);
        return guardianRepository.save(guardian);
    };
    public Guardian findById(int id) {
        Optional<Guardian> optionalGuardian = guardianRepository.findById(id);
        if (optionalGuardian.isEmpty()){
            throw new GuardianNotFoundException("Guardian not found");
        }
        return optionalGuardian.get();
    }

    public List<Guardian> findAll() {
        return  guardianRepository.findAll();
    }

    public List<Guardian> searchByName(String name) {
        List<Guardian> guardianList = guardianRepository.findLikeName(name);

        if (guardianList.isEmpty()) {
            throw new GuardianNotFoundException("Guardian Not Found");
        }
        return guardianList;
    }

    public Guardian deleteById(int id) {
        Optional<Guardian> guardianToDelete = guardianRepository.findById(id);
        if(guardianToDelete.get().getPets().isEmpty()){
            guardianRepository.deleteById(id);
            return guardianToDelete.get();
        }
        throw new GuardianNotFoundException("Guardian Have Pets");
    }

    public Guardian updateGuardian(int id, GuardianRequest guardianRequest) {
        Optional<Guardian> guardianToUpdate = guardianRepository.findById(id);

        if(guardianToUpdate.isEmpty()){
            throw new GuardianNotFoundException("Guardian Not Found");
        }
        Guardian guardian = GuardianMapper.fromRequest(guardianRequest);
        guardianToUpdate.get().setName(guardian.getName());
        guardianToUpdate.get().setPhone(guardian.getPhone());
        guardianToUpdate.get().setEmail(guardian.getEmail());
        guardianToUpdate.get().setAddress(guardian.getAddress());

        return guardianRepository.save(guardianToUpdate.get());
    }

    public long countGuardians() {
        return guardianRepository.count();
    }
}
