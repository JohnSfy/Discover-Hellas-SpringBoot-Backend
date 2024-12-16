package com.OlympusRiviera.controller.Amenity_Event;


import com.OlympusRiviera.model.Amenity.Amenity;
import com.OlympusRiviera.model.Amenity.AmenityCategory;
import com.OlympusRiviera.model.Event.Event;
import com.OlympusRiviera.service.Amenity.AmenityCategoryService;
import com.OlympusRiviera.service.Amenity.AmenityService;
import com.OlympusRiviera.service.Event.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/admin")
public class PotapController {

    private final AmenityService amenityService;
    private final AmenityCategoryService amenityCategoryService;
    private final EventService eventService;
    public PotapController(AmenityService amenityService, AmenityCategoryService amenityCategoryService, EventService eventService) {
        this.amenityService = amenityService;
        this.amenityCategoryService = amenityCategoryService;
        this.eventService = eventService;
    }

    //----------------------------Amenity----------------------------------------------------------
    @GetMapping("/amenity/get/all")
    public ResponseEntity<List<Amenity>> getAllAmenityDetails() {
        List<Amenity> amenities = amenityService.getAllAmenities();
        return ResponseEntity.ok(amenities); // Return 200 OK with the list of destinations
    }

    // Create a new amenity from potap with status approved
    @PostMapping("/amenity/create")
    public ResponseEntity<String> createAmenityDetails(@RequestBody Amenity amenity) {
        amenity.setStatus("APPROVED");
        amenityService.createAmenity(amenity);
        String message = "Amenity with id: " + amenity.getAmenity_id() + " Created Successfully from ΠΟΤΑΠ";
        return ResponseEntity.status(HttpStatus.CREATED).body(message); // Return 201 Created
    }

    @PutMapping("/amenity/edit/{amenity_id}")
    public ResponseEntity<String> updateAmenity(@PathVariable String amenity_id, @RequestBody Amenity amenity) {
        amenity.setAmenity_id(amenity_id);
        amenity.setStatus("APPROVED"); //Because ΠΟΤΑΠ UPDATED the amenity
        amenityService.updateAmenity(amenity);
        String message = "Amenity with id: " + amenity.getAmenity_id() + " Updated Successfully";
        return ResponseEntity.ok(message); // Return 200 OK
    }

    @DeleteMapping("/amenity/delete/{amenity_id}")
    public ResponseEntity<String> deleteDestination(@PathVariable String amenity_id) {
        amenityService.deleteAmenity(amenity_id);
        String message = "Amenity with id: " + amenity_id + " Deleted Successfully from ΠΟΤΑΠ";
        return ResponseEntity.status(HttpStatus.OK).body(message); // Use 200 OK instead
    }


    //-----------------------Amenity Category Control-----------------------------------



    @PostMapping("/amenity/category/create")
    public ResponseEntity<String> createAmenityDetails(@RequestBody AmenityCategory amenityCategory) {
        amenityCategoryService.createAmenityCategory(amenityCategory);
        String message = "Amenity Category with id: " + amenityCategory.getCategory_id() + " Created Successfully";
        return ResponseEntity.status(HttpStatus.CREATED).body(message); // Return 201 Created
    }

    @PutMapping("/amenity/category/{category_id}")
    public ResponseEntity<String> updateAmenityCategory(@PathVariable String category_id, @RequestBody AmenityCategory amenityCategory) {
        amenityCategory.setCategory_id(category_id);
        amenityCategoryService.updateAmenityCategory(amenityCategory);
        String message = "Amenity Category with id: " + amenityCategory.getCategory_id() + " Updated Successfully";
        return ResponseEntity.ok(message); // Return 200 OK
    }

    @DeleteMapping("/amenity/category/{category_id}")
    public ResponseEntity<String> deleteAmenityCategory(@PathVariable String category_id) {
        amenityCategoryService.deleteAmenityCategory(category_id);
        String message = "Amenity Category with id: " + category_id + " Deleted Successfully";
        return ResponseEntity.status(HttpStatus.OK).body(message); // Return 204 No Content after deletion
    }




    //-----------------------------------Event------------------------------------------------------
    //----------------------------------------------------------------------------------------------


    @GetMapping("/event/get/all")
    public ResponseEntity<List<Event>> getAllEventDetails() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events); // Return 200 OK with the list of destinations
    }

    // Create a new amenity from potap with status approved
    @PostMapping("/event/create")
    public ResponseEntity<String> createEventDetails(@RequestBody Event event) {
        event.setStatus("APPROVED");
        eventService.createEvent(event);
        String message = "Event with id: " + event.getEvent_id() + " Created Successfully from ΠΟΤΑΠ";
        return ResponseEntity.status(HttpStatus.CREATED).body(message); // Return 201 Created
    }

    @PutMapping("/event/edit/{event_id}")
    public ResponseEntity<String> updateEvent(@PathVariable String event_id, @RequestBody Event event) {
        event.setEvent_id(event_id);
        event.setStatus("APPROVED"); //Because ΠΟΤΑΠ UPDATED the amenity
        eventService.updateEvent(event);
        String message = "Amenity with id: " + event.getEvent_id() + " Updated Successfully";
        return ResponseEntity.ok(message); // Return 200 OK
    }

    @DeleteMapping("/event/delete/{event_id}")
    public ResponseEntity<String> deleteEvent(@PathVariable String event_id) {
        eventService.deleteEvent(event_id);
        String message = "Event with id: " + event_id + " Deleted Successfully from ΠΟΤΑΠ";
        return ResponseEntity.status(HttpStatus.OK).body(message); // Use 200 OK instead
    }
}
