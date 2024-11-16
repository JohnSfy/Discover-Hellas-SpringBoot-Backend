package com.OlympusRiviera.controller;

import com.OlympusRiviera.model.Landmark;
import com.OlympusRiviera.service.LandmarkService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/olympus_riviera/landmark")
public class LandmarkController {
    LandmarkService landmarkService;
    public LandmarkController(LandmarkService landmarkService){
        this.landmarkService = landmarkService;
    }

    @GetMapping("{landmarkId}")
    public Landmark getLandmarkDetails(@PathVariable("landmarkId") String landmarkId){


        return landmarkService.getLandmark(landmarkId);
    }


    // Read all Landmarks Details from DB
    @GetMapping()
    public List<Landmark> getAllUserDetails(){


        return landmarkService.getAllLandmarks();
    }

    @PostMapping
    public String createLandmarkDetails(@RequestBody Landmark landmark){
//        this.cloudVendor = cloudVendor;
        landmarkService.createLandmark(landmark);
        return "Landmark Created Successfully ";

    }


    @PutMapping("{landmarkId}") // Include the landmarkId in the path
    public String updateLandmark(@PathVariable String landmarkId, @RequestBody Landmark landmark) {
        // Assuming you want to update the landmark using its ID
        landmark.setLandmarkId(landmarkId); // Set the ID from the URL to the request body
        landmarkService.updateLandmark(landmark);
        return "Landmark Updated Successfully";
    }

    @DeleteMapping("{landmarkId}")
    public String deleteLandmarkDetails(@PathVariable String landmarkId){
        landmarkService.deleteLandmark(landmarkId);
        return "Landmark Deleted Successfully";
    }
}
