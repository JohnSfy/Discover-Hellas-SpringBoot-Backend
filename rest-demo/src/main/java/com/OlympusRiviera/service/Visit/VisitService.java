package com.OlympusRiviera.service.Visit;

import com.OlympusRiviera.model.Visit.Visit;

import java.util.List;

public interface VisitService {


    public String createVisit(Visit visit);
    public String updateVisit(Visit visit);
    public String deleteVisit(String visit_id);
    public Visit getVisit(String visit_id);
    public List<Visit> getAllVisits();
}
