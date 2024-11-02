package com.OlympusRiviera.service;

import com.OlympusRiviera.model.Landmark;
import com.OlympusRiviera.model.User;

import java.util.List;

public interface LandmarkService {
    public String createLandmark(Landmark landmark);
    public String updateLandmark(Landmark landmark);
    public String deleteLandmark(String landmarkId);
    public Landmark getLandmark(String landmarkId);
    public List<Landmark> getAllLandmarks();
}
