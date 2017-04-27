package com.tourism.microservices.services;

import com.tourism.microservices.models.PointOfInterest;
import com.tourism.microservices.repositories.PointOfInterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by apiriu on 4/28/2017.
 */

@Service
public class PointOfInterestServiceImpl implements PointOfInterestService {

    @Autowired
    private PointOfInterestRepository repository;

//    public PointOfInterestServiceImpl(PointOfInterestRepository inputRepository) {
//        this.repository = inputRepository;
//    }

    @Override
    public PointOfInterest save(PointOfInterest entity) {
        return this.repository.save(entity);
    }

    @Override
    public List<PointOfInterest> getAll() {
        return this.repository.findAll();
    }

    @Override
    public PointOfInterest getById(Long id) {
        return this.repository.findOne(id);
    }

    @Override
    public void delete(Long id) {
        this.repository.delete(id);
    }
}
