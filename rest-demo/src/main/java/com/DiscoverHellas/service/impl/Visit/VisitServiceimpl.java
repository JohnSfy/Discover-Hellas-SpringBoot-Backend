package com.DiscoverHellas.service.impl.Visit;

import com.DiscoverHellas.model.Visit.Visit;
import com.DiscoverHellas.repository.Visit.VisitRepository;
import com.DiscoverHellas.service.Visit.VisitService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitServiceimpl implements VisitService {

    VisitRepository visitRepository;

    public VisitServiceimpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    // Implement the methods for interacting with the plan repository
    @Override
    public String createVisit(Visit visit) {
        visitRepository.save(visit);
        return "Success";
    }

    @Override
    public String updateVisit(Visit visit) {
        visitRepository.save(visit);
        return "Update Success";
    }

    @Override
    public String deleteVisit(String visit_id) {
        visitRepository.deleteById(visit_id);
        return "Deleted Success";
    }

    @Override
    public Visit getVisit(String visit_id) {

        return visitRepository.findById(visit_id).get();
    }

    @Override
    public List<Visit> getAllVisits() {
        return visitRepository.findAll();
    }
}
