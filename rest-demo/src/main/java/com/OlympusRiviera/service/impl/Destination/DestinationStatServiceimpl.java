package com.OlympusRiviera.service.impl.Destination;

import com.OlympusRiviera.model.Destination.DestinationStat;
import com.OlympusRiviera.repository.Destination.DestinationStatRepository;
import com.OlympusRiviera.service.Destination.DestinationStatService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationStatServiceimpl implements DestinationStatService {

    DestinationStatRepository destinationStatRepository;

    public DestinationStatServiceimpl(DestinationStatRepository destinationStatRepository){
        this.destinationStatRepository = destinationStatRepository;
    }

    // Implement the required methods here
    // Create, update, delete, and retrieve destination statistics
    @Override
    @Transactional
    public String createDestinationStat(DestinationStat destinationStat) {
        destinationStatRepository.save(destinationStat);
        return "Success";
    }

    @Override
    public String updateDestinationStat(DestinationStat destinationStat){
        destinationStatRepository.save(destinationStat);
        return "Update Success";
    }

    public String deleteDestinationStat(String stat_id) {
        destinationStatRepository.deleteById(stat_id);
        return "Deleted Success";
    }


    public DestinationStat getDestinationStat(String stat_id){
        return destinationStatRepository.findById(stat_id).get();
    }

    @Override
    public List<DestinationStat> getAllDestinationStats(){
        return destinationStatRepository.findAll();
    }

}
