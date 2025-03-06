package com.MyEventFinder.controller;

import com.MyEventFinder.model.DTO.EntertainmentDTO;
import com.MyEventFinder.model.DTO.LocationDTO;
import com.MyEventFinder.repository.LocationRepository;
import com.MyEventFinder.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public ResponseEntity<?> createLocation(@RequestBody LocationDTO dto) {
        return locationService.createLocation(dto);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getLocationById(@PathVariable Long id) {
        return locationService.getLocationById(id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateLocation(@PathVariable Long id, @RequestBody LocationDTO dto) {
        return locationService.updateLocation(id, dto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long id) {
        return locationService.deleteLocation(id);
    }

}
