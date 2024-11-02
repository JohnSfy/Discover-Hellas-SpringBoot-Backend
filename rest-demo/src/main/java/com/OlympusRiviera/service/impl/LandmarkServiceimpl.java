package com.OlympusRiviera.service.impl;

import com.OlympusRiviera.model.Landmark;
import com.OlympusRiviera.repository.LandmarkRepository;
import com.OlympusRiviera.service.LandmarkService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LandmarkServiceimpl implements LandmarkService {

    LandmarkRepository landmarkRepository;

    public LandmarkServiceimpl(LandmarkRepository landmarkRepository) {
        this.landmarkRepository = landmarkRepository;
    }

    // Implement the methods for interacting with the landmark repository
    @Override
    public String createLandmark(Landmark landmark) {
        landmarkRepository.save(landmark);
        return "Success";
    }

    @Override
    public String updateLandmark(Landmark landmark) {
//        more logic
        landmarkRepository.save(landmark);
        return "Updated SUCCESS";
    }

    @Override
    public String deleteLandmark(String landmarkId) {
        landmarkRepository.deleteById(landmarkId);
        return "Deleted Success";
    }

    @Override
    public Landmark getLandmark(String landmarkId) {

        return landmarkRepository.findById(landmarkId).get();
    }

    @Override
    public List<Landmark> getAllLandmarks() {
        return landmarkRepository.findAll();
    }

}
