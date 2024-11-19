package com.OlympusRiviera.service.impl;

import com.OlympusRiviera.model.destination;
import com.OlympusRiviera.repository.DestinationRepository;
import com.OlympusRiviera.service.DestinationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationServiceimpl implements DestinationService {

    DestinationRepository destinationRepository;

    public DestinationServiceimpl(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    // Implement the methods for interacting with the landmark repository
    @Override
    public String createDestination(destination destination) {
        destinationRepository.save(destination);
        return "Success";
    }

    @Override
    public String updateDestination(destination destination) {
//        more logic
        destinationRepository.save(destination);
        return "Update Success";
    }

    @Override
    public String deleteDestination(String destination_id) {
        destinationRepository.deleteById(destination_id);
        return "Deleted Success";
    }

    @Override
    public destination getDestination(String destination_id) {

        return destinationRepository.findById(destination_id).get();
    }

    @Override
    public List<destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

}
