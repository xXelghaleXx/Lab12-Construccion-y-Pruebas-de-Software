package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.domain.VisitTO;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exception.VisitNotFoundException;
import com.tecsup.petclinic.mapper.VisitMapper;
import com.tecsup.petclinic.services.VisitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class VisitController {

    private final VisitService visitService;
    private final VisitMapper mapper;

    public VisitController(VisitService visitService, VisitMapper mapper) {
        this.visitService = visitService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/visits")
    public ResponseEntity<List<VisitTO>> findAllVisits() {
        List<Visit> visits = visitService.findAll();
        log.info("Visits: {}", visits);
        return ResponseEntity.ok(mapper.toVisitTOList(visits));
    }

    @PostMapping(value = "/visits")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VisitTO> create(@RequestBody VisitTO visitTO) {
        Visit newVisit = mapper.toVisit(visitTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toVisitTO(visitService.create(newVisit)));
    }

    @GetMapping(value = "/visits/{id}")
    public ResponseEntity<VisitTO> findById(@PathVariable Integer id) {
        try {
            Visit visit = visitService.findById(id);
            return ResponseEntity.ok(mapper.toVisitTO(visit));
        } catch (VisitNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/visits/{id}")
    public ResponseEntity<VisitTO> update(@RequestBody VisitTO visitTO, @PathVariable Integer id) {
        try {
            Visit visit = visitService.findById(id);
            visit.setPetId(visitTO.getPetId());
            visit.setVisitDate(visitTO.getVisitDate());
            visit.setDescription(visitTO.getDescription());
            return ResponseEntity.ok(mapper.toVisitTO(visitService.update(visit)));
        } catch (VisitNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/visits/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            visitService.delete(id);
            return ResponseEntity.ok("Deleted Visit ID: " + id);
        } catch (VisitNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
