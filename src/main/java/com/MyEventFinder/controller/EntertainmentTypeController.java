package com.MyEventFinder.controller;

import com.MyEventFinder.service.EntertainmentService;
import com.MyEventFinder.service.EntertainmentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/entertainmentType")
@RequiredArgsConstructor
public class EntertainmentTypeController {
    private final EntertainmentTypeService entTypeService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllEntertainmentTypes() {
        return entTypeService.findAll();
    }
}
