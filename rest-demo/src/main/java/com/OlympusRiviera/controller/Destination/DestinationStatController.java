package com.OlympusRiviera.controller.Destination;

import com.OlympusRiviera.model.Destination.DestinationStat;
import com.OlympusRiviera.service.Destination.DestinationStatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/admin/statistics")
public class DestinationStatController {

    private final DestinationStatService destinationStatService;

    public DestinationStatController(DestinationStatService destinationStatService){
        this.destinationStatService = destinationStatService;
    }


    //getStats for the specified destination
    @GetMapping("/{destination_id}")
    public ResponseEntity<List<DestinationStat>> getStatsByDestinationId(@PathVariable("destination_id") String destination_id) {
        // Fetch all stats
        List<DestinationStat> allStats = destinationStatService.getAllDestinationStats();
    
        // Filter stats by destination_id
        List<DestinationStat> filteredStats = allStats.stream()
                .filter(stat -> destination_id.equals(stat.getDestination_id()))
                .collect(Collectors.toList());
    
        if (filteredStats.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 if no stats are found
        } else {
            return ResponseEntity.ok(filteredStats); // Return 200 OK with the filtered list
        }
    }

    //get all stats for destinations
    @GetMapping("/get/all")
    public ResponseEntity<List<DestinationStat>> getAllDestinationDetails() {
        List<DestinationStat> destinationStats = destinationStatService.getAllDestinationStats();
        return ResponseEntity.ok(destinationStats); // Return 200 OK with the list of destinations
    }


    @PostMapping("/create")
    public ResponseEntity<String> createDestinationStat(@RequestBody DestinationStat destinationStat) {
        destinationStatService.createDestinationStat(destinationStat);
        String message = "Statistic with id: " + destinationStat.getStat_id() + " Created Successfully" + "for destination with id: "+ destinationStat.getDestination_id();
        return ResponseEntity.status(HttpStatus.CREATED).body(message); // Return 201 Created
    }

}
