package com.OlympusRiviera.service;

import com.OlympusRiviera.model.destination;

import java.util.List;

public interface DestinationService {
    public String createDestination(destination destination);
    public String updateDestination(destination destination);
    public String deleteDestination(String landmarkId);
    public destination getDestination(String landmarkId);
    public List<destination> getAllDestinations();
}
