package com.DiscoverHellas.service.impl.Destination;

import com.DiscoverHellas.model.Destination.DestinationStat;
import com.DiscoverHellas.repository.Destination.DestinationStatRepository;
import com.DiscoverHellas.service.Destination.DestinationStatService;
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
