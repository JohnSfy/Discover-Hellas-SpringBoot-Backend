package com.OlympusRiviera.service.impl.Destination;

import com.OlympusRiviera.model.Destination.Destination;
import com.OlympusRiviera.repository.Destination.DestinationRepository;
import com.OlympusRiviera.service.Destination.DestinationService;
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
    public String createDestination(Destination destination) {
        destinationRepository.save(destination);
        return "Success";
    }

    @Override
    public String updateDestination(Destination destination) {
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
    public Destination getDestination(String destination_id) {

        return destinationRepository.findById(destination_id).get();
    }

    @Override
    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }


}
