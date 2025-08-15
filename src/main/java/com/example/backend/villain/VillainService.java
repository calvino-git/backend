package com.example.backend.villain;

import java.util.UUID;

// import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import java.util.NoSuchElementException;

public class VillainService {

    private final VillainRepository villainRepository;

    public VillainService(VillainRepository villainRepository) {
        this.villainRepository = villainRepository;
    }

    public Villain createVillain(Villain villain) {
        return villainRepository.save(villain);
    }

    public Villain updateVillain(UUID id, Villain villainDetails) {
        Villain existingVillain = findOrThrow(id);
        if (existingVillain != null) {
            existingVillain.setFirstName(villainDetails.getFirstName());
            existingVillain.setLastName(villainDetails.getLastName());
            existingVillain.setHouse(villainDetails.getHouse());
            existingVillain.setKnowAs(villainDetails.getKnowAs());
            return villainRepository.save(existingVillain);
        }
        return null;
    }

    public Villain getVillainById(UUID id) {
        return findOrThrow(id);
    }

    public Iterable<Villain> getAllVillains() {
        return villainRepository.findAll();
    }

    public void deleteVillain(UUID id) {
        villainRepository.deleteById(id);
    }
    private Villain findOrThrow(final UUID id) {
        return villainRepository
            .findById(id)
            .orElseThrow(
                () -> new NoSuchElementException("Villain by id " +
                    id + " was not found")
            );
    }
    
}
