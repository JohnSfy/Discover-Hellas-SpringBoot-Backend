package com.OlympusRiviera.controller;

import com.OlympusRiviera.model.Landmark;
import com.OlympusRiviera.service.LandmarkService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/landmark")
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


    @PutMapping
    public String updateLandmark(@RequestBody Landmark landmark){
//        this.cloudVendor = cloudVendor;
        landmarkService.updateLandmark(landmark);
        return "Landmark Updated Successfully";
    }

    @DeleteMapping("{landmarkId}")
    public String deleteLandmarkDetails(@PathVariable String landmarkId){
        landmarkService.deleteLandmark(landmarkId);
        return "Landmark Deleted Successfully";
    }
}
