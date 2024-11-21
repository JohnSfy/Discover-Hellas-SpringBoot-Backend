package com.OlympusRiviera.service.Destination;

import com.OlympusRiviera.model.Destination.Destination;
import com.OlympusRiviera.model.Destination.DestinationStat;

import java.util.List;

public interface DestinationStatService {

    public String createDestinationStat(DestinationStat destinationStat);
    public String updateDestinationStat(DestinationStat destinationStat);
    public String deleteDestination(String stat_id);
    public DestinationStat getDestination(String stat_id);
    public List<DestinationStat> getAllDestinationStats();
}
