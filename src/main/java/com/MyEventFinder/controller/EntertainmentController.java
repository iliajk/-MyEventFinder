package com.MyEventFinder.controller;

import com.MyEventFinder.model.DTO.EntertainmentDTO;
import com.MyEventFinder.model.entity.Entertainment;
import com.MyEventFinder.service.EntertainmentService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/entertainment")
@RequiredArgsConstructor
public class EntertainmentController{
    private final EntertainmentService entertainmentService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllEntertainments(@RequestParam @Nullable Long typeId,
                                                  @RequestParam @Nullable String location,
                                                  @RequestParam @Nullable Long max) {
        return entertainmentService.getAllEntertainments(typeId, location, max);
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public ResponseEntity<?> createEntertainment(@RequestBody EntertainmentDTO dto) {
        return entertainmentService.createEntertainment(dto);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getEntertainmentById(@PathVariable Long id) {
        return entertainmentService.getEntertainmentById(id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateEntertainment(@PathVariable Long id, @RequestBody EntertainmentDTO dto) {
        return entertainmentService.updateEntertainment(id, dto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteEntertainment(@PathVariable Long id) {
        return entertainmentService.deleteEntertainment(id);
    }
}
