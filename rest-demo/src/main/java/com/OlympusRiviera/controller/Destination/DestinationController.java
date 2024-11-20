package com.OlympusRiviera.controller.Destination;

import com.OlympusRiviera.model.Destination.Destination;
import com.OlympusRiviera.service.Destination.DestinationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/admin/destination")
public class DestinationController {

    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    // Get details of a specific destination by ID
    @GetMapping("/{destination_id}")
    public ResponseEntity<Destination> getDestinationDetails(@PathVariable("destination_id") String destination_id) {
        Destination destinationDetails = destinationService.getDestination(destination_id);
        if (destinationDetails != null) {
            return ResponseEntity.ok(destinationDetails); // Return 200 OK with the destination
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 Not Found if the destination doesn't exist
        }
    }

    // Get all destination details
    @GetMapping("/get/all")
    public ResponseEntity<List<Destination>> getAllDestinationDetails() {
        List<Destination> destinations = destinationService.getAllDestinations();
        return ResponseEntity.ok(destinations); // Return 200 OK with the list of destinations
    }

    // Create a new destination
    @PostMapping("/create")
    public ResponseEntity<String> createDestinationDetails(@RequestBody Destination destination) {
        destinationService.createDestination(destination);
        String message = "Destination with id: " + destination.getDestination_id() + " Created Successfully";
        return ResponseEntity.status(HttpStatus.CREATED).body(message); // Return 201 Created
    }

    // Update an existing destination by ID
    @PutMapping("/{destination_id}")
    public ResponseEntity<String> updateDestination(@PathVariable String destination_id, @RequestBody Destination destination) {
        destination.setDestination_id(destination_id);
        destinationService.updateDestination(destination);
        String message = "Destination with id: " + destination.getDestination_id() + " Updated Successfully";
        return ResponseEntity.ok(message); // Return 200 OK
    }

    // Delete a destination by ID
    @DeleteMapping("/{destination_id}")
    public ResponseEntity<String> deleteDestination(@PathVariable String destination_id) {
        destinationService.deleteDestination(destination_id);
        String message = "Destination with id: " + destination_id + " Deleted Successfully";
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message); // Return 204 No Content after deletion
    }
}
