package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exception.VisitNotFoundException;
import com.tecsup.petclinic.repositories.VisitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

    public VisitServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public Visit create(Visit visit) {
        return visitRepository.save(visit);
    }

    @Override
    public Visit update(Visit visit) throws VisitNotFoundException {
        if (!visitRepository.existsById(visit.getId())) {
            throw new VisitNotFoundException("Visit not found with ID: " + visit.getId());
        }
        return visitRepository.save(visit);
    }

    @Override
    public void delete(Integer id) throws VisitNotFoundException {
        if (!visitRepository.existsById(id)) {
            throw new VisitNotFoundException("Visit not found with ID: " + id);
        }
        visitRepository.deleteById(id);
    }

    @Override
    public Visit findById(Integer id) throws VisitNotFoundException {
        return visitRepository.findById(id)
                .orElseThrow(() -> new VisitNotFoundException("Visit not found with ID: " + id));
    }

    @Override
    public List<Visit> findByPetId(Integer petId) {
        return visitRepository.findByPetId(petId);
    }

    @Override
    public List<Visit> findAll() {
        return visitRepository.findAll();
    }
}
