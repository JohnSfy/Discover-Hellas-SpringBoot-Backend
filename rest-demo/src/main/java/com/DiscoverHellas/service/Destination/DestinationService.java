package com.DiscoverHellas.service.Destination;

import com.DiscoverHellas.model.Destination.Destination;


import java.util.List;

public interface DestinationService {

    public String createDestination(Destination destination);
    public String updateDestination(Destination destination);
    public String deleteDestination(String destination_id);
    public Destination getDestination(String destination_id);
    public List<Destination> getAllDestinations();
}
