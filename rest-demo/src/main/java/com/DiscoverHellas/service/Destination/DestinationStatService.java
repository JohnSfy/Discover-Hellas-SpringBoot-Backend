package com.DiscoverHellas.service.Destination;

import com.DiscoverHellas.model.Destination.DestinationStat;

import java.util.List;

public interface DestinationStatService {

    public String createDestinationStat(DestinationStat destinationStat);
    public String updateDestinationStat(DestinationStat destinationStat);
    public String deleteDestinationStat(String stat_id);
    public DestinationStat getDestinationStat(String stat_id);
    public List<DestinationStat> getAllDestinationStats();

}
