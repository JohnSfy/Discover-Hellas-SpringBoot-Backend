package com.OlympusRiviera.service.impl.Destination;

import com.OlympusRiviera.model.Destination.DestinationStat;
import com.OlympusRiviera.repository.Destination.DestinationStatRepository;
import com.OlympusRiviera.service.Destination.DestinationStatService;
import org.springframework.stereotype.Service;

@Service
public class DestinationStatServiceimpl implements DestinationStatService {

    DestinationStatRepository destinationStatRepository;

    public DestinationStatServiceimpl(DestinationStatRepository destinationStatRepository){
        this.destinationStatRepository = destinationStatRepository;
    }

    // Implement the required methods here
    // Create, update, delete, and retrieve destination statistics
    public String createDestinationStat(DestinationStat destinationStat) {
        destinationStatRepository.save(destinationStat);
        return "Success";
    }

    @Override
    public String updateDestinationStat(DestinationStat destinationStat){
        destinationStatRepository.save(destinationStat);
        return "Update Success";
    }

}
