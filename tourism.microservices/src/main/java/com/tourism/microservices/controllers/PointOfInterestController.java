package com.tourism.microservices.controllers;

import com.tourism.microservices.models.PointOfInterest;
import com.tourism.microservices.services.PointOfInterestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by apiriu on 4/28/2017.
 */
@RestController
@RequestMapping("/pointsofinterest")
public class PointOfInterestController {

    private PointOfInterestService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PointOfInterest>> get() {
        List<PointOfInterest> pois = this.service.getAll();
        if (pois.isEmpty()) {
            return new ResponseEntity<List<PointOfInterest>>(HttpStatus.OK);
//            return new ResponseEntity<List<PointOfInterest>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<PointOfInterest>>(pois, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PointOfInterest> addPointOfInterest(@RequestBody PointOfInterest pointOfInterest){
        PointOfInterest poi = this.service.save(pointOfInterest);
        return new ResponseEntity<PointOfInterest>(poi, HttpStatus.CREATED);
    }
}
